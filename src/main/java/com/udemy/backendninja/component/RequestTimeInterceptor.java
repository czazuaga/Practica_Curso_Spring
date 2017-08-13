package com.udemy.backendninja.component;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.udemy.backendninja.repository.LogRepository;


@Component("requestTimeInterceptor")
public class RequestTimeInterceptor extends HandlerInterceptorAdapter {
	
	/** The log repository. */
	@Autowired
	@Qualifier("logRepository")
	private LogRepository logRepository;

	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(RequestTimeInterceptor.class);

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("startTime", System.currentTimeMillis());
		return true;
	}

	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String url = request.getRequestURL().toString();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = "";
		if(auth != null && auth.isAuthenticated()) {
			username = auth.getName();
		}
	    
		logRepository.save(new com.udemy.backendninja.entity.Log(Calendar.getInstance().getTime(), auth.getDetails().toString() , username, url));		
		long startTime = (long) request.getAttribute("startTime");
		LOG.info("Url to " + url + " in: "
				+ (System.currentTimeMillis() - startTime) + " ms");
	}

}
