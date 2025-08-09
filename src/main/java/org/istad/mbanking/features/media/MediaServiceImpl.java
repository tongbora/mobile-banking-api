package org.istad.mbanking.features.media;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.istad.mbanking.features.media.dto.MediaResponse;
import org.istad.mbanking.util.FileSizeUtil;
import org.istad.mbanking.util.MediaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService{


    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String requestMaxSize;


    @Override
    public MediaResponse uploadSingle(MultipartFile file, String folderName) {
        Long maxSize = FileSizeUtil.parseSizeToBytes(requestMaxSize);

        // Generate a unique name for file
        String newName = UUID.randomUUID().toString();

        // Extract file extension from file upload
        String extension = MediaUtil.extractExtension(file.getOriginalFilename());
        log.info("File extension: {}", extension);
        newName = newName + "." + extension;
        log.info("New file name: {}", newName);

        // Copy file
        Path path = Paths.get(serverPath, folderName, newName);
        log.info("File path: {}", path);
        if(file.getSize() > maxSize){
            throw new MaxUploadSizeExceededException(maxSize);
        }

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        }
        return MediaResponse.builder()
                .name(newName)
                .contentType(file.getContentType())
                .extension(extension)
                .size(file.getSize())
                .uri(baseUri + folderName + "/" + newName)
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName) {
        // create empty array list , wait to add single file
        List<MediaResponse> mediaResponses = new ArrayList<>();

        files.forEach(file -> {
                MediaResponse mediaResponse = uploadSingle(file, folderName);
                mediaResponses.add(mediaResponse);
        });
        return mediaResponses;
    }

    @Override
    public MediaResponse loadMediaByName(String mediaName, String folderName) {

        Path path = Paths.get(serverPath, folderName, mediaName);
        log.info("File path: {}", path);
        try {

            // This method is mine
            // I use media name to get resources

            // Check
            if(!Files.exists(path)){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Media has not been found.");
            }

            // Detect content type
            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = "application/octet-stream"; // fallback
            }
            Long size = Files.size(path);

            // This method is teacher's
            // Teacher use resource class to get resource by media name
            Resource resource = new UrlResource(path.toUri());
            if(!resource.exists()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Media has not been found.");
            }


            return MediaResponse.builder()
//                    .name(resource.getFilename())
                    .name(mediaName)
                    .contentType(contentType)
                    .extension(MediaUtil.extractExtension(mediaName))
                    .size(size)
                    .uri(baseUri + folderName + "/" + mediaName)
                    .build();

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        }
    }

    @Override
    public int deleteMediaByName(String mediaName, String folderName) {
        Path path = Paths.get(serverPath, folderName, mediaName);
        log.info("File path: {}", path);
        try {
            if(Files.deleteIfExists(path)){
                return 1;
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Media has not been found.");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        }
    }

    @Override
    public Resource downloadMediaByName(String mediaName, String folderName) {
        Path path = Paths.get(serverPath, folderName, mediaName);

        try {
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists()){
                return resource;
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Media has not been found.");
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        }
    }

    @Override
    public List<MediaResponse> loadAllMedia(String folderName) {
        Path path = Paths.get(serverPath, folderName);
        log.info("File path: {}", path);

        try {
            // Check if the path exists and is a directory
            if (!Files.exists(path)) {
                log.warn("Path does not exist: {}", path);
                return List.of();
            }

            if (!Files.isDirectory(path)) {
                log.warn("Path is not a directory: {}", path);
                return List.of();
            }

            // Read all files in the directory
            List<MediaResponse> mediaResponses = new ArrayList<>();

            Files.list(path)
                    .filter(Files::isRegularFile) // Only include regular files
                    .forEach(file -> {
                        try {
                            Resource resource = new UrlResource(file.toUri());
                            if (resource.exists()) {
                                MediaResponse response = MediaResponse.builder()
                                        .name(resource.getFilename())
                                        .contentType(Files.probeContentType(file))
                                        .extension(MediaUtil.extractExtension(resource.getFilename()))
                                        .size(Files.size(file))
                                        .uri(baseUri + folderName + "/" + resource.getFilename())
                                        .build();
                                mediaResponses.add(response);
                            }
                        } catch (Exception e) {
                            log.error("Error processing file: {}", file.getFileName(), e);
                        }
                    });

            return mediaResponses;

        } catch (IOException e) {
            log.error("Error reading directory: {}", path, e);
            throw new RuntimeException("Failed to load media files", e);
        }
    }
}
