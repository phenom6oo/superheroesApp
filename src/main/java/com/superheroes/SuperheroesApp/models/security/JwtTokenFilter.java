package com.superheroes.SuperheroesApp.models.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
@Component
public class JwtTokenFilter extends GenericFilterBean {

    private static final String LOGIN_URL = "/api/auth/login";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //System.out.println("JwtTokenFilter invoked");

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();

            if (!LOGIN_URL.equals(requestURI)) {
                String token = extractTokenFromHeader(httpRequest);

                if (token != null && JwtTokenProvider.validateToken(token)) {
                    String username = JwtTokenProvider.getUsernameFromToken(token);

                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            username, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        chain.doFilter(request, response);
    }
    public String extractTokenFromHeader(HttpServletRequest request) {
        return request.getHeader("token");
    }

}
