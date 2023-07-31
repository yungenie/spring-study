package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    /**
     * 회원명 조회 <p>
     * ※ 나머지 회원 관련 메소드는 스프링 데이터 Jpa가 JpaRepository, MemberRepository 인터페이스를 보고 자동으로 구현해줍니다.
     * @param name
     * @return 회원 도메인 타입의 Optional 객체
     */
    //@Override
    //Optional<Member> findByName(String name);

}
