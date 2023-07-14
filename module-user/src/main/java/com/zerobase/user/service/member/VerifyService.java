package com.zerobase.user.service.member;

import com.zerobase.common.domain.member.Member;
import com.zerobase.common.repository.member.MemberRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VerifyService {

	private final MemberRepository memberRepository;

	@Transactional
	public void setMemberVerifyInit(Long id, String code) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없음"));

		member.setVerificationCode(code);
		member.setExpired(LocalDateTime.now().plusDays(1L));
	}
	@Transactional
	public void changeMemberVerify(String email, String code) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없음"));

		validateCheck(member, code);
		member.setVerify(true);
	}

	private void validateCheck(Member member, String code) {
		if (member.isVerify()) {
			throw new RuntimeException("이미 검증이 완료된 이메일");
		} else if (!code.equals(member.getVerificationCode())) {
			throw new RuntimeException("코드 불일치");
		} else if (LocalDateTime.now().isAfter(member.getExpired())) {
			throw new RuntimeException("검증 시간 초과");
		}
	}
}
