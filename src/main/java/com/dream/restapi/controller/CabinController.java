package com.dream.restapi.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.dream.restapi.model.Cabin;
import com.dream.restapi.model.CabinImage;
import com.dream.restapi.dto.CabinRequestDto;
import com.dream.restapi.dto.CabinImageDTO;
import com.dream.restapi.services.CabinService;
import com.dream.restapi.services.CabinImageService;

import java.util.List;
import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/cabin")
public class CabinController {

    @Autowired
    private CabinService cabinService;

    @Autowired
    private CabinImageService cabinImageService;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @GetMapping()
    public List<Cabin> getCabins() {
        return this.cabinService.getAllCabins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cabin> getCabinById(@PathVariable Integer id) {
        Cabin cabin = cabinService.getCabinById(id);
        if (cabin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cabin);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cabin> createCabin(@RequestBody CabinRequestDto cabinDto) {
        try {
            Cabin cabin = new Cabin();
            cabin.setName(cabinDto.getName());
            cabin.setCapacity(cabinDto.getCapacity());
            cabin.setPrice(cabinDto.getPrice());
            cabin.setStatus(cabinDto.getStatus());
            cabin.setLocation(cabinDto.getLocation());
            cabin.setDescription(cabinDto.getDescription());
            cabin.setCreatedAt(LocalDateTime.now());
            cabin.setUpdatedAt(LocalDateTime.now());

            // Primero guardamos la cabaña para tener su ID
            Cabin createdCabin = cabinService.createOrUpdateCabin(cabin);

            return ResponseEntity.ok(createdCabin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCabinImages(@PathVariable Integer id,
            @RequestParam("files") List<MultipartFile> files) {
        try {
            Cabin cabin = cabinService.getCabinById(id);
            if (cabin == null) {
                return ResponseEntity.notFound().build();
            }

            List<CabinImage> savedImages = cabinImageService.saveImages(cabin, files);
            return ResponseEntity.ok(savedImages);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error al procesar las imágenes: " + e.getMessage());
        }
    }

    @PostMapping(value = "/{id}/image-url", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCabinImageUrl(@PathVariable Integer id,
            @RequestBody CabinImageDTO imageDTO) {
        try {
            Cabin cabin = cabinService.getCabinById(id);
            if (cabin == null) {
                return ResponseEntity.notFound().build();
            }

            if (imageDTO.getImageUrl() == null || imageDTO.getImageUrl().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La URL de imagen no puede estar vacía");
            }

            CabinImage savedImage = cabinImageService.saveImageUrl(cabin, imageDTO);
            return ResponseEntity.ok(savedImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar la URL de imagen: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cabin> updateCabin(@PathVariable Integer id, @RequestBody CabinRequestDto cabinDto) {
        try {
            Cabin existingCabin = cabinService.getCabinById(id);
            if (existingCabin == null) {
                return ResponseEntity.notFound().build();
            }

            existingCabin.setName(cabinDto.getName());
            existingCabin.setCapacity(cabinDto.getCapacity());
            existingCabin.setPrice(cabinDto.getPrice());
            existingCabin.setStatus(cabinDto.getStatus());
            existingCabin.setLocation(cabinDto.getLocation());
            existingCabin.setDescription(cabinDto.getDescription());
            existingCabin.setUpdatedAt(LocalDateTime.now());

            Cabin updatedCabin = cabinService.createOrUpdateCabin(existingCabin);
            return ResponseEntity.ok(updatedCabin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCabin(@PathVariable Integer id) {
        try {
            boolean deleted = cabinService.deleteCabin(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Integer imageId) {
        try {
            boolean deleted = cabinImageService.deleteImage(imageId);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
