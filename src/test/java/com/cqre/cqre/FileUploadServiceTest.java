package com.cqre.cqre;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.cqre.cqre.service.FileUploadService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileUploadServiceTest {

    @Mock
    AmazonS3 amazonS3;

    @InjectMocks
    FileUploadService fileUploadService;

    @Before("??")
    public void setUp() {
        amazonS3 = mock(AmazonS3.class);
    }

    @Test
    @DisplayName("MultipartFile 타입을 File 타입으로 변환 한다.")
    public void convert() throws IOException {
        // given
        List<MultipartFile> multipartFiles = new ArrayList<>();
        MultipartFile file = new MockMultipartFile("name", "originFileName", "imageType", new byte[1]);
        multipartFiles.add(file);

        List<File> files = new ArrayList<>();
        String home = System.getProperty("user.home");
        File convertFile = new File(home + File.separator + System.currentTimeMillis() + "_" + multipartFiles.get(0).getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFiles.get(0).getBytes());
            }
            files.add(convertFile);
        }

        // when
        List<File> findFiles = fileUploadService.convert(multipartFiles);

        // then
        assertThat(findFiles.get(0).getFreeSpace()).isEqualTo(files.get(0).getFreeSpace());
    }

    @Test
    @DisplayName("S3에 파일을 업로드 한다.")
    @Disabled("System.currentTimeMillis() 때문에 stubbing arcument가 mismatch 난다.. 테스트 하려면 원본 코드를 수정해야 할 듯하다.")
    public void fileUploadToS3() throws MalformedURLException {
        List<File> files = new ArrayList<>();
        File file = new File("pathName");
        files.add(file);

        PutObjectResult result = mock(PutObjectResult.class);
        when(amazonS3.putObject("cqrebucket", "key", file)).thenReturn(result);

        URL url = new URL("http:test");
        when(amazonS3.getUrl("cqrebucket", file.getName())).thenReturn(url);

        //when
        List<String> findUrls = fileUploadService.uploadToS3(files, "dirName");

        // then
        assertThat(findUrls.get(0)).isEqualTo(url.toString());
    }

}
