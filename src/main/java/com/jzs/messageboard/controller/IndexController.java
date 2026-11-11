package com.jzs.messageboard.controller;

import com.jzs.messageboard.service.MessageService;
import com.jzs.messageboard.vo.MessageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    private final MessageService messageService;

    public IndexController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping({"/", "/index"})
    public String index(@RequestParam(required = false) String keyword, Model model) {
        // 传入关键词执行查询，为空时等价于原有的全量查询
        List<MessageVO> allList = messageService.listAllWithUsername(keyword);

        // 从查询结果中筛选置顶留言，搜索前后逻辑统一
        List<MessageVO> topList = allList.stream()
                .filter(msg -> msg.getIsTop() != null && msg.getIsTop() == 1)
                .collect(Collectors.toList());

        model.addAttribute("messageList", allList);
        model.addAttribute("topList", topList);
        // 关键词回传到前端，用于输入框回填
        model.addAttribute("keyword", keyword);

        return "index";
    }
}
