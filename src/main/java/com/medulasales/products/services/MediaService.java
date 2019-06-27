package com.medulasales.products.services;

import com.medulasales.products.exceptions.BusinessException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public interface MediaService {
    public String storeMedia(@NonNull MultipartFile mediaFile);
    public String storePicture(@NonNull MultipartFile pictureFile) throws BusinessException;
    public String storeVideo(@NonNull MultipartFile videoFile) throws BusinessException;
    public String storeAudio(@NonNull MultipartFile audioFile) throws BusinessException;
    public String storeDocument(@NonNull MultipartFile documentFile) throws BusinessException;
    public byte[] getMediaBytes(@NonNull @NotEmpty String id) throws BusinessException;
    public void removeMedia(@NonNull @NotEmpty String id) throws BusinessException;
}
