package com.ptbh.kyungsunghotel.board;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ListForm {
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String boardNo;

    @Column
    private String writer;

    @Column
    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @Column
    private LocalDateTime createTime;

    public ListForm() {
    }

}
