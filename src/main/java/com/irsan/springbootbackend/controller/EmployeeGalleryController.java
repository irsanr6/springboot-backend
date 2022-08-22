package com.irsan.springbootbackend.controller;

import com.irsan.springbootbackend.service.EmployeeGalleryService;
import com.irsan.springbootbackend.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@RestController
@RequestMapping("/api/v1/gallery")
@RequiredArgsConstructor
public class EmployeeGalleryController {

    private final EmployeeGalleryService employeeGalleryService;

    @PostMapping("/upload")
    public BaseResponse<?> uploadGallery(
            @RequestParam("image") MultipartFile file,
            @RequestParam("employeeId") Long employeeId) throws IOException {
        return employeeGalleryService.uploadGallery(file, employeeId);
    }

    @GetMapping("/download")
    public BaseResponse<?> uploadGallery(@RequestParam("fileName") String fileName) {
        return employeeGalleryService.downloadGallery(fileName);
    }
}
