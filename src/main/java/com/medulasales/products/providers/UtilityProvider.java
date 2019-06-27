package com.medulasales.products.providers;

import com.medulasales.products.properties.StorageProperties;
import com.medulasales.products.utils.JmUUIDGenerator;
import com.medulasales.products.utils.storage.FileStorageManager;
import com.medulasales.products.utils.storage.MediaUtils;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

@Component
@Getter
public class UtilityProvider {

    private ServletContext servletContext;
    @Getter(AccessLevel.NONE)
    private StorageProperties storageProperties;
    private FileStorageManager fileStorageManager;
    private JmUUIDGenerator jmUUIDGenerator;
    private MediaUtils mediaUtils;

    @Autowired
    public UtilityProvider(ServletContext servletContext, StorageProperties storageProperties) {
        this.servletContext = servletContext;
        this.storageProperties = storageProperties;
        this.fileStorageManager = new FileStorageManager(storageProperties.getLocation());
        this.jmUUIDGenerator = new JmUUIDGenerator();
        this.mediaUtils = new MediaUtils(servletContext);
        fileStorageManager.init();
    }

    public FileStorageManager getFileStorageManager(String location) {
        FileStorageManager fileStorageManager = new FileStorageManager(location);
        fileStorageManager.init();
        return fileStorageManager;
    }
}
