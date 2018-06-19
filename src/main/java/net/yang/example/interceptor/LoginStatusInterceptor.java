package net.yang.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;
import net.yang.example.security.UserAccount;

@Slf4j
public class LoginStatusInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// ログ出力
        String uri = request.getRequestURI();
        String httpMethod = request.getMethod();
        String sessionId = request.getSession().getId();
        UserAccount account = getUserAccount();
        String memberId = (account != null) ? account.getUsername() : "none";
        log.info("[{}][{}][{}][{}][{}]", httpMethod, uri, sessionId, memberId, account != null ? account.getAuthorities() : "");
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception { 
		
		if(modelAndView == null) {
			return;
		}
		
		String viewName = modelAndView.getViewName();
		if(StringUtils.isEmpty(viewName)) {
			return;
		}
		if(viewName.startsWith("redirect:") || viewName.startsWith("forward:")) {
			return;
		}
		
		boolean isLogin = getUserAccount() != null;
		modelAndView.addObject("login", isLogin);
	}
	
	private UserAccount getUserAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			if(authentication.getPrincipal() instanceof UserAccount) {
				return UserAccount.class.cast(authentication.getPrincipal());
			}
		}
		
		return null;
	}
}
