package com.study.board.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.board.domain.Address;
import com.study.board.domain.Member;
import com.study.board.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")  //홈 화면 열기
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm()); //컨트롤러에서 뷰로 넘어갈때 데이터를 실어서 넘긴다
        return "members/createMemberForm";
    }

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
}
