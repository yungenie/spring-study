package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    /** DIP 적용 전 */
    /* private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); //DIP 위반!!

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }*/

    /** DIP 적용 후 */
    // 추상화(인터페이스)에만 의존함. DIP 원칙 지킴
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    //private final DiscountPolicy rateDiscountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("subDiscountPolicy") DiscountPolicy discountPolicy) {
        System.out.println("생성자 memberRepository = " + memberRepository);
        System.out.println("생성자 discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    //private MemberRepository memberRepository;
    //private DiscountPolicy discountPolicy;

/*    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("set memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("set discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }*/

    //@Autowired // 생략가능. 생성자가 딱 1개만 있을 때 생략해도 자동 주입된다. 요즘에는 생략하는 추세이다.


    /** @RequiredArgsConstructor 적용 후 */
    //@RequiredArgsConstructor 사용하면 생성자 직접 안만들어줘도 된다.
/*    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        System.out.println("생성자 memberRepository = " + memberRepository);
        System.out.println("생성자 discountPolicy = " + discountPolicy);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }*/

    /*public OrderServiceImpl() {
    }*/

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
//        int discountPrice = rateDiscountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
