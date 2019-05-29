package com.sxbang.friday.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class MyPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        boolean accessable = false;
        if(authentication.getPrincipal().toString().compareToIgnoreCase("anonymousUser") != 0){
            String privilege = targetDomainObject + "-" + permission;
            for(GrantedAuthority authority : authentication.getAuthorities()){
                if(privilege.equalsIgnoreCase(authority.getAuthority())){
                    accessable = true;
                    break;
                }
            }

            return accessable;
        }

        return accessable;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
