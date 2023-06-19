package com.trueid.JsonWebToken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trueid.JsonWebToken.entity.User;
import com.trueid.JsonWebToken.exception.UserNotFoundException;
import com.trueid.JsonWebToken.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	/*
	 * @PostMapping("/register") public String addNewUser(@RequestBody User user) {
	 * return service.saveUser(user); }
	 */

	@PostMapping("/token")
	public String getToken(@RequestBody User authRequest) {
		/*
		 * Authentication authenticate = authenticationManager.authenticate( new
		 * UsernamePasswordAuthenticationToken(authRequest.getUsername(),
		 * authRequest.getPassword())); if (authenticate.isAuthenticated()) { return
		 * service.generateToken(authRequest.getUsername()); } else { throw new
		 * RuntimeException("invalid access/WrongUser"); }
		 */try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			return service.generateToken(authRequest.getUsername());
		} catch (UsernameNotFoundException e) {
			throw new UserNotFoundException("Wrong username or Password");
		} catch (BadCredentialsException e) {
			throw new UserNotFoundException("You entered Wrong Password");
		} catch (InternalAuthenticationServiceException e) {
			e.printStackTrace();
			throw new UserNotFoundException("User Not Found With This username:" + authRequest.getUsername());
		}
	}

	@GetMapping("/validate")
	public String validateToken(@RequestParam("token") String token) {
		service.validateToken(token);
		return "Token is valid";
	}
}
