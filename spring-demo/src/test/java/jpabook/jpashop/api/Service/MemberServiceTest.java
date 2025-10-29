package jpabook.jpashop.api.Service;

import jpabook.jpashop.api.application.MemberService;
import jpabook.jpashop.api.domain.Member;
import jpabook.jpashop.api.infrastructure.persistence.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired  MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        // Given
        Member member = new Member();
        member.setName("kim");

        // When
        Long saveId = memberService.join(member);

        // Then
        assertEquals(member, memberRepository.findOne(saveId));

    }

    @Test(expected = IllegalArgumentException.class)
    public void 중복_회원_예외() throws Exception{

        // Given
        Member member1 = new Member();
        member1.setName("kim");;

        Member member2 = new Member();
        member2.setName("kim");

        // When
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생하도록

        // Then
        fail("예외가 발생해야 한다.");
    }


}

/**
 * @RunWith(SpringRunner.class) : 스프링과 테스트 통합
 * @SpringBootTest : 스프링 부트 띄우고 테스트 (이게 없으면 @Autowired 다 실패)
 * @Transactional : 반복 가능한 테스트 지원 , 각 테스트 시작마다 트랜잭션을 시작하고 테스트가 끝나면 트랜잭션을 강제로 롤백 ( 테스트 케이스에서 사용될 때만 롤백)
 *
 */

/**
 * 테스트 케이스를 위한 설정
 * 테스트는 케이스 격리된 환경에서 실행하고 초기화 하는게 좋다 , 메모리 DB를 사용하는 것이 가장 이상적이다.
 * 테스트 케이스를 위한 스프링 환경과 , 일반적으로 애플리케이션을 실행하는 환경을 따로 두자
 *
 * test/resources/application.yml 에 설정파일을 추가하면 된다
 * 테스트에서 스프링을 실행하면 이 실행파일을 읽는다.
 * 만약 없으면 본 파일을 읽는다.
 *
 * datasource 설정이 없으면 기본적으로 메모리 DB를 사용하고 driver-class도 현재 등록된 라이브러리를 보고 찾아준다. ddl-auto도 create-drop모드로 동작한다.
 * 데이터소스나 JPA 관련된것도 추가설정을 하지 않아도 된다.
 *
 */