package com.example.springredditclone.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import com.example.springredditclone.exception.SpringRedditException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

	private KeyStore keyStore;

	@PostConstruct
	public void init(){
		try {
			keyStore = KeyStore.getInstance("JKS");
			//InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			InputStream resourceAsStream = new FileInputStream("/springblog.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray());
			//keyStore.load(new FileInputStream("/springblog.jks"), "secret".toCharArray());
			
			
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new SpringRedditException("Exception occurred while loading keystore");
		}
	}

	private PrivateKey getPrivateKey() {
		try {
			System.out.println("Pivate Key from getPrivateKey= "+keyStore.getKey("springblog", "secret".toCharArray()));
			return (PrivateKey)keyStore.getKey("springblog", "secret".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException |UnrecoverableKeyException e) {
			throw new SpringRedditException("Exception occured while retrieving public key from keystore");
			
		} 
		
	}
	

	public String generateToken(Authentication authentication) {
		org.springframework.security.core.userdetails.User principal = (User)authentication.getPrincipal();
		//System.out.println("Rivate Key = "+getPrivateKey());
		return Jwts.builder()
		.setSubject(principal.getUsername())
		.signWith(getPrivateKey())
		.compact();
	}
	
	

	

	
}
