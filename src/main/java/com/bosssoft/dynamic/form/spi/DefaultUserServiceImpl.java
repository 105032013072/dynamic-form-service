 package com.bosssoft.dynamic.form.spi;

import org.springframework.stereotype.Service;

@Service
 public class DefaultUserServiceImpl implements UserService{

    @Override
    public String getCurrentUserId() {
        
         return "user0001";
    }

}
