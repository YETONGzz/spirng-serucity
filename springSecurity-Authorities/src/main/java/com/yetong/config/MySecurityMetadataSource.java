package com.yetong.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;

import java.util.Collection;

public class MySecurityMetadataSource implements SecurityMetadataSource {
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
