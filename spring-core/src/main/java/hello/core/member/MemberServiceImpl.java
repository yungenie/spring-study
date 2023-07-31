package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    /** DIP 적용 전 */
    /* private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }*/

    /** DIP 적용 후 */
    // MemberServiceImpl는 MemberRepository 인터페이스에만 의존합니다. 즉, 추상화에만 의존함. DIP 원칙 지킴
    private final MemberRepository memberRepository;

    // 구현객체를 new로 선언하는 게 아닌 memberRepository에 구현체가 뭐가 들어갈 건지 생성자를 통해서 생성합니다. (생성자 주입)
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
