package com.jzs.messageboard.controller;

import com.jzs.messageboard.service.MessageService;
import com.jzs.messageboard.vo.MessageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    private final MessageService messageService;

    public IndexController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        List<MessageVO> allList = messageService.listAllWithUsername();
        // 后端筛选置顶留言，传给模板
        List<MessageVO> topList = allList.stream()
                .filter(msg -> msg.getIsTop() != null && msg.getIsTop() == 1)
                .collect(Collectors.toList());

        model.addAttribute("messageList", allList);
        model.addAttribute("topList", topList);
        return "index";
    }
}
