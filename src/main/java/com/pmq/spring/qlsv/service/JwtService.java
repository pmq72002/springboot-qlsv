package com.pmq.spring.qlsv.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import com.pmq.spring.qlsv.exception.AppException;
import com.pmq.spring.qlsv.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;

import java.text.ParseException;
import java.util.Date;

public class JwtService {
    @Value("${jwt.signerKey}")
    private String signerKey;

    public boolean isTokenValid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

            if (expiryTime.before(new Date())) {
                throw new AppException(ErrorCode.TOKEN_EXPIRED);
            }

            // verify signature
            JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
            return signedJWT.verify(verifier);

        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.UNREAD_TOKEN);
        }
    }
}
