
package com.ptbh.kyungsunghotel.member;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Member {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "아이디는 필수입니다")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    @NotBlank(message = "이메일은 필수입니다")
    private String email;

    @NotBlank(message = "전화번호는 필수입니다")
    private String telephone;

    public Member() {
    }

    @Builder
    public Member(String loginId, String password, String name, String email, String telephone) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
    }
}
