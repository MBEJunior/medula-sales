package com.medulasales.products.utils.storage;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

public class MediaUtils {

    public static final String MIME_TYPE_AUDIO = "audio/*";
    public static final String MIME_TYPE_TEXT = "text/*";
    public static final String MIME_TYPE_IMAGE = "image/*";
    public static final String MIME_TYPE_VIDEO = "video/*";
    public static final String MIME_TYPE_APP = "application/*";

    ServletContext servletContext;

    public MediaUtils(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getMimeType(String fileName) {
        return servletContext.getMimeType(fileName);
    }

    public boolean isImage(MultipartFile multipartFile) {
        return getMimeType(multipartFile.getOriginalFilename()).contains(MIME_TYPE_IMAGE);
    }

    public boolean isVideo(MultipartFile multipartFile) {
        return getMimeType(multipartFile.getOriginalFilename()).contains(MIME_TYPE_VIDEO);
    }

    public boolean isAudio(MultipartFile multipartFile) {
        return getMimeType(multipartFile.getOriginalFilename()).contains(MIME_TYPE_AUDIO);
    }

    public boolean isText(MultipartFile multipartFile) {
        return getMimeType(multipartFile.getOriginalFilename()).contains(MIME_TYPE_TEXT);
    }

    public String getExtension(String fileName) {
        int dot = fileName.lastIndexOf(".");
        if (dot >= 0) {
            return fileName.substring(dot);
        }
        return "";
    }
}
