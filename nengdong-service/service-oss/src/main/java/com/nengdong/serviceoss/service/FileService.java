package com.nengdong.serviceoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFileOSS(MultipartFile file);
}
