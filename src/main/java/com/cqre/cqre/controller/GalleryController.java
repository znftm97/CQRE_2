package com.cqre.cqre.controller;

import com.cqre.cqre.dto.CreateGalleryDto;
import com.cqre.cqre.dto.FindGalleryFileDto;
import com.cqre.cqre.repository.gallery.GalleryRepository;
import com.cqre.cqre.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class GalleryController {

    private final GalleryService galleryService;

    /*갤러리 페이지*/
    @GetMapping("/gallery")
    public String gallery(Model model, @PageableDefault(size = 6, sort = "id") Pageable pageable){
        Page<FindGalleryFileDto> galleryFiles = galleryService.findGalleryFiles(pageable);
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
    @GetMapping("/galleryView")
    public String galleryView(){
        return "/gallery/galleryView";
    }
}
