package com.redit.SecuritConfig;

import com.redit.exceptions.SpingReddiExp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userJwt= getJwtFromRequest(request);
        try {
            log.info("/*********************jwt***************** "+userJwt);
            if(StringUtils.hasText(userJwt) && jwtProvider.validateJwtToken(userJwt)){
                String username=jwtProvider.getUsernameFromJwt(userJwt);
                UserDetails userDetails=userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request,response);

        } catch (SpingReddiExp spingReddiExp) {
            spingReddiExp.printStackTrace();
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
    String bearkerToken=request.getHeader("Authorization");
    //the token is like this form Bearer <Token>
        log.info("****** this is raw ******"+bearkerToken);
        if (StringUtils.hasText(bearkerToken) && bearkerToken.startsWith("Bearer ")){
            return bearkerToken.substring(7);
        }
        else {
            return bearkerToken;
        }

    }
}
