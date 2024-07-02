package org.example.filterexam.filterExam4.service;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.example.filterexam.filterExam4.entity.Role;
import org.example.filterexam.filterExam4.entity.User;
import org.example.filterexam.filterExam4.repository.RoleRepository;
import org.example.filterexam.filterExam4.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    //회원가입
    @Transactional(readOnly = false)
    public User registerUser(User user) {
        //Role 정보를 가져와서 User에 같이 넣어 줘야 함
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}