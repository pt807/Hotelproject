package com.ptbh.kyungsunghotel.reserve;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    List<Reserve> findAllByDateBetween(LocalDate checkIn, LocalDate checkOut);
}
