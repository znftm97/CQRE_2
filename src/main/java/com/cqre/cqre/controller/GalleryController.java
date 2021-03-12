package com.cqre.cqre.controller;

import com.cqre.cqre.dto.gallery.CreateGalleryDto;
import com.cqre.cqre.dto.gallery.FindGalleryFileDetailDto;
import com.cqre.cqre.dto.gallery.FindGalleryFileDto;
import com.cqre.cqre.service.GalleryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GalleryController {

    private final GalleryService galleryService;

    /*갤러리 페이지*/
    @GetMapping("/gallery")
    public String gallery(Model model, @PageableDefault(size = 6, sort = "id") Pageable pageable){
        Page<FindGalleryFileDto> galleryFiles = galleryService.findGalleryFilesDistinct(pageable);
        model.addAttribute("galleryFiles", galleryFiles);

        return "/gallery/gallery";
    }

    /*갤러리 생성 페이지*/
    @GetMapping("/gallery/create")
    public String createGallery(Model model){
        model.addAttribute("createGalleryDto", new CreateGalleryDto());
        return "/gallery/createGallery";
    }

    /*갤러리 생성*/
    @PostMapping("/gallery/create")
    public String PCreateGallery(@ModelAttribute("createGalleryDto") CreateGalleryDto createGalleryDto,
                                 @RequestParam("file") MultipartFile[] files) {

        galleryService.createGallery(files, createGalleryDto);

        return "redirect:/gallery";
    }

    /*갤러리 상세 조회 페이지*/
    @GetMapping("/galleryView/{bundleId}")
    public String galleryView(@PathVariable(value = "bundleId") Long bundleId,
                              @PageableDefault(size = 6, sort = "bundleOrder") Pageable pageable,
                              Model model){
        Page<FindGalleryFileDetailDto> galleryFiles = galleryService.findGalleryFiles(pageable, bundleId);

        String title = galleryFiles.getContent().get(0).getTitle();
        model.addAttribute("galleryFiles", galleryFiles);
        model.addAttribute("title", title);
        model.addAttribute("bundleId", bundleId);

        return "/gallery/galleryView";
    }

    /*삭제*/
    @PostMapping("/gallery/delete")
    public String galleryDelete(Long bundleId){
        galleryService.galleryFileDelete(bundleId);
        return "redirect:/gallery";
    }
}
