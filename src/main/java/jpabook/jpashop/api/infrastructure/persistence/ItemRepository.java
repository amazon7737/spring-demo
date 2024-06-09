package jpabook.jpashop.api.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.api.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }

    }
    public Item findOne(Long id){
        return em.find(Item.class , id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    /**
     * 준영속 엔티티
     * 영속성 컨텍스트가 더는 관리하지 않는 엔티티를 말한다.
     * Book 객체가 있을때, 이미 DB에 한번 저장되어서 식별자가 존재한다. 임의로 만들어낸 엔티티도 기존 식별자를 가지고 있으면 준영속 엔티티로 볼 수 있다.
     */

    /**
     * 변경 감지 기능 사용
     */
    @Transactional
    void update(Item itemParam){ // itemParam : 파라머니터로 넘어온 준 영속 상태의 엔티티
        Item findItem = em.find(Item.class, itemParam.getId()); // 같은 엔티티를 조회한다

        findItem.setPrice(itemParam.getPrice()); // 데이터를 수정한다.
    }

    /**
     * 병합 사용
     * 병합은 준영속 상태의 엔티티를 영속 상태로 변경할 때 사용하는 기능이다.
     */
//    @Transactional
//    void update(Item itemParam){ // itemParam : 파라미터로 넘어온 준 영속 상태의 엔티티
//        Item mergeItem = em.merge(itemParam);
//    }
}

/**
 * 새로운 엔티티 저장과 준영속 엔티티 병합을 편리하게 한번에 처리
 *
 * 상품 리포지토리에선 save() 메서드는 하나로 저장과 수정(병합)을 다 처리한다. 식별자 값이 없으면 새로운 엔티티로 판단해서 persist()로 영속화 하고
 * 만약 식별자 값이 있으면 이미 한번 영속화 되었던 엔티티로 판단해서 merge() 로 수정(병합)한다. 결국 여기서의 저장이라는 의미는
 * 신규 데이터를 저장하는 것뿐만 아니라 변경된 데이터의 저장이라는 의미도 포함한다.
 * 이로인해 클라이언트의 로직이 단순해진다.
 *
 * 여기서 사용하는 수정(병합)은 준영속 상태의 엔티티를 수정할 때 사용한다. 영속 상태의 엔티티는 변경 감지(dirty checking)이 동작해서 트랜잭션을
 * 커밋할때 자동으로 수정되므로 별도의 수정 메서드를 호출할 필요가 없고 그런 메서드도 없다.
 *
 *
 * 참고 : save() 메서드는 식별자를 자동 생성해야 정상 동작한다. -> @GeneratedValue
 * 호출하면 persist() 를 호출하며 자동으로 할당된다. @Id만 선언했다면 식별자가 없다는 예외가 발생한다.
 *
 * 참고2 : 실무에서는 업데이트 기능이 매우 제한적. 병합은 모든 필드를 변경 , 데이터가 없으면 null로 업데이트해버림
 * 병합을 사용하면서 문제를 해결하려면 변경 폼 화면에서 모든 데이터를 항상 유지해야함.
 * 실무에서는 변경가능한 데이터만 노출 , 병합을 사용하는 것이 오히려 번거롭다.
 */

