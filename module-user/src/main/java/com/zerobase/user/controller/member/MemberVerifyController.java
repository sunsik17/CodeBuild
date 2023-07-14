package com.zerobase.user.controller.member;

import com.zerobase.user.service.member.VerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/verify")
public class MemberVerifyController {

	private final VerifyService verifyService;
	@GetMapping
	public ResponseEntity<String> checkCode(@RequestParam String email, @RequestParam String code) {
		verifyService.changeMemberVerify(email, code);
		return ResponseEntity.ok("인증완료");
	}
}
