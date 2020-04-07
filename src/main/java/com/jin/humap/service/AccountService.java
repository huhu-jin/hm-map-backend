package com.jin.humap.service;

import com.jin.humap.entity.Account;

import java.util.Set;

public interface AccountService {


    Account findByUsername(String username);
}
