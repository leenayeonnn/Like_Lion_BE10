package org.example.realprojectexam.security;

import lombok.RequiredArgsConstructor;
import org.example.realprojectexam.domain.User;
import org.example.realprojectexam.repository.UserRepository;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    //인증.인가를 진행할때 필요한 유저와 권한에대해 세팅해주는 역할
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }
        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username); //시큐리티에서 제공하는 User객체를 사용해 username을 넣어준다.
        userBuilder.password(user.getPassword());//비밀번호넣어준다.
        userBuilder.roles(user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new));//역할도 알려준다.
        return userBuilder.build();
    }
}
