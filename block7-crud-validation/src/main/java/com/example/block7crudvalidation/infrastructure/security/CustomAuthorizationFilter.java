package com.example.block7crudvalidation.infrastructure.security;

import com.example.block7crudvalidation.infrastructure.security.authorities.BaseGrantedAuthority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private String secretJwtKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {


        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "");
        try {

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(this.secretJwtKey).parseClaimsJws(token);
            String username = claimsJws.getBody().getSubject();

            List<BaseGrantedAuthority> authorities = null;
            Object authoritiesObject = claimsJws.getBody().get("authorities");
            if(authoritiesObject != null) {
                authorities = ((List<String>) authoritiesObject).stream().map(BaseGrantedAuthority::new).toList();
            }


            Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }
    }
}