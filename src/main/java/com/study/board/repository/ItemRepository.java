package com.study.board.repository;

import com.study.board.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId()==null){ //아이디 값이 없다는 것은 새로 생성한 것과 같다. 신규 등록
            em.persist(item);
        }else {
            em.merge(item); //update. 아이디 값이 있으면 업데이트와 유사
        }
    }
    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
