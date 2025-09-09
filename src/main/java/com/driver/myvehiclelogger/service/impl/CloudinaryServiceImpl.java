package com.driver.myvehiclelogger.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.driver.myvehiclelogger.service.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile image, Long userId) {
        try {
            String url = cloudinary.uploader().upload(image.getBytes(),
                            Map.of(
                                    "folder", "users/" + userId,
                                    "public_id", UUID.randomUUID().toString())).get("url")
                    .toString();
            System.out.println("Image uploaded");
            System.out.println(url);
            return url;
        } catch (IOException ex) {
            System.out.println("Error while uploading file");
        }
        return null;
    }

    public void destroyFile(String url) {
        String publicId = extractPublicId(url);

        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroyFolder(Long userId) {
        try {
            cloudinary.api().deleteFolder("users/"+userId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractPublicId(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }

        try {
            String cleanUrl = url.split("\\?")[0];

            int uploadIndex = cleanUrl.indexOf("/upload/");
            if (uploadIndex == -1) {
                return null;
            }

            String afterUpload = cleanUrl.substring(uploadIndex + 8);

            if (afterUpload.matches("^v\\d+/.+")) {
                afterUpload = afterUpload.substring(afterUpload.indexOf('/') + 1);
            }

            int lastDot = afterUpload.lastIndexOf('.');
            if (lastDot != -1) {
                afterUpload = afterUpload.substring(0, lastDot);
            }

            return afterUpload;
        } catch (Exception ex) {
            return null;
        }
    }

}
