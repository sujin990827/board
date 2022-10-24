package com.study.board.repository;

import com.study.board.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

    private String memberName;

    private OrderStatus orderStatus; //주문 상태
}
