package com.ptbh.kyungsunghotel.member;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank(message = "아이디는 필수입니다")
    @Column(name = "member_id")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;

}