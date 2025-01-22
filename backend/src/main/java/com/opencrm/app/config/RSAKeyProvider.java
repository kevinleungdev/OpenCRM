package com.opencrm.app.config;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.jwk.RSAKey;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RSAKeyProvider {

    private final Resource publicKeyResource;
    private final Resource privateKeyResource;

    private RSAKey rsaKey;

    public RSAKeyProvider(@Value("${publicKey}") Resource publicKeyResource,
            @Value("${privateKey}") Resource privateKeyResource) {
        this.publicKeyResource = publicKeyResource;
        this.privateKeyResource = privateKeyResource;
    }

    public RSAKey getRsaKey() {
        return rsaKey;
    }

    private RSAPublicKey getPublicKey() throws Exception {
        log.debug("Public key: {}", publicKeyResource.getURI());

        byte[] keyBytes = publicKeyResource.getInputStream().readAllBytes();
        String publicKeyPem = new String(keyBytes)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("\\s+", "")
                .replaceAll("\\r\\n|\\n|\\r", "");
        byte[] decoded = Base64.getDecoder().decode(publicKeyPem.trim());

        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

    private RSAPrivateKey getPrivateKey() throws Exception {
        log.debug("Private key: {}", privateKeyResource.getURI());

        byte[] keyBytes = privateKeyResource.getInputStream().readAllBytes();
        String privateKeyPem = new String(keyBytes)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("\\s+", "")
                .replaceAll("\\r\\n|\\n|\\r", "");

        byte[] decoded = Base64.getDecoder().decode(privateKeyPem.trim());

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    @PostConstruct
    private void generateRsaKey() throws Exception {
        RSAPublicKey publicKey = getPublicKey();
        RSAPrivateKey privateKey = getPrivateKey();

        this.rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }
}
