package kg.amanturov.forum.service;


import kg.amanturov.forum.dto.AttachmentRequestDto;
import kg.amanturov.forum.dto.AttachmentResponseDto;
import kg.amanturov.forum.model.Attachments;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {


    String save(MultipartFile file, String path);



//    AttachmentResponseDto findByReviewsId(Long id);
//    AttachmentResponseDto findByApplicationsId(Long id);
//    AttachmentResponseDto findByTicketsId(Long id);


    AttachmentResponseDto findByUserId(Long id);

    AttachmentResponseDto findByUserIdAndType(Long id);

    AttachmentResponseDto saveAttachment(MultipartFile file, AttachmentRequestDto dto) throws IOException;


//    void deleteByApplicationsId(Long id);

    AttachmentResponseDto getAttachmentById(Long id);

    void saveAvatar(MultipartFile file, Long userId);

    void updateAvatar(MultipartFile file, Long userId);

    void deleteAvatar(Long userId);

    void deleteAttachmentById(Long id);

    Resource convertFileFromPath(Attachments attachments) throws IOException;

    AttachmentResponseDto getFileById(Long id) throws IOException;

    Resource load(String fileName, Path root);

    void deleteAll();

    Stream<Path> loadAll();


}
