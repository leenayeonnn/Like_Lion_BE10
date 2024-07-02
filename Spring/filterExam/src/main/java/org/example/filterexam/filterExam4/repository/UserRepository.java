package org.example.filterexam.filterExam4.repository;

import org.example.filterexam.filterExam4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //id = 의미없는 값 => username 이용
    User findByUsername(String username);
}
