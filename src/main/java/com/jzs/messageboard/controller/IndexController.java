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


@Controller
public class IndexController {

    private final MessageService messageService;
    private final ReplyService replyService; // 新增注入

    public IndexController(MessageService messageService, ReplyService replyService) {
        this.messageService = messageService;
        this.replyService = replyService;
    }

    @GetMapping({"/", "/index"})
    public String index(@RequestParam(required = false) String keyword, Model model) {
        List<MessageVO> allList = messageService.listAllWithUsername(keyword);

        // 新增：按留言ID分组封装回复列表
        java.util.Map<Long, List<ReplyVO>> replyMap = new java.util.HashMap<>();
        for (MessageVO msg : allList) {
            replyMap.put(msg.getId(), replyService.listByMessageId(msg.getId()));
        }
        model.addAttribute("replyMap", replyMap);

        // 置顶留言过滤
        List<MessageVO> topList = allList.stream()
                .filter(msg -> msg.getIsTop() != null && msg.getIsTop() == 1)
                .collect(java.util.stream.Collectors.toList());

        model.addAttribute("messageList", allList);
        model.addAttribute("topList", topList);
        model.addAttribute("keyword", keyword);

        return "index";
    }
}
