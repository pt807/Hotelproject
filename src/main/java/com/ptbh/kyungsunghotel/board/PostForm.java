package com.ptbh.kyungsunghotel.board;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class PostForm {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardNo;

    @Column
    private String writer;


    @Column
    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @Column
    @NotBlank(message = "내용은 필수입니다")
    private String content;

    @Column
    private LocalDateTime createTime;

    public PostForm() {
    }

}
