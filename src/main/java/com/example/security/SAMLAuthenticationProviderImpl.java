package com.example.security;

import java.util.Date;

import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLCredential;

public class SAMLAuthenticationProviderImpl extends SAMLAuthenticationProvider {

    @Override
    protected Date getExpirationDate(SAMLCredential credential) {
        return null;
    }

}
