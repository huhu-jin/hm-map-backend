package com.jin.humap.controller;

import com.jin.humap.dto.ApiResponse;
import com.jin.humap.dto.AuthorizeDTO;
import com.jin.humap.entity.Account;
import com.jin.humap.exception.ExceptionCode;
import com.jin.humap.request.authorize.LoginRequest;
import com.jin.humap.service.AccountService;
import com.jin.humap.service.AuthorizationService;
import com.jin.humap.until.JWTUtil;
import com.jin.humap.until.bcrypt.BCryptPasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor @Slf4j
public class LoginController {

    private final ApplicationContext applicationContext;

    private final AccountService accountService;

    private final AuthorizationService authorizationService;

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ApiResponse login(@RequestBody @Valid LoginRequest request){
        Account account = accountService.findByUsername(request.getUsername());
        if (account == null || !bCryptPasswordEncoder.matches(request.getPassword(),account.getPassword())) {
            return ApiResponse.ERROR(ExceptionCode.LOGIN_ERROR);
        }
        String token =  JWTUtil.doGenerateToken(account);
        Set<String> authorization = authorizationService.findByAccountId(account.getId());
        AuthorizeDTO authorizeDTO = AuthorizeDTO.builder().authorizes(authorization).token(token).build();
        return ApiResponse.OK(authorizeDTO);
    }


}
