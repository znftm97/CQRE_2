package com.cqre.cqre.service;

import com.cqre.cqre.dto.gallery.CreateGalleryDto;
import com.cqre.cqre.dto.gallery.FindGalleryFileDetailDto;
import com.cqre.cqre.dto.gallery.FindGalleryFileDto;
import com.cqre.cqre.entity.GalleryFile;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.exception.customexception.CFileIsNotImage;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final UserService userService;
    private final GalleryRepository galleryRepository;

    @Value("${custom.path.gallery-images}")
    private String savePath;

    AtomicLong bundleId = new AtomicLong(1);

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
                throw new CFileIsNotImage();
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
                        .bundleId(bundleId.get())
                        .bundleOrder(System.currentTimeMillis())
                        .build();

                galleryRepository.save(galleryFile);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        bundleId.incrementAndGet();
    }

    /*갤러리 파일 중복 제거 조회*/
    public Page<FindGalleryFileDto> findGalleryFilesDistinct(Pageable pageable){
        return galleryRepository.findAllDistinctByBundleId(pageable);
    }

    /*갤러리 상세 조회 페이지*/
    public Page<FindGalleryFileDetailDto> findGalleryFiles(Pageable pageable, Long bundleId){
        Page<GalleryFile> findGalleryFiles = galleryRepository.findGalleryFileByBundleIdPaging(bundleId, pageable);

        Long loginUserId = userService.getLoginUser().getId();
        Long galleryFileCreatorUserId = findGalleryFiles.getContent().get(0).getUser().getId();

        if (loginUserId.equals(galleryFileCreatorUserId)) {
            return findGalleryFiles.map(g -> new FindGalleryFileDetailDto(g, "true"));
        }else {
            return findGalleryFiles.map(g -> new FindGalleryFileDetailDto(g, "false"));
        }
    }

    /*삭제*/
    @Transactional
    public void galleryFileDelete(Long bundleId){
        List<GalleryFile> findGalleryFiles = galleryRepository.findGalleryFileByBundleId(bundleId);

        List<Long> GalleryFileIds = findGalleryFiles.stream()
                .map(g -> g.getId())
                .collect(Collectors.toList());

        galleryRepository.deleteAllByIdInQuery(GalleryFileIds);
    }
}
