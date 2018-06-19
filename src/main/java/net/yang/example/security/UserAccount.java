package net.yang.example.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import net.yang.core.entity.Account;

public class UserAccount implements UserDetails {

	private static final long serialVersionUID = -1934120484832016711L;
	
	private Account account;
	private Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

	public UserAccount(Account account) {
		this.account = account;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return Account.STATUS.VALID.equals(account.getStatus()) ? true : false;
	}

}
