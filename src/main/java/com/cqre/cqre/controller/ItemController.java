package com.cqre.cqre.controller;

import com.cqre.cqre.dto.coupon.FindCouponDto;
import com.cqre.cqre.dto.item.CreateItemDto;
import com.cqre.cqre.dto.item.FindItemDetailDto;
import com.cqre.cqre.dto.item.FindItemDto;
import com.cqre.cqre.exception.customexception.item.CFileIsNotImageItemException;
import com.cqre.cqre.service.CategoryService;
import com.cqre.cqre.service.CouponService;
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

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final CategoryService categoryService;
    private final CouponService couponService;

    /*상품 페이지*/
    @GetMapping("/items")
    public String itemPage(@PageableDefault(size = 6) Pageable pageable,
                       @RequestParam(value = "notEnoughStock", required = false) String notEnoughStock,
                       Model model){
        Page<FindItemDto> items = itemService.findItems(pageable);
        List<String> categoryNames = categoryService.findCategoryNames();

        model.addAttribute("items", items);
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("notEnoughStock", notEnoughStock);

        return "/shop/shop";
    }

    /*카테고리별 상품조회*/
    @GetMapping("/items/{categoryName}")
    public String readItemOfCategory(@PageableDefault(size = 6, sort = "id") Pageable pageable, Model model,
                       @PathVariable(value = "categoryName") String categoryName){
        Page<FindItemDto> items = itemService.findItemsByCategory(pageable, categoryName);
        List<String> categoryNames = categoryService.findCategoryNames();

        model.addAttribute("items", items);
        model.addAttribute("categoryNames", categoryNames);

        return "/shop/shop";
    }

    /*상품 생성 페이지*/
    @GetMapping("/items/page")
    public String createItemPage(){

        return "/item/createItem";
    }

    /*상품 생성*/
    @PostMapping("/items")
    public String createItem(@ModelAttribute CreateItemDto dto,
                              @RequestParam("file") List<MultipartFile> files) throws IOException {
        for (MultipartFile uploadFile : files) {
            if (uploadFile.getContentType().startsWith("image") == false) {
                throw new CFileIsNotImageItemException();
            }
        }

        itemService.createItem(dto, files, "shopImages");
        return "redirect:/items";
    }

    /*상품 상세 조회*/
    @GetMapping("/items/{itemId}/{bundleId}")
    public String readItemDetail(@PathVariable("bundleId") String bundleId,
                                 @PathVariable("itemId") Long itemId,
                                 Model model){

        FindItemDetailDto itemDetail = itemService.findItemDetail(itemId);
        List<String> itemImageDetail = itemImageService.findItemImageDetail(bundleId);
        List<FindCouponDto> coupons = couponService.myCouponsNotPaging();

        model.addAttribute("itemDetail", itemDetail);
        model.addAttribute("itemImageDetail", itemImageDetail);
        model.addAttribute("coupons", coupons);

        return "/item/readItemDetail";
    }
}
