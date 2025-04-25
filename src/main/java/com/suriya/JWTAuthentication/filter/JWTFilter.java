package com.suriya.JWTAuthentication.filter;

import com.suriya.JWTAuthentication.model.Users;
import com.suriya.JWTAuthentication.repository.UserDetailsRepository;
import com.suriya.JWTAuthentication.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")){
            token = header.substring(7);
            username =jwtUtils.extractUsername(token);
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            Optional<Users> userDetails = userDetailsRepository.findByUsername(username);
            if(userDetails.isPresent()){
                if(jwtUtils.validateToken(username,userDetails.get(),token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails.get().getUsername(), userDetails.get().getPassword(), userDetails.get().getAuthorities());
                    WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();
                    webAuthenticationDetailsSource.buildDetails(request);
                    authToken.setDetails(webAuthenticationDetailsSource);
                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}