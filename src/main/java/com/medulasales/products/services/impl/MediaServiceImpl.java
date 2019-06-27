package com.medulasales.products.services.impl;

import com.medulasales.products.exceptions.BusinessException;
import com.medulasales.products.providers.ContextProvider;
import com.medulasales.products.providers.UtilityProvider;
import com.medulasales.products.services.MediaService;
import com.medulasales.products.utils.JmUUIDGenerator;
import com.medulasales.products.utils.storage.FileStorageManager;
import com.medulasales.products.utils.storage.MediaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Service
@Validated
public class MediaServiceImpl implements MediaService {

    private ContextProvider contextProvider;
    private UtilityProvider utilityProvider;

    private FileStorageManager fileStorageManager;
    private JmUUIDGenerator uuidGenerator;
    private MediaUtils mediaUtils;

    @Autowired
    public MediaServiceImpl(ContextProvider contextProvider, UtilityProvider utilityProvider) throws BusinessException {
        this.contextProvider = contextProvider;
        this.utilityProvider = utilityProvider;
        this.fileStorageManager = utilityProvider.getFileStorageManager();
        this.uuidGenerator = utilityProvider.getJmUUIDGenerator();
        this.mediaUtils = utilityProvider.getMediaUtils();
    }

    @Override
    public String storeMedia(@NonNull MultipartFile mediaFile) {
        String filename = uuidGenerator.generateDashless().toString()+"."+mediaUtils.getExtension(mediaFile.getOriginalFilename());
        fileStorageManager.store(mediaFile);
        return filename;
    }

    @Override
    public String storePicture(@NonNull MultipartFile pictureFile) throws BusinessException {
        if(!mediaUtils.isImage(pictureFile)) {
            throw BusinessException.MEDIA_IMAGE_INVALID(pictureFile.getName());
        }
        return storeMedia(pictureFile);
    }

    @Override
    public String storeVideo(@NonNull MultipartFile videoFile) throws BusinessException {
        return null;
    }

    @Override
    public String storeAudio(@NonNull MultipartFile audioFile) throws BusinessException {
        return null;
    }

    @Override
    public String storeDocument(@NonNull MultipartFile documentFile) throws BusinessException {
        return null;
    }

    @Override
    public byte[] getMediaBytes(@NotEmpty String id) throws BusinessException {
        return null;
    }

    @Override
    public void removeMedia(@NotEmpty String id) throws BusinessException {

    }
}
