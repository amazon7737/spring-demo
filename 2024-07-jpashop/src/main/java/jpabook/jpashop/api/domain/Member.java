package jpabook.jpashop.api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();


}

/**
 * 외래 키가 있는 곳을 연관관계의 주인으로 정해라
 *
 *  일대다 관계에서 항상 다쪽에 외래 키가 있으므로 그곳을 주인으로 정하면 된다
 *  만약 일 쪽을 주인으로 정하게 되면 후에 관리되지 않은 곳까지 외래키값이 업데이트 되면서 관리가 어렵다.
 *  추가적인 쿼리가 발생하는 성능 문제가 있다.
 *
 */

/**
 * 예제는 설명을 쉽게하기 위해서 Getter, Setter를 모두 열고, 최대한 단순하게 설계했다
 *
 * 가급적 Getter는 열어두고 Setter는 꼭 필요한 경우에만 사용하는 것을 추천한다
 *
 * Getter : 데이터 조회할 일 (아무리 호출해도 호출만 하는것으로는 어떤일이 발생하지 않음)
 * Setter : 호출하면 데이터가 변한다 ( 막 열어두면 가까운 미래에 엔티티가 도대체 왜 변경되는지 찾기 ㅎ미들어진다.)
 * 따라서 엔티티를 변경할때는 Setter 대신에 변경 지점이 명확하도록 메서드를 별도로 제공해야함
 *
 */

/**
 * 테이블은 관례상 테이블명 + id를 많이 사용한다
 *
 *
 * 실무에서는 @ManyToMany를 사용하지 말자
 * 편리한것 같지만 중간 테이블에 컬럼 추가가 불가능 하고, 세밀하게 쿼리를 실행하기 어렵기 때문에 실무에서는 한계가 있다.
 * 중간엔티티를 만들고 @ManyToOne , @OneToMany로 매핑해서 사용하자
 *
 * 모든 연관관계는 지연로딩으로 설정
 *
 * 스프링 부트 신규 설정
 * memberPoint -> member_point
 *
 */