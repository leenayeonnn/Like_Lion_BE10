package org.example.filterexam.filterExam2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public void list(){
        User user = UserContext.getUser();

        // user 유무에 따라 비지니스가 처리 도리 수 있음
        System.out.println(user);
    }
}
