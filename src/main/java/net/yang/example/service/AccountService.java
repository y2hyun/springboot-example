package net.yang.example.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import net.yang.example.vo.RegistrationVo;

public interface AccountService {

	/**
	 * ユーザー登録
	 * @param registrationVo
	 */
	UserDetails createAccount(RegistrationVo registrationVo);
	
	/**
	 * ユーザー登録チェック
	 * @param username
	 * @return
	 */
	boolean isExistsUser(String username);
	
	/**
	 * パスワードなしで認証を行う。（ユーザー登録後、自動認証用）
	 * @param userAccount
	 * @param request
	 */
	boolean doAuthenticationWithoutPassword(UserDetails userAccount, HttpServletRequest request);
	
}
