package com.ptbh.kyungsunghotel.boards;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
    private LocalDateTime creatTime;

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(LocalDateTime creatTime) {
        this.creatTime = creatTime;
    }
}
