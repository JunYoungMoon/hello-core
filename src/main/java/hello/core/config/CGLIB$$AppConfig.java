package hello.core.config;

import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import org.springframework.beans.factory.config.SingletonBeanRegistry;

public class CGLIB$$AppConfig extends AppConfig {
//    private final SingletonBeanRegistry singletonBeanRegistry;
//
//    public CGLIB$$AppConfig(SingletonBeanRegistry singletonBeanRegistry) {
//        this.singletonBeanRegistry = singletonBeanRegistry;
//    }
//
//    @Override
//    public MemberService memberService(){
//        //getSingleton으로 빈이름을 키로 존재하는지 여부를 체크한다.
//        //Supplier방식으로 존재 하지 않는다면 AppConfig로 새로운 빈을 등록한다.
//        return (MemberService) this.singletonBeanRegistry.getSingleton("memberService", () -> super.memberService());
//    }
//
//    @Override
//    public MemberRepository memberRepository() {
//        return (MemberRepository) this.singletonBeanRegistry.getSingleton("memberRepository", () -> super.memberRepository());
//    }
}

