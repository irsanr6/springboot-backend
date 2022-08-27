package com.irsan.springbootbackend.repository;

import com.irsan.springbootbackend.entity.EmployeeGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Repository
public interface EmployeeGalleryRepository extends JpaRepository<EmployeeGallery, Long> {
    Optional<EmployeeGallery> findByGalleryName(String galleryName);
}
