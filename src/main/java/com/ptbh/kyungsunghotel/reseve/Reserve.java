package com.ptbh.kyungsunghotel.reseve;

import com.ptbh.kyungsunghotel.member.Member;
import com.ptbh.kyungsunghotel.room.Room;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Reserve {
    @Id
    @Column
    private Long id;

    @Column
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_no")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}

