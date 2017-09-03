package org.ckr.msdemo.adminservice.service;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.io.Serializable;
import javax.transaction.NotSupportedException;

/**
 * Created by Administrator on 2017/9/3.
 */
public class SecurityEvaluatorService implements PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        if ("public".equals(permission)) {
            return true;
        }

        if (!(authentication instanceof OAuth2Authentication)) {
            return false;
        }

        OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;



        System.out.println("authentication \n" + authentication);
        System.out.println("hasPermission \n" + targetDomainObject);
        System.out.println(permission);

        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new RuntimeException("This method is not suppored.");
    }
}
