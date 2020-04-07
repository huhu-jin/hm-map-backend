package com.jin.humap.service;

import java.util.Set;

public interface AuthorizationService {

    Set<String> findByAccountId(Long id);
}
