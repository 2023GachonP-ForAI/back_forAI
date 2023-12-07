package com.forAi.watermelon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.dir.record}")
    private String recordDir;

    @Override
    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("업로드 된 파일이 없습니다.");
        }
//        String randomName = UUID.randomUUID().toString();
        String originName = file.getOriginalFilename();
        if (originName == null) throw new AssertionError();
//        String extension = originName.substring(originName.lastIndexOf(".")).toLowerCase();
        String fileDir = recordDir;
//        String saveFilePath = fileDir + randomName + extension;
        // 파일 이름 추출, 변경 후 확장자와 함께 저장
        try {
            File saveFile = new File(fileDir + originName);
            System.out.println("파일 저장 완료");
            file.transferTo(saveFile);
            return originName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 업로드 중 오류가 발생했습다.", e);
        }
    }


    @Override
    public void deleteFile(String fileName) {
        String filePath = recordDir + fileName;
        File fileToDelete = new File(filePath);

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("파일 삭제 성공: " + filePath);
            } else {
                System.out.println("파일 삭제 실패: " + filePath);
            }
        } else {
            System.out.println("파일이 존재하지 않습니다: " + filePath);
        }
    }
}
