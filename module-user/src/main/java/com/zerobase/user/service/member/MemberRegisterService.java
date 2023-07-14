package com.zerobase.user.service.member;

import com.zerobase.common.domain.member.Member;
import com.zerobase.common.domain.member.dto.RegisterMember;
import com.zerobase.common.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRegisterService {

	private final MemberRepository memberRepository;
	public Member register(RegisterMember.Request request) {

		registerValidationCheck(request.getEmail());

		return memberRepository.save(Member.builder()
					.name(request.getName())
					.phoneNumber(request.getPhoneNumber())
					.password(request.getPassword())
					.email(request.getEmail())
					.verify(false)
				.build());
	}
	private void registerValidationCheck(String email) {
		if (memberRepository.findByEmail(email).isPresent()){
			throw new RuntimeException("해당이메일 이미존재");
		}
	}
}
