package com.example.springredditclone.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import com.example.springredditclone.exception.SpringRedditException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

	private KeyStore keyStore;

	@PostConstruct
	public void init(){
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray() );
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new SpringRedditException("Exception occurred while loading keystore");
		}
	}

	public String generateToken(Authentication authenticate) {
		return null;
	}

	private 

}
