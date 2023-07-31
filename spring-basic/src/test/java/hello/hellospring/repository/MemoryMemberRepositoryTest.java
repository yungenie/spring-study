package hello.hellospring.repository;

import hello.hellospring.domain.Member;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Test 실행순서는 보장이 안됩니다. 서로 순서 상관없고 의존관계 없이 설계가 되어야 합니다.
 * 메서드들 각각 독립해서 돈 후에 공용 데이터를 깔끔하게 지워줘야 다른 메서드에 영향이 안갑니다.
 * public 안해도 됩니다...
 */
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
        //System.out.println("(result == member = " + (result == member);
        //Assertions.assertEquals(member, result);
        //assertThat(actual 실제값(계산), expected 기대값(정답))
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);

    }
}