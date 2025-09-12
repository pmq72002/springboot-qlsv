package com.pmq.spring.qlsv.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.pmq.spring.qlsv.exception.AppException;
import com.pmq.spring.qlsv.exception.ErrorCode;
import com.pmq.spring.qlsv.repository.StudentRepository;
import com.pmq.spring.qlsv.request.AuthenticationRequest;
import com.pmq.spring.qlsv.request.IntrospectRequest;
import com.pmq.spring.qlsv.response.AuthenticationResponse;
import com.pmq.spring.qlsv.response.IntrospectResponse;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class AuthenticationService {

    private final StudentRepository studentRepository;

    @NonFinal
    protected static final String SINGER_KEY = "g41r5K803rCxW82cnRq5tQAuyICSgrVXY1GfDW84xjcy649QrGwsyQBDWlHLNYo6";


    public IntrospectResponse introspect(IntrospectRequest request)
            throws ParseException, JOSEException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SINGER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified =  signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expiredTime.after(new Date()))
                .build();


    }

    @Autowired
    public AuthenticationService (StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var student = studentRepository.findById(request.getStuCode())
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), student.getPassword());

        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(request.getStuCode());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(String stuCode){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(stuCode)
                .issuer("pmqchez.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e){
            log.error("Cannot read token");
            throw new RuntimeException(e);
        }

    }
}
