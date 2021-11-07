package com.cqre.cqre.service;

import com.cqre.cqre.domain.shop.ItemImage;
import com.cqre.cqre.domain.shop.item.CommonItem;
import com.cqre.cqre.repository.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final FileUploadService fileUploadService;

    public void upload(List<MultipartFile> multipartFiles, String dirName, CommonItem commonItem) throws IOException {
        List<File> convertFiles = fileUploadService.convert(multipartFiles);
        List<String> uploadImageUrls = fileUploadService.uploadToS3(convertFiles, dirName);
        String bundleId = UUID.randomUUID().toString();

        for (int i = 0; i < multipartFiles.size(); i++) {
            String origFilename = multipartFiles.get(i).getOriginalFilename(); /*원본 파일 명*/
            String filename = System.currentTimeMillis() + "_" + origFilename; /*파일 이름 중복되지 않도록*/
            String filePath = uploadImageUrls.get(i);

            ItemImage itemImage = ItemImage.builder()
                    .originFilename(origFilename)
                    .filename(filename)
                    .filePath(filePath)
                    .bundleId(bundleId)
                    .bundleOrder(System.currentTimeMillis())
                    .item(commonItem)
                    .build();

            itemImageRepository.save(itemImage);
        }
    }

    /*상품 상세 이미지 조회*/
    public List<String> findItemImageDetail(String bundleId) {
        List<ItemImage> findItemImages = itemImageRepository.findItemImageByBundleId(bundleId);
        return findItemImages.stream()
                .map(i -> i.getFilePath())
                .collect(Collectors.toList());
    }

}
