package com.careLink.security;

import com.careLink.entity.MemberEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class AppUserDetails extends User {

    private MemberEntity memberEntity;

    public AppUserDetails(MemberEntity memberEntity, List<GrantedAuthority> authorities) {
        super(
                memberEntity.getMemberId(),
                memberEntity.getPassword(),
                (memberEntity.getWithdrawal() == 1) ? true : false, //회원탈퇴여부 1:회원유지, 2: 탈퇴
                true,
                true,
                true,
                authorities) ;
        this.memberEntity = memberEntity;
    }

}