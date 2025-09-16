package com.pmq.spring.qlsv.controller;

import com.nimbusds.jose.JOSEException;
import com.pmq.spring.qlsv.dto.request.AuthenticationRequest;
import com.pmq.spring.qlsv.dto.request.IntrospectRequest;
import com.pmq.spring.qlsv.dto.response.ApiResponse;
import com.pmq.spring.qlsv.dto.response.AuthenticationResponse;
import com.pmq.spring.qlsv.dto.response.IntrospectResponse;
import com.pmq.spring.qlsv.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("api/auth")

public class AuthenticationController {

    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

}
