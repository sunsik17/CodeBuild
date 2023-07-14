package com.zerobase.user.controller.member;

import com.zerobase.common.domain.member.dto.RegisterMember;
import com.zerobase.user.application.MemberRegisterApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/register")
public class MemberRegisterController {

	private final MemberRegisterApplication memberRegisterApplication;
	@PostMapping
	public ResponseEntity<String> register(
		@RequestBody RegisterMember.Request request) {
		memberRegisterApplication.register(request);
		return ResponseEntity.ok("회원가입 완료");
	}
}
