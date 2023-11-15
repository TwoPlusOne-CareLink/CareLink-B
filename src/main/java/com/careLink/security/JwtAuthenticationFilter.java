package com.careLink.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final UserDetailsService userDetailsService;
    private final JwrUtil jwrUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter 들어옴");
        //AccessToken 얻기
        String accessToken = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        //Authorization: Bearer token
        // Request의 Header에서 token 값 가져오기
        String bearerToken = httpServletRequest.getHeader("Authorization");

        log.info("1");
        if(bearerToken != null && bearerToken.contains("Bearer")) {
            accessToken = bearerToken.substring(7);
        }
        else {
            accessToken = request.getParameter("accessToken");
        }

        log.info("2");

        //유효한 토큰인지 확인
        if(accessToken != null && !accessToken.trim().equals("")) {
            log.info("3");
            if(jwrUtil.validateToken(accessToken)) {
                log.info("4");
                //토큰에서 userId 얻기
                String id = jwrUtil.getId(accessToken); //토큰에서 아이디값 가져오는 메소드 실행해서 아이디 출력
                //DB에서 userId에 해당하는 정보를 가져오기
                UserDetails userDetails = userDetailsService.loadUserByUsername(id);
                //인증 객체 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(id, "", userDetails.getAuthorities());
                //Spring Security에 인증 객체 등록
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        log.info("넌 머냐");
        chain.doFilter(request, response);

    }
}