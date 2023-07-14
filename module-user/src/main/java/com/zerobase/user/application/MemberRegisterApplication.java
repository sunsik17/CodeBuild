package com.zerobase.user.application;

import com.zerobase.client.mailgun.dto.MailForm;
import com.zerobase.common.domain.member.Member;
import com.zerobase.common.domain.member.dto.RegisterMember;
import com.zerobase.client.mailgun.MailgunClient;
import com.zerobase.user.service.member.MemberRegisterService;
import com.zerobase.user.service.member.VerifyService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRegisterApplication {

	private static final String VERIFICATION_MAIL_SUBJECT = "Verification Email!";

	private final MemberRegisterService memberRegisterService;
	private final VerifyService verifyService;
	private final MailgunClient mailgunClient;

	@Value("${mailgun.owner-email}")
	private String ownerEmail;
	public void register(RegisterMember.Request request) {

		Member member = memberRegisterService.register(request);
		String code = creatRandomCode();
		mailgunClient.sendEmail(MailForm.builder()
				.from(ownerEmail)
				.to(request.getEmail())
				.subject(VERIFICATION_MAIL_SUBJECT)
				.text(getVerificationEmailBody(
					member.getEmail(), member.getName(), code))
			.build());

		verifyService.setMemberVerifyInit(member.getId(), code);
	}
	private String creatRandomCode() {
		return RandomStringUtils.random(10, true, true);
	}
	private String getVerificationEmailBody(String email, String name, String code) {
		return "Hello " + name + "! \n Please Click Link for verification\n\n"
			+ "http://localhost:8082/members/verify?email="
			+ email
			+ "&code="
			+ code;
	}
}
