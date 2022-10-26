package com.study.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.board.domain.Member;
import com.study.board.domain.Order;
import com.study.board.domain.item.Book;
import com.study.board.domain.item.Item;
import com.study.board.repository.OrderSearch;
import com.study.board.service.ItemService;
import com.study.board.service.MemberService;
import com.study.board.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OrderController {

	private final OrderService orderService;
	private final MemberService memberService;
	private final ItemService itemService;

	//상품 주문
	@GetMapping("/order")
	private String createForm(Model model){
		List<Member> members = memberService.findMembers();
		List<Item> items = itemService.findItems();

		model.addAttribute("members", members);
		model.addAttribute("items", items);

		return "order/orderForm";
	}

	@PostMapping("/order")
	private String order(@RequestParam("memberId") Long memberId,
							@RequestParam("itemId") Long itemId,
							@RequestParam("count") int count){
		 orderService.order(memberId, itemId, count);
		 return "redirect:/orders";

	}

	//주문 목록 검색, 취소
	@GetMapping("/orders")
	public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
		List<Order> orders = orderService.findOrders(orderSearch);
		model.addAttribute("orders", orders);
		return "order/orderList";
	}

	@PostMapping("/orders/{orderId}/cancel")
	public String cancelOrder(@PathVariable("orderId") Long orderId){
		orderService.cancelOrder(orderId);
		return "redirect:/orders";
	}
}
