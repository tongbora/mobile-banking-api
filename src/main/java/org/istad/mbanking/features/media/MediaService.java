package org.istad.mbanking.features.media;

import org.istad.mbanking.features.media.dto.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    MediaResponse uploadSingle(MultipartFile file, String folderName);
    List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName);

    MediaResponse loadMediaByName(String mediaName, String folderName);

    int deleteMediaByName(String mediaName, String folderName);
    Resource downloadMediaByName(String mediaName, String folderName);
    List<MediaResponse> loadAllMedia(String folderName);
}
