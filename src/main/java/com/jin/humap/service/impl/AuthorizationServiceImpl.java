package com.jin.humap.service.impl;

import com.jin.humap.service.AuthorizationService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public Set<String> findByAccountId(Long id) {
        return null;
    }
}
