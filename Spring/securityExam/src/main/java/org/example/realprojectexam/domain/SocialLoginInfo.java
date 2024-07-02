package org.example.realprojectexam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sicial_login_info")
@Getter@Setter
public class SocialLoginInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private String socialId;
    private LocalDateTime createdAt;
    private String uuid;

    public SocialLoginInfo(){
        //소셜로그인한 사용자의 시간이랑 UUID를 만듦.
        //소셜로그인한 이후에 특정 시간까지만 추가 작업을 가능하도록 하려고 한다.
        this.createdAt=LocalDateTime.now();
        this.uuid= UUID.randomUUID().toString();
    }
}
