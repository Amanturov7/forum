package kg.amanturov.forum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;


import kg.amanturov.forum.dto.AttachmentRequestDto;
import kg.amanturov.forum.dto.AttachmentResponseDto;
import kg.amanturov.forum.exception.MyFileNotFoundException;
import kg.amanturov.forum.service.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@RestController
@RequestMapping("/rest/attachments")
public class AttachmentController {

    private final FileStorageService service;

    private final ObjectMapper objectMapper;

    public AttachmentController(FileStorageService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public AttachmentResponseDto uploadFile(@RequestPart String dto, @RequestPart MultipartFile file) throws IOException {
            AttachmentRequestDto attachmentRequestDto = objectMapper.readValue(dto, AttachmentRequestDto.class);
            return service.saveAttachment(file, attachmentRequestDto);
    }


    @DeleteMapping("/delete/tickets/{id}")
    public ResponseEntity<String> deleteAttachmentByApplicationsId(@PathVariable Long id) {
            service.deleteByTicketsId(id);
            return ResponseEntity.ok("Attachment deleted successfully");
    }



    @GetMapping(value = "/download/comment/{id}")
    public ResponseEntity<byte[]> findByApplicationsId(@PathVariable Long id) throws IOException {
        AttachmentResponseDto attachments = service.findByComment(id);
        if (Objects.isNull(attachments)) {
            return ResponseEntity.notFound().build();
        }
        byte[] fileContent;
            fileContent = readFileContent(attachments.getFilePath());
        String sanitizedFileName = attachments.getName();
        sanitizedFileName = sanitizedFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", sanitizedFileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileContent.length)
                .body(fileContent);
    }




    @GetMapping(value = "/download/tickets/{id}")
    public ResponseEntity<byte[]> findByTicketsId(@PathVariable Long id) throws IOException {
        AttachmentResponseDto attachments = service.findByTicketsId(id);
        if (Objects.isNull(attachments)) {
            return ResponseEntity.notFound().build();
        }
        byte[] fileContent;
        try {
            fileContent = readFileContent(attachments.getFilePath());
        } catch (IOException e) {
            throw new MyFileNotFoundException("Ошибка при чтении файла: " + e.getMessage());
        }
        String sanitizedFileName = attachments.getName();
        sanitizedFileName = sanitizedFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", sanitizedFileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileContent.length)
                .body(fileContent);
    }


    @GetMapping(value = "/download/avatar/user/{id}")
    public ResponseEntity<byte[]> findByUserId(@PathVariable Long id) throws IOException {
        AttachmentResponseDto attachments = service.findByUserIdAndType(id);
        if (Objects.isNull(attachments)) {
            return ResponseEntity.notFound().build();
        }
        byte[] fileContent;
        try {
            fileContent = readFileContent(attachments.getFilePath());
        } catch (IOException e) {
            throw new MyFileNotFoundException("Ошибка при чтении файла: " + e.getMessage());
        }
        String sanitizedFileName = attachments.getName();
        sanitizedFileName = sanitizedFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", sanitizedFileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileContent.length)
                .body(fileContent);
    }



    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws Exception {
        AttachmentResponseDto attachment = service.getAttachmentById(id);
        if (Objects.isNull(attachment)) {
            return ResponseEntity.notFound().build();
        }
        byte[] fileContent;
        try {
            fileContent = readFileContent(attachment.getFilePath());
        } catch (IOException e) {
            throw new MyFileNotFoundException("Ошибка при чтении файла: " + e.getMessage());
        }
        String sanitizedFileName = attachment.getName();
        sanitizedFileName = sanitizedFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", sanitizedFileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileContent.length)
                .body(fileContent);
    }

    //    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public @ResponseBody byte[] getFileByPath(@RequestParam("path") String path) throws IOException {
//        return readFileContent(path);
//    }
//
//
    private byte[] readFileContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

}