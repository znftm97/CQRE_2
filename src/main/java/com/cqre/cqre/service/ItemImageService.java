package com.cqre.cqre.service;

import com.cqre.cqre.entity.shop.ItemImage;
import com.cqre.cqre.entity.shop.item.CommonItem;
import com.cqre.cqre.exception.customexception.CFileIsNotImage;
import com.cqre.cqre.repository.itemImage.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;

    @Value("${custom.path.shop-images}")
    private String savePath;

    private Long bundleId = 1L;

    @Transactional
    public void createItemImage(MultipartFile[] files, CommonItem commonItem) {

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

                ItemImage itemImage = ItemImage.builder()
                        .originFilename(origFilename)
                        .filename(filename)
                        .filePath(filePath)
                        .bundleOrder(System.currentTimeMillis())
                        .bundleId(bundleId)
                        .item(commonItem)
                        .build();

                itemImageRepository.save(itemImage);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        bundleId++;
    }

    public List<String> findItemImageDetail(Long bundleId) {
        List<ItemImage> findItemImages = itemImageRepository.findItemImageByBundleId(bundleId);
        return findItemImages.stream()
                .map(i -> i.getFilename())
                .collect(Collectors.toList());
    }
}
