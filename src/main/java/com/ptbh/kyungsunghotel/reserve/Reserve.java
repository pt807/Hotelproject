package com.ptbh.kyungsunghotel.reserve;

import com.ptbh.kyungsunghotel.member.Member;
import com.ptbh.kyungsunghotel.room.Room;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Reserve {
    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_no")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Reserve() {
    }

    @Builder
    public Reserve(Long id, LocalDate date, Room room, Member member) {
        this.id = id;
        this.date = date;
        this.room = room;
        this.member = member;
    }
}

