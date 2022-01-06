package com.ptbh.kyungsunghotel.room;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Room {
    @Id
    @Column
    private String roomNo;

    @Column
    private String state;

    public Room(){
    }

    @Builder
    public Room(String roomNo, String state){
        this.roomNo = roomNo;
        this.state = state;
    }
}
