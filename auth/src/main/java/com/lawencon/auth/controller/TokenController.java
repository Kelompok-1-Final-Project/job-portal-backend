package com.lawencon.auth.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.auth.dto.TokenReqDto;
import com.lawencon.auth.service.JwtService;

@RestController
@RequestMapping("token")
public class TokenController {

	@Autowired
	private JwtService jwtService;
	
	@PostMapping
	public ResponseEntity<String> getToken(@RequestBody TokenReqDto tokenDto){
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 1);
		
		final Map<String, Object> claims = new HashMap<>();
		claims.put("exp", cal.getTime());
		claims.put("id",tokenDto.getId());
		final String response = jwtService.generateJwt(claims);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@PostMapping("/validate")
	public ResponseEntity<Boolean> validateToken(){
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
}
				