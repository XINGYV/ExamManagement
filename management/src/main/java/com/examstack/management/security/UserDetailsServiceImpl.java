package com.examstack.management.security;

import com.exammanagement.common.domain.user.User;
import com.examstack.management.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserInfo userInfo;
    @Autowired
    public UserMapper userMapper;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        User user = userMapper.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名无效");
        }
        //roles=角色代码
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles());
        userInfo = new UserInfo(username, user.getPassword(), true, true, true, true, authorities);
        userInfo.setUserid(user.getUserId());
        userInfo.setRolesName(user.getRoles());
        return userInfo;
    }


}
