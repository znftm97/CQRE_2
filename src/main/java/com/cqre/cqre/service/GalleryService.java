package com.cqre.cqre.service;

import com.cqre.cqre.dto.CreateGalleryDto;
import com.cqre.cqre.dto.FindGalleryFileDto;
import com.cqre.cqre.entity.GalleryFile;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.exception.customexception.CGalleryFileIsNotImage;
import com.cqre.cqre.repository.gallery.GalleryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final UserService userService;
    private final GalleryRepository galleryRepository;

    @Value("${custom.path.gallery-images}")
    private String savePath;

    Long bundleId = 1L;

    /*갤러리 파일 생성*/
    @Transactional
    public void createGallery(MultipartFile[] files, CreateGalleryDto createGalleryDto) {
        User loginUser = userService.getLoginUser();

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
                String filename = System.currentTimeMillis() + "_" + origFilename; /*파일 이름 중복되지 않도록*/
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

    /*갤러리 파일 중복 제거 조회*/
    public Page<FindGalleryFileDto> findGalleryFiles(Pageable pageable){
        return galleryRepository.findAllDistinctByBundleId(pageable);
    }
}
