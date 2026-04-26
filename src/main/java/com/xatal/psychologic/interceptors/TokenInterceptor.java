package com.xatal.psychologic.interceptors;

import com.xatal.psychologic.Structures.Rol;
import com.xatal.psychologic.util.TokenUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class TokenInterceptor implements Filter {
	private final TokenUtils tokenUtils;

	private final String BEARER_PREFIX = "Bearer ";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (!isPostUsuario(httpRequest) && !isLogin(httpRequest)) {
			if (isTokenValid(httpRequest)) {
				chain.doFilter(request, response);
			} else {
				httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean isTokenValid(HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (token != null) {
			token = token.replace(BEARER_PREFIX, "");
			return !tokenUtils.isExpired(token) && tokenUtils.getTokenClaims(token) != null;
		}
		return false;
	}

	private boolean isPostUsuario(HttpServletRequest request) {
		String path = request.getServletPath();
		boolean isUserPath = Arrays.stream(Rol.values())
			.anyMatch(rol -> path.toLowerCase().contains(rol.toString().toLowerCase()));

		boolean isPost = request.getMethod().equals(HttpMethod.POST.name());
		return isUserPath && isPost;
	}

	private boolean isLogin(HttpServletRequest request) {
		return request.getServletPath().contains("login");
	}
}
