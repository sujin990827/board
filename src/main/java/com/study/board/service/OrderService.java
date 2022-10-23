package com.study.board.service;

import com.study.board.domain.Delivery;
import com.study.board.domain.Member;
import com.study.board.domain.Order;
import com.study.board.domain.OrderItem;
import com.study.board.domain.item.Item;
import com.study.board.repository.ItemRepository;
import com.study.board.repository.MemberRepository;
import com.study.board.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long Order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress()); //회원의 주소에 있는 값으로 대체한다

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrder(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel(); //비즈니스로직 사용
    }

    //검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}
