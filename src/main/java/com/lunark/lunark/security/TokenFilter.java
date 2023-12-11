package com.lunark.lunark.security;

import com.lunark.lunark.util.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenUtils tokenUtil;

    public TokenFilter(TokenUtils tokenUtil, UserDetailsService userDetailsService) {
        this.tokenUtil = tokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        if(request.getRequestURL().toString().contains("/api")) {
            String requestTokenHeader = request.getHeader("Authorization");
            String username = null;
            String token = null;

            if (requestTokenHeader != null && requestTokenHeader.contains("Bearer")) {
                token = requestTokenHeader.substring(requestTokenHeader.indexOf("Bearer") + 7);
                try {
                    username = tokenUtil.getUsernameFromToken(token);
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (tokenUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT token.");
                } catch (ExpiredJwtException e) {
                    System.out.println("Token has expired.");
                } catch (io.jsonwebtoken.MalformedJwtException e) {
                    System.out.println("Bad JWT token.");
                }
            } else {
                logger.warn("JWT token does not exist");
            }
        }
        chain.doFilter(request, response);
    }
}