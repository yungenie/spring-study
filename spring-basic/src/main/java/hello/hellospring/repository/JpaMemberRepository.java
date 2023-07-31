package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * 회원가입
     * @param member 회원 객체
     * @return 회원 객체
     */
    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    /**
     * 회원 아이디 조회
     * @param id 회원 아이디
     * @return 회원 도메인 타입의 Optional 객체
     */
    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    /**
     * 회원명 조회
     * @param name 회원명
     * @return 회원 도메인 타입의 Optional 객체
     */
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    /**
     * 전체 회원 조회
     * @return 회원 도메인 타입의 리스트
     */
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
