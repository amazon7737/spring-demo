package jpabook.jpashop.api.application;

import jpabook.jpashop.api.domain.Member;
import jpabook.jpashop.api.infrastructure.persistence.MemberDataRepository;
import jpabook.jpashop.api.infrastructure.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Transactional : 트랜잭션, 영속성 컨텍스트
 * readOnley = true : 데이터 변경이 없는 읽기 전용 메서드에 사용 , 영속성 컨텍스트를 플러시 하지 않아서 약간의 성능 향상 (읽기 전용에는 다 적용)
 * 데이터베이스 드라이버가 지원하면 DB에서 성능 향상
 *
 * @AutoWired : 생성자 Injection 많이 사용 , 생성자가 하나면 생략가능
 *
 *
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    /**
     * 필드 주입 방식
     */
//    @Autowired
//    MemberRepository memberRepository;

    private final MemberRepository memberRepository;

//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
    /**
     * 생성자 주입 방식을 권장 , 안전한 객체 생성이가능하다
     */

    /**
     * 실무에서는 검증 로직이 있어도 멀티 쓰레드 상황을 고려해서 회원 테이블의 회원명 컬럼에
     * 유니크 제약 조건을 추가하는 것이 안전하다.
     * 참고 : 스프링 필드 주입 대신에 생성자 주입을 사용하자.
     * 생성자 주입
     * 생성사 주입 방식을 권장한다. 변경 불가능한 안전한 객체 생성이 가능하고, 생성자가 하나면,
     * @Autowired 를 생략할 수 있다.
     * final 키워드를 추가하면 컴파일 시점에서 memberRepository를 설정하지 않는 오류를 체크
     * 할 수 있다. (보통 기본 생성자를 추가할때 발견)
     */


    @Transactional //변경
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if(!findMembers.isEmpty()){
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    /**
     * 회원 수정
     */

    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }


//    public Member find(Long id){
//        return memberRepository.find(id);
//    }


}
