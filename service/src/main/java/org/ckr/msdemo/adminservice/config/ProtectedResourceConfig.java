package org.ckr.msdemo.adminservice.config;

import org.ckr.msdemo.authserver.cyrpto.AsymmetricVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.Base64Utils;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Administrator on 2017/9/3.
 * This is obsoleted. Should use @EnableJWTTokenAuthentication
 */
//@EnableResourceServer
//@Configuration
//public class ProtectedResourceConfig extends ResourceServerConfigurerAdapter {
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenStore(new JwtTokenStore(this.jwtAccessTokenConverter()));
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();
//
//
//    }
//
//
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter()  {
//
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        String keyStr = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEPS0uGZGdksBcNzRuLq2/6RD7gQOzZi0WhcVKCF/8ePlXg991xlOHEy7IP9ZWA+svWCVM22cts7v6hOaDoSFp7A==";
//
//        byte[] keyBytes = Base64Utils.decodeFromString(keyStr);
//        try {
//            X509EncodedKeySpec spec =
//                    new X509EncodedKeySpec(keyBytes);
//            KeyFactory kf = KeyFactory.getInstance("EC");
//            PublicKey publicKey = kf.generatePublic(spec);
//
//            converter.setVerifier(new AsymmetricVerifier(publicKey, "SHA256withECDSA"));
//        } catch (Exception ie) {
//            throw new RuntimeException("", ie);
//        }
//        return converter;
//    }
//
//
//
//}
