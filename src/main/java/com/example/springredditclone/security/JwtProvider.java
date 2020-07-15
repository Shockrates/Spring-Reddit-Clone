package com.example.springredditclone.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

	public String generateToken(Authentication authenticate) {
		return null;
	}

}
