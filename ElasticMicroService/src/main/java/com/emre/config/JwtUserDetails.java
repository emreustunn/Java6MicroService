package com.emre.config;

import com.emre.repository.entity.UserProfile;
import com.emre.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetails implements UserDetailsService {

    @Autowired
    UserProfileService userProfileService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails getUserByAuthId(Long authid){
        Optional<UserProfile> userProfile = userProfileService.findByAuthId(authid);
        if (userProfile.isEmpty()) return null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("Admin"));
        authorities.add(new SimpleGrantedAuthority("OzelMusteri"));
        authorities.add(new SimpleGrantedAuthority("VIP"));
        return User.builder()
                .username(userProfile.get().getUsername())
                .accountLocked(false)
                .accountExpired(false)
                .authorities(authorities)
                .build();
    }
}
