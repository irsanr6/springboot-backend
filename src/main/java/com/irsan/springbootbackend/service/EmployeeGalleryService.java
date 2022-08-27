package com.irsan.springbootbackend.service;

import com.irsan.springbootbackend.entity.EmployeeGallery;
import com.irsan.springbootbackend.repository.EmployeeGalleryRepository;
import com.irsan.springbootbackend.utils.BaseResponse;
import com.irsan.springbootbackend.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Service
@RequiredArgsConstructor
public class EmployeeGalleryService {

    private final EmployeeGalleryRepository employeeGalleryRepository;

    public BaseResponse<?> uploadGallery(MultipartFile file, Long employeeId) throws IOException {

        EmployeeGallery gallery = employeeGalleryRepository.save(EmployeeGallery.builder()
                .employeeId(employeeId)
                .galleryName(Helper.traversWordByWord(file.getOriginalFilename()))
                .dataType(file.getContentType())
                .imageData(Helper.compressImage(file.getBytes()))
                .build());
        if (gallery != null) {
            return BaseResponse.ok("File uploaded successfully", Helper.traversWordByWord(file.getOriginalFilename()));
        } else {
            return BaseResponse.error200("Failed to upload image");
        }

    }

    public BaseResponse<?> downloadGallery(String fileName) {

        Optional<EmployeeGallery> gallery = employeeGalleryRepository.findByGalleryName(fileName);
        if (gallery.isPresent()) {
            byte[] images = Helper.decompressImage(gallery.get().getImageData());
            return BaseResponse.ok(images);
        } else {
            return BaseResponse.error200("Failed not found");
        }

    }

}
