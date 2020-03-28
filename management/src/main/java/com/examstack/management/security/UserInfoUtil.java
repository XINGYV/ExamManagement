package com.examstack.management.security;

import org.springframework.security.core.context.SecurityContextHolder;


public class UserInfoUtil {
	public static UserInfo getUserInfo(){
		UserInfo userInfo=(UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userInfo;
	}

}
