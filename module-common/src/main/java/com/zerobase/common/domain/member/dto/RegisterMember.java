package com.zerobase.common.domain.member.dto;

import lombok.Getter;
public class RegisterMember {
	@Getter
	public static class Request {
		private String name;
		private String email;
		private String password;
		private String phoneNumber;
	}

}
