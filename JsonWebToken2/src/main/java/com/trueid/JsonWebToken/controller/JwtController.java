
package com.trueid.JsonWebToken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trueid.JsonWebToken.entity.User;
import com.trueid.JsonWebToken.helper.JwtUtil;
import com.trueid.JsonWebToken.response.JwtResponse;
import com.trueid.JsonWebToken.services.CustomUserDetailsService;

@RestController
public class JwtController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody User user) {
		System.err.println("user" + user);
		try {
			this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (UsernameNotFoundException e) {
			throw new RuntimeException("bad request");
		} catch (BadCredentialsException e) {
			System.err.println("in exception");
			e.printStackTrace();
		}

		UserDetails loadUserByUsername = this.customUserDetailsService.loadUserByUsername(user.getUsername());
		System.out.println(loadUserByUsername);
		String generateToken = this.jwtUtil.generateToken(loadUserByUsername);
		System.err.println();
		return ResponseEntity.ok(new JwtResponse(generateToken));
	}
}
