package com.careLink.security;

import com.careLink.Member.vo.MemberDto;
import com.careLink.Member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //로그인한 아이디 값을 가지고 아이디가 존재하면 입력한 아이디의 회원정보를 db에서 불러온다.
        MemberDto member = memberMapper.findById(username).orElse(null);

        //아이디가 존재하는지 여부 확인후 입력한 비밀번호와 아이디로 불러온 회원정보의 비밀번호 값을 대조
        if(member == null) {
            log.info("아이디가 존재하지 않습니다.");
            throw new UsernameNotFoundException(username); //아이디가 없을때 예외처리
        }

        log.info("아이디 존재");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getRole())); //회원정보에서 권한정보를 가져와서 담는다.

        AppUserDetails userDetails = new AppUserDetails(member, authorities);
       
        return userDetails;
    }
}
