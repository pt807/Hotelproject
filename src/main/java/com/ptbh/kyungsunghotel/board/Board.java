package com.ptbh.kyungsunghotel.board;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
public class Board {
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

    public Board() {
    }

    @Builder
    public Board(String writer, String title, String content, LocalDateTime createTime) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }
}


