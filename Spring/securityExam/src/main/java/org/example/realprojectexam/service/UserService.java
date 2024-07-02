package org.example.realprojectexam.service;

import lombok.RequiredArgsConstructor;
import org.example.realprojectexam.domain.Role;
import org.example.realprojectexam.domain.User;
import org.example.realprojectexam.repository.RoleRepository;
import org.example.realprojectexam.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //private final PasswordEncoder passwordEncoder;

//    @Transactional
//    //회원가입
//    public User registUser(User user){
//        //role설정
//        Role userRole = roleRepository.findByName("USER");
//        user.setRoles(Collections.singleton(userRole));
//        //패스워드 인코딩 (시큐리티는 무조건 인코딩 해줘야한다.). password암호화
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        return userRepository.save(user);
//    }

    @Transactional(readOnly = true)
    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }


    public Optional<User> findByProviderAndSocialId(String provider , String socialId){
        return userRepository.findByProviderAndSocialId(provider,socialId);
    }

    @Transactional(readOnly = false)
    public User saveUser(String username, String name, String email, String socialId, String provider, PasswordEncoder passwordEncoder){
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setEmail(email);
        user.setSocialId(socialId);
        user.setProvider(provider);
        user.setPassword(passwordEncoder.encode("")); // 비밀번호는 소셜 로그인 사용자의 경우 비워둡니다.
        return userRepository.save(user);
    }
}
