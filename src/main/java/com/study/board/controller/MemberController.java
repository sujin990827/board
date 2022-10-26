package com.study.board.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.board.domain.Address;
import com.study.board.domain.Member;
import com.study.board.domain.item.Book;
import com.study.board.domain.item.Item;
import com.study.board.service.ItemService;
import com.study.board.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final ItemService itemService;

    //회원 등록
    @GetMapping("/members/new")  //홈 화면 열기
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm()); //컨트롤러에서 뷰로 넘어갈때 데이터를 실어서 넘긴다
        return "members/createMemberForm";
    }

    //회원 등록
    @PostMapping("/members/new")  //데이터 실제 등록
    public String create(@Valid MemberForm form, BindingResult result){
        //회원 이름을 필수로 쓰고싶을때 @valid 사용
        //만약 에러가 있다면 createMemberForm을 실행시킨다
        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    //상품등록
    @GetMapping("/members")
    public String create(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);  //모델에 담아서 화면에 넘긴다
        return "members/memberList";
    }


    //상품목록
    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    //상품 수정
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());



        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable String itemId, @ModelAttribute("form") BookForm form){
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/items";
    }

}
