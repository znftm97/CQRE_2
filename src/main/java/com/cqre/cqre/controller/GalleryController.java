package com.cqre.cqre.controller;

import com.cqre.cqre.dto.gallery.CreateGalleryDto;
import com.cqre.cqre.dto.gallery.FindGalleryFileDetailDto;
import com.cqre.cqre.dto.gallery.FindGalleryFileDto;
import com.cqre.cqre.exception.customexception.gallery.CFileIsNotImageGalleryException;
import com.cqre.cqre.service.GalleryService;
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
public class GalleryController {

    private final GalleryService galleryService;

    /*갤러리 페이지*/
    @GetMapping("/gallerys")
    public String galleryPage(Model model, @PageableDefault(size = 6) Pageable pageable){
        Page<FindGalleryFileDto> galleryFiles = galleryService.findGalleryFilesDistinct(pageable);
        model.addAttribute("galleryFiles", galleryFiles);

        return "/gallery/gallery";
    }

    /*갤러리 생성 페이지*/
    @GetMapping("/gallerys/page")
    public String createGalleryPage(Model model){
        model.addAttribute("createGalleryDto", new CreateGalleryDto());
        return "/gallery/createGallery";
    }

    /*갤러리 생성*/
    @PostMapping("/gallerys")
    public String createGallery(@RequestParam("file") List<MultipartFile> files,
                                 @RequestParam("title") String title) throws IOException {

        for (MultipartFile uploadFile : files) {
            if (uploadFile.getContentType().startsWith("image") == false) {
                throw new CFileIsNotImageGalleryException();
            }
        }

        galleryService.upload(files, "galleryImages", title);

        return "redirect:/gallerys";
    }

    /*갤러리 상세 조회 페이지*/
    @GetMapping("/gallerys/{bundleId}")
    public String galleryDetailPage(@PathVariable(value = "bundleId") String bundleId,
                              @PageableDefault(size = 6, sort = "bundleOrder") Pageable pageable,
                              Model model){
        Page<FindGalleryFileDetailDto> galleryFiles = galleryService.findGalleryFiles(pageable, bundleId);

        String title = galleryFiles.getContent().get(0).getTitle();
        String authorCheck = galleryFiles.getContent().get(0).getAuthorCheck();

        /*나중에 title, bundleId, authorCheck 를 포함하는 DTO하나 만들자*/
        model.addAttribute("galleryFiles", galleryFiles);
        model.addAttribute("title", title);
        model.addAttribute("bundleId", bundleId);
        model.addAttribute("authorCheck", authorCheck);

        return "/gallery/galleryView";
    }

    /*삭제*/
    @DeleteMapping("/gallerys")
    public String deleteGallery(String bundleId){
        galleryService.galleryFileDelete(bundleId);
        return "redirect:/gallerys";
    }
}
