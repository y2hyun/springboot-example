package net.yang.example.service.impl;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.yang.core.entity.Account;
import net.yang.core.repository.AccountRepository;
import net.yang.example.security.UserAccount;
import net.yang.example.service.AccountService;
import net.yang.example.vo.RegistrationVo;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public UserDetails createAccount(RegistrationVo vo) {
		Account account = new Account();
		account.setId(vo.getId());
		account.setPassword(this.passwordEncoder.encode(vo.getPassword()));
		account.setEmail(vo.getEmail());
		account.setStatus(Account.STATUS.VALID);
		account.setCreateDatetime(Calendar.getInstance());
		account.setCreator(0L);
		return new UserAccount(this.accountRepository.saveAndFlush(account));
	}

	@Override
	public boolean isExistsUser(String username) {
		if(StringUtils.isBlank(username)) {
			return true;
		}
		return this.accountRepository.countById(username) > 0;
	}

	@Override
	public boolean doAuthenticationWithoutPassword(UserDetails userAccount, HttpServletRequest request) {
		if(userAccount == null) {
			return false;
		}
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userAccount, null, userAccount.getAuthorities());
		if(request != null) {
			authToken.setDetails(new WebAuthenticationDetails(request));
		}
		SecurityContextHolder.getContext().setAuthentication(authToken);
		return true;
	}
}
