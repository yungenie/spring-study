package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    /**
     * 회원가입
     * @param member 회원 객체
     * @return 회원 객체
     */
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    /**
     * 회원 아이디 조회
     * @param id 회원 아이디
     * @return 회원 도메인 타입의 Optional 객체
     */
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * 회원명 조회
     * @param name 회원명
     * @return 회원 도메인 타입의 Optional 객체
     */
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    /**
     * 전체 회원 조회
     * @return 회원 도메인 타입의 리스트
     */
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * 전체 회원 조회
     * @return 회원저장객체(HashMap) 데이터 초기화(삭제)
     */
    public void clearStore() {
        store.clear();
    }

}
