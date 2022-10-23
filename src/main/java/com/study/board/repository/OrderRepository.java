package com.study.board.repository;

import com.study.board.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    //단건 조회
    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    // 검색기능 (동적 쿼리)
//    public List<Order> findAll(OrderSearch orderSearch){
//
//    }
}
