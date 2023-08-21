package com.lawencon.jobportal.candidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lawencon.jobportal.candidate.dto.TokenReqDto;
import com.lawencon.jobportal.candidate.dto.user.UserLoginReqDto;
import com.lawencon.jobportal.candidate.dto.user.UserLoginResDto;
import com.lawencon.jobportal.candidate.service.UserService;

@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private RestTemplate restTemplate;

	@PostMapping
	public ResponseEntity<?> login(@RequestBody final UserLoginReqDto user) {
		final Authentication auth = new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword());
		
		authenticationManager.authenticate(auth);
		final UserLoginResDto userGet = userService.login(user);
		
		final String tokenURl = "http://localhost:8082/token";
		
		final TokenReqDto tokenReqDto = new TokenReqDto();
		tokenReqDto.setId(userGet.getUserId());	
		
		final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		
		final RequestEntity<TokenReqDto> token = RequestEntity.post(tokenURl).headers(headers).body(tokenReqDto);
		
		final ResponseEntity<String> response = restTemplate.exchange(token,String.class);

		final UserLoginResDto loginRes = new UserLoginResDto();
		loginRes.setUserId(userGet.getUserId());
		loginRes.setUserName(userGet.getUserName());
		loginRes.setPhotoId(userGet.getPhotoId());
		loginRes.setToken(response.getBody());
		
		return new ResponseEntity<>(loginRes, HttpStatus.OK);
	}
}
