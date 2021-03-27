package com.cqre.cqre.controller;

import com.cqre.cqre.dto.item.CreateItemDto;
import com.cqre.cqre.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/shop")
    public String shop(){
        return "/shop/shop";
    }

    @GetMapping("/createItem")
    public String createItem(){

        return "/item/createItem";
    }

    @PostMapping("/createItem")
    public String pCreateItem(@ModelAttribute CreateItemDto dto,
                              @RequestParam("file") MultipartFile[] files) {

        itemService.createItem(dto, files);
        return "redirect:/shop";
    }
}
