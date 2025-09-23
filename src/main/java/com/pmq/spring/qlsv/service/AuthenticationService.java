package com.pmq.spring.qlsv.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.pmq.spring.qlsv.exception.AppException;
import com.pmq.spring.qlsv.exception.ErrorCode;
import com.pmq.spring.qlsv.model.Student;
import com.pmq.spring.qlsv.repository.StudentRepository;
import com.pmq.spring.qlsv.dto.request.AuthenticationRequest;
import com.pmq.spring.qlsv.dto.request.IntrospectRequest;
import com.pmq.spring.qlsv.dto.response.AuthenticationResponse;
import com.pmq.spring.qlsv.dto.response.IntrospectResponse;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
public class AuthenticationService {

    private final StudentRepository studentRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;


    public IntrospectResponse introspect(IntrospectRequest request)
            throws ParseException, JOSEException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expiredTime.after(new Date()))
                .build();


    }

    @Autowired
    public AuthenticationService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var student = studentRepository.findById(request.getStuCode())
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), student.getPassword());

        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(student);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(Student student) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(student.getStuCode())
                .issuer("pmqchez.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(student))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot read token");
            throw new AppException(ErrorCode.UNREAD_TOKEN);
        }

    }

    private String buildScope(Student student) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(student.getRoles()))
            student.getRoles().forEach(stringJoiner::add);

        return stringJoiner.toString();
    }
}
