package com.careLink.security;

import com.careLink.Member.vo.MemberDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter@Setter
public class AppUserDetails extends User {

    private MemberDto memberDto;

    public AppUserDetails(MemberDto memberDto, List<GrantedAuthority> authorities) {
        super(
                memberDto.getId(),
                memberDto.getPassword(),
                (memberDto.getWithdrawal() == 1) ? true : false, //회원탈퇴여부 1:회원유지, 2: 탈퇴
                true,
                true,
                true,
                authorities) ;
        this.memberDto = memberDto;
    }

}