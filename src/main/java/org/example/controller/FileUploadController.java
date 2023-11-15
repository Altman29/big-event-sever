package org.example.controller;

import org.example.pojo.Result;
import org.example.utils.TXCOSUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {


    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        //把文件的内容存储到本地磁盘上
        //保证文件名字唯一，从而防止覆盖
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("E:\\code\\files\\" + fileName));
        String url = TXCOSUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }
}
