package com.cqre.cqre.service;

import com.cqre.cqre.dto.CreateGalleryDto;
import com.cqre.cqre.entity.GalleryFile;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.exception.customexception.CGalleryFileIsNotImage;
import com.cqre.cqre.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final UserService userService;
    private final GalleryRepository galleryRepository;

    Long bundleId = 1L;

    @Transactional
    public void createGallery(MultipartFile[] files, CreateGalleryDto createGalleryDto) {
        User loginUser = userService.getLoginUser();

        String savePath = System.getProperty("user.dir") + "\\files" + "\\galleryFiles";

        if (!new java.io.File(savePath).exists()) {
            try{
                new java.io.File(savePath).mkdir();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        /*이미지 파일인지 검사*/
        for (MultipartFile uploadFile : files) {
            if (uploadFile.getContentType().startsWith("image") == false) {
                throw new CGalleryFileIsNotImage();
            }
        }

        for(int i=0; i<files.length; i++){
            try {
                String origFilename = files[i].getOriginalFilename(); /*원본 파일 명*/
                String filename = System.currentTimeMillis() + " - " + origFilename; /*파일 이름 중복되지 않도록*/
                String filePath = savePath + "\\" + filename;

                files[i].transferTo(new java.io.File(filePath));

                GalleryFile galleryFile = GalleryFile.builder()
                        .title(createGalleryDto.getTitle())
                        .filename(filename)
                        .filePath(filePath)
                        .originFilename(origFilename)
                        .user(loginUser)
                        .bundleId(bundleId)
                        .bundleOrder(System.currentTimeMillis())
                        .build();

                galleryRepository.save(galleryFile);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        bundleId++;
    }
}
