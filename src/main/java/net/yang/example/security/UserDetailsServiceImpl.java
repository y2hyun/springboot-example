package net.yang.example.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.yang.core.entity.Account;
import net.yang.core.repository.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("username is empty");
		}
		
		Account account = this.accountRepository.findById(username);
		
		if(account == null) {
			throw new UsernameNotFoundException("account not found : " + username);
		}
		UserAccount user = new UserAccount(account);
		return user;
	}

}
