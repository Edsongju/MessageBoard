package com.jzs.messageboard.controller;

import com.jzs.messageboard.service.MessageService;
import com.jzs.messageboard.service.ReplyService;
import com.jzs.messageboard.vo.MessageVO;
import com.jzs.messageboard.vo.ReplyVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class IndexController {

    private final MessageService messageService;
    private final ReplyService replyService; // 新增注入

    public IndexController(MessageService messageService, ReplyService replyService) {
        this.messageService = messageService;
        this.replyService = replyService;
    }

    @GetMapping({"/", "/index"})
    public String index(@RequestParam(required = false) String keyword,
                        @RequestParam(required = false) String topic,
                        Model model) {
        List<MessageVO> allList = messageService.listAllWithUsername(keyword);

        // 按分类筛选
        if (topic != null && !topic.isEmpty()) {
            allList = allList.stream()
                    .filter(m -> topic.equals(m.getTopic()))
                    .collect(Collectors.toList());
        }

        List<MessageVO> topList = allList.stream()
                .filter(msg -> msg.getIsTop() != null && msg.getIsTop() == 1)
                .collect(Collectors.toList());

        model.addAttribute("messageList", allList);
        model.addAttribute("topList", topList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentTopic", topic);
        return "index";
    }

}
