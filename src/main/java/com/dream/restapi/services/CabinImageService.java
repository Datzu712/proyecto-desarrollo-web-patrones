package com.dream.restapi.services;

import com.dream.restapi.model.Cabin;
import com.dream.restapi.model.CabinImage;
import com.dream.restapi.repository.CabinImageRepository;
import com.dream.restapi.dto.CabinImageDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CabinImageService {

    @Autowired
    private CabinImageRepository cabinImageRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public List<CabinImage> saveImages(Cabin cabin, List<MultipartFile> files) throws IOException {
        List<CabinImage> savedImages = new ArrayList<>();

        // Crear directorio si no existe
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            // Generar nombre único para el archivo
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            // Crear y guardar entrada en base de datos
            CabinImage cabinImage = new CabinImage();
            cabinImage.setCabin(cabin);
            cabinImage.setImageUrl("/uploads/" + uniqueFilename);
            cabinImage.setIsMain(savedImages.isEmpty()); // Primera imagen como principal
            cabinImage.setCreatedAt(LocalDateTime.now());

            // Guardar la imagen como BLOB
            cabinImage.setImageBlob(file.getBytes());

            // Guardar archivo físicamente también para respaldo
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath);

            savedImages.add(cabinImageRepository.save(cabinImage));
        }

        return savedImages;
    }

    public CabinImage saveImageUrl(Cabin cabin, CabinImageDTO imageDTO) {
        CabinImage cabinImage = new CabinImage();
        cabinImage.setCabin(cabin);
        cabinImage.setImageUrl(imageDTO.getImageUrl());
        cabinImage.setIsMain(imageDTO.getIsMain() != null ? imageDTO.getIsMain() : false);
        cabinImage.setCreatedAt(LocalDateTime.now());

        return cabinImageRepository.save(cabinImage);
    }

    public boolean deleteImage(Integer imageId) {
        if (cabinImageRepository.existsById(imageId)) {
            CabinImage image = cabinImageRepository.findById(imageId).orElse(null);
            if (image != null) {
                // Si la imagen tiene una URL local (no externa), eliminar el archivo físico
                if (image.getImageUrl() != null && image.getImageUrl().startsWith("/uploads/")) {
                    String filename = image.getImageUrl().substring(image.getImageUrl().lastIndexOf("/") + 1);
                    Path filePath = Paths.get(uploadDir).resolve(filename);
                    try {
                        Files.deleteIfExists(filePath);
                    } catch (IOException e) {
                        // Registrar error pero continuar con la eliminación en DB
                        e.printStackTrace();
                    }
                }

                // Eliminar de la base de datos
                cabinImageRepository.delete(image);
                return true;
            }
        }
        return false;
    }

    public List<CabinImage> getImagesByCabinId(Integer cabinId) {
        return cabinImageRepository.findByCabinId(cabinId);
    }
}