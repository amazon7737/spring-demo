package jpabook.jpashop.api.infrastructure.persistence;

import jpabook.jpashop.api.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDataRepository extends JpaRepository<Order, Long> {
}
