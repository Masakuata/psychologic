package com.xatal.psychologic.interceptors;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.xatal.psychologic.configuration.InterceptorConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class PasswordFilter implements jakarta.servlet.Filter {
    private final String BEARER_PREFIX = "Bearer ";
    private final InterceptorConfig interceptorConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // if (!isPasswordValid(httpRequest)) {
        //     httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //     return;
        // }

        chain.doFilter(request, response);
    }

    private boolean isPasswordValid(HttpServletRequest request) {
        String password = getHeaderPassword(request);
        return password != null && password.equals(interceptorConfig.getPassword());

    }

    private String getHeaderPassword(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && !header.isEmpty() && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length()).trim();
        }
        return null;
    }
}
