package com.driver.myvehiclelogger.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    String uploadFile(MultipartFile image, Long userId);

    void destroyFile(String url);

    void destroyFolder(Long userId);

}
