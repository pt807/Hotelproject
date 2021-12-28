package com.ptbh.kyungsunghotel.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
