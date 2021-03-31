package com.cqre.cqre.controller;

import com.cqre.cqre.dto.item.CreateItemDto;
import com.cqre.cqre.dto.item.FindItemDetailDto;
import com.cqre.cqre.dto.item.FindItemDto;
import com.cqre.cqre.service.CategoryService;
import com.cqre.cqre.service.ItemImageService;
import com.cqre.cqre.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final CategoryService categoryService;

    @GetMapping("/shop")
    public String shop(@PageableDefault(size = 6, sort = "id") Pageable pageable, Model model){
        Page<FindItemDto> items = itemService.findItems(pageable);
        List<String> categoryNames = categoryService.findCategoryNames();

        model.addAttribute("items", items);
        model.addAttribute("categoryNames", categoryNames);

        return "/shop/shop";
    }

    @GetMapping("/shop/{categoryName}")
    public String shop(@PageableDefault(size = 6, sort = "id") Pageable pageable, Model model,
                       @PathVariable(value = "categoryName") String categoryName){
        Page<FindItemDto> items = itemService.findItemsByCategory(pageable, categoryName);
        List<String> categoryNames = categoryService.findCategoryNames();

        model.addAttribute("items", items);
        model.addAttribute("categoryNames", categoryNames);

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

    @GetMapping("/itemDetail/{itemId}/{bundleId}")
    public String findItemDetail(@PathVariable("bundleId") Long bundleId,
                                 @PathVariable("itemId") Long itemId,
                                 Model model){

        FindItemDetailDto itemDetail = itemService.findItemDetail(itemId);
        List<String> itemImageDetail = itemImageService.findItemImageDetail(bundleId);
        model.addAttribute("itemDetail", itemDetail);
        model.addAttribute("itemImageDetail", itemImageDetail);

        return "/item/readItemDetail";
    }
}