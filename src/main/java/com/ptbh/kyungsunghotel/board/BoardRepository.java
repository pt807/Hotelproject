package com.ptbh.kyungsunghotel.board;

import com.ptbh.kyungsunghotel.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Integer> {

}
