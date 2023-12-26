package com.careLink.security;

import com.careLink.auth.mapper.AuthMapper;
import com.careLink.entity.MemberEntity;
import com.careLink.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //로그인한 아이디 값을 가지고 아이디가 존재하면 입력한 아이디의 회원정보를 db에서 불러온다.
        MemberEntity member = authMapper.findById(username).orElse(null);
        log.info("123");
        //아이디가 존재하는지 여부 확인후 입력한 비밀번호와 아이디로 불러온 회원정보의 비밀번호 값을 대조
        if(member == null) {
            throw new ErrorException(HttpStatus.NOT_FOUND.value(), "아이디가 없다"); //로그인 아이디가 없다.
        }

        log.info("아이디 존재");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole())); //회원정보에서 권한정보를 가져와서 담는다.

        AppUserDetails userDetails = new AppUserDetails(member, authorities);

        return userDetails;
    }
}
