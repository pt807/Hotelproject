package com.ptbh.kyungsunghotel.board;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
public class PostForm {
    @Column
    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @Column
    @NotBlank(message = "내용은 필수입니다")
    private String content;

    public PostForm() {
    }

}
