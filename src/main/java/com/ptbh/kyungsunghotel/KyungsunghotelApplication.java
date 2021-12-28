package com.ptbh.kyungsunghotel;

import com.ptbh.kyungsunghotel.board.Board;
import com.ptbh.kyungsunghotel.board.BoardRepository;
import com.ptbh.kyungsunghotel.member.Member;
import com.ptbh.kyungsunghotel.member.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

@SpringBootApplication
public class KyungsunghotelApplication {


	public static void main(String[] args) {
		SpringApplication.run(KyungsunghotelApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(MemberRepository memberRepository, BoardRepository boardRepository) throws Exception {
		return (args) -> {
			memberRepository.save(Member.builder().loginId("test").password("test").name("홍길동").email("test@test.com").telephone("01012345678").build());
			IntStream.rangeClosed(1, 300).forEach(index ->
					boardRepository.save(Board.builder()
							.member(memberRepository.findByLoginId("test").orElse(null))
							.title("게시글 " + index)
							.content("내용" + index)
							.createTime(LocalDateTime.now())
							.build()
					)
			);
		};
	}
}