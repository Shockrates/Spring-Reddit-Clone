package com.example.springredditclone.security;

import com.example.springredditclone.exception.SpringRedditException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

	private KeyStore keyStore;
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			//InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springreddit.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray());
			//keyStore.load(new FileInputStream("/springblog.jks"), "secret".toCharArray());
			logger.info("Pivate Key from init()= "+keyStore.getKey("springreddit", "secret".toCharArray()));
			
			
		} catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | UnrecoverableKeyException e) {
			throw new SpringRedditException("Exception occurred while loading keystore");
		}
	}

	private PrivateKey getPrivateKey() {
		try {
			//System.out.println("Pivate Key from getPrivateKey= "+keyStore.getKey("springreddit", "secret".toCharArray()));
			return (PrivateKey)keyStore.getKey("springreddit", "secret".toCharArray());
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


	public boolean validateToken(String jwt) {
		parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return false;
	}

	private PublicKey getPublicKey() {
		try {
			return keyStore.getCertificate("springreddit").getPublicKey();
		} catch (KeyStoreException e) {
			throw new SpringRedditException("Exception occured while retrieving public key from keystore");
		}
	
	}

	public String getUsernameFromJWT(String token){
		Claims claims = parser()
				.setSigningKey(getPublicKey())
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();

	}

	
	
	

	

	
}
