package com.medulasales.products.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;

@ConfigurationProperties("medulasales-products.storage")
@Getter
@Setter
public class StorageProperties {

    private String location = "media-storage";
    private int maxFileSize = 10000;
    private MediaType defaultMediaType = MediaType.ALL;
    private MediaType defaultPictureType = MediaType.IMAGE_JPEG;
    private MediaType defaultVideoType = MediaType.APPLICATION_OCTET_STREAM;
    private MediaType defaultAudioType = MediaType.APPLICATION_OCTET_STREAM;
    private MediaType defaultDocumentType = MediaType.ALL;
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
