package jpabook.jpashop.api.infrastructure.persistence;

import jpabook.jpashop.api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDataRepository extends JpaRepository<Member, Long> {
}
