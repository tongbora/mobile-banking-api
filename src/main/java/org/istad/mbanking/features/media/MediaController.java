package org.istad.mbanking.features.media;

import lombok.RequiredArgsConstructor;
import org.istad.mbanking.features.media.dto.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaController {
    private final MediaService mediaService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-single")
    public MediaResponse uploadSingle(@RequestPart MultipartFile file, String folderName) {
        return mediaService.uploadSingle(file, "IMAGE");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-multiple")
    public List<MediaResponse> uploadMultiple(@RequestPart List<MultipartFile> files, String folderName) {
        return mediaService.uploadMultiple(files, "IMAGE");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{mediaName}")
    public MediaResponse loadMediaByName(@PathVariable String mediaName, String folderName) {
        return mediaService.loadMediaByName(mediaName, "IMAGE");
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{mediaName}")
    public int deleteMediaByName(@PathVariable String mediaName, String folderName) {
        return mediaService.deleteMediaByName(mediaName, "IMAGE");
    }

    @GetMapping("/download/{mediaName}")
    public ResponseEntity<Resource> downloadMediaByName(@PathVariable String mediaName, String folderName) {
        Resource resource = mediaService.downloadMediaByName(mediaName, "IMAGE");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                // this line tells the browser to download resources
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/all/{folderName}")
    @ResponseStatus(HttpStatus.OK)
    public List<MediaResponse> loadAllMedia(@PathVariable String folderName) {
        return mediaService.loadAllMedia(folderName);
    }
}
