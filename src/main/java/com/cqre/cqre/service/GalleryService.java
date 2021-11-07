package com.cqre.cqre.service;

import com.cqre.cqre.domain.GalleryFile;
import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.dto.gallery.FindGalleryFileDetailDto;
import com.cqre.cqre.dto.gallery.FindGalleryFileDto;
import com.cqre.cqre.repository.gallery.GalleryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

    private final UserService userService;
    private final GalleryRepository galleryRepository;
    private final FileUploadService fileUploadService;

    public void upload(List<MultipartFile> multipartFiles, String dirName, String title) throws IOException {
        User loginUser = userService.getLoginUser();

        List<File> convertFiles = fileUploadService.convert(multipartFiles);
        List<String> uploadImageUrls = fileUploadService.uploadToS3(convertFiles, dirName);
        String bundleId = UUID.randomUUID().toString();

        for (int i = 0; i < multipartFiles.size(); i++) {
            String origFilename = multipartFiles.get(i).getOriginalFilename(); /*원본 파일 명*/
            String filename = System.currentTimeMillis() + "_" + origFilename; /*파일 이름 중복되지 않도록*/
            String filePath = uploadImageUrls.get(i);

            GalleryFile galleryFile = GalleryFile.builder()
                    .title(title)
                    .filename(filename)
                    .filePath(filePath)
                    .originFilename(origFilename)
                    .user(loginUser)
                    .bundleId(bundleId)
                    .bundleOrder(System.currentTimeMillis())
                    .build();

            galleryRepository.save(galleryFile);
        }
    }

    /*갤러리 파일 중복 제거 조회*/
    public Page<FindGalleryFileDto> findGalleryFilesDistinct(Pageable pageable){
        return galleryRepository.findAllDistinctByBundleId(pageable);
    }

    /*갤러리 상세 조회 페이지*/
    public Page<FindGalleryFileDetailDto> findGalleryFiles(Pageable pageable, String bundleId){
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
    public void galleryFileDelete(String bundleId){
        List<GalleryFile> findGalleryFiles = galleryRepository.findGalleryFileByBundleId(bundleId);

        List<Long> GalleryFileIds = findGalleryFiles.stream()
                .map(g -> g.getId())
                .collect(Collectors.toList());

        galleryRepository.deleteAllByIdInQuery(GalleryFileIds);
    }

}
