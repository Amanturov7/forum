package kg.amanturov.forum.service;


import kg.amanturov.forum.dto.AttachmentRequestDto;
import kg.amanturov.forum.dto.AttachmentResponseDto;
import kg.amanturov.forum.exception.MyFileNotFoundException;
import kg.amanturov.forum.exception.ServerErrorException;
import kg.amanturov.forum.model.Attachments;
import kg.amanturov.forum.model.Tickets;
import kg.amanturov.forum.repository.AttachmentRepository;
import kg.amanturov.forum.repository.CommentRepository;
import kg.amanturov.forum.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final AttachmentRepository repository;
    private final TicketsRepository ticketsRepository;

    private final UserService userService;
    private final CommentRepository commentRepository;




    @Value("${file.storage.photos}")
    private String photoDirectory;
    @Value("${file.storage.videos}")
    private String videoDirectory;

    public FileStorageServiceImpl(AttachmentRepository repository, TicketsRepository ticketsRepository, UserService userService, CommentRepository commentRepository) {
        this.repository = repository;
        this.ticketsRepository = ticketsRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }


    @Override
    public String save(MultipartFile file, String extension) {

        String uuid = Objects.requireNonNull(UUID.randomUUID().toString()).concat("."+extension);
        switch(extension) {
            case "mp4":
            case "mov":
                try {
                    Path root = Paths.get(videoDirectory);
                    Files.copy(file.getInputStream(), root.resolve(uuid));
                    return root+"/"+uuid;
                } catch (Exception e) {
                    throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
                }
            case "jpg":
            case "jpeg":
            case "png":
            case "image":
                try {
                    Path root = Paths.get(photoDirectory);
                    Files.copy(file.getInputStream(), root.resolve(uuid));
                    return root+"/"+uuid;
                } catch (Exception e) {
                    throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
                }
        }
        return null;
    }

//    @Override
//    public AttachmentResponseDto findByApplicationsId(Long id) {
//         Attachments Attachment = repository.findByApplicationsId(id);
//
//        if (Attachment != null) {
//            AttachmentResponseDto responseDto = mapToAttachmentResponseDto(Attachment);
//            String sanitizedFileName = responseDto.getName();
//            sanitizedFileName = sanitizedFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
//            responseDto.setDownloadUrl("http://localhost:8080/rest/attachment/download/" + responseDto.getAttachmentId());
//            responseDto.setName(sanitizedFileName);
//            return responseDto;
//        } else {
//            return null;
//        }
//    }

    @Override
    public AttachmentResponseDto findByComment(Long id) {
        Attachments Attachment = repository.findByCommentId(id);

        if (Attachment != null) {
            AttachmentResponseDto responseDto = mapToAttachmentResponseDto(Attachment);
            String sanitizedFileName = responseDto.getName();
            sanitizedFileName = sanitizedFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
            responseDto.setDownloadUrl("http://localhost:8080/rest/attachment/download/" + responseDto.getAttachmentId());
            responseDto.setName(sanitizedFileName);
            return responseDto;
        } else {
            return null;
        }
    }


    @Override
    public AttachmentResponseDto findByTicketsId(Long id) {
        Attachments Attachment = repository.findByTicketsId(id);

        if (Attachment != null) {
            AttachmentResponseDto responseDto = mapToAttachmentResponseDto(Attachment);
            String sanitizedFileName = responseDto.getName();
            sanitizedFileName = sanitizedFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
            responseDto.setDownloadUrl("http://localhost:8080/rest/attachment/download/" + responseDto.getAttachmentId());
            responseDto.setName(sanitizedFileName);
            return responseDto;
        } else {
            return null;
        }
    }

    @Override
    public AttachmentResponseDto findByUserId(Long id) {
        return null;
    }

    @Override
    public AttachmentResponseDto findByUserIdAndType(Long id) {
        Attachments Attachment = repository.findByUserIdAndType(id, "avatar");

        if (Attachment != null) {
            AttachmentResponseDto responseDto = mapToAttachmentResponseDto(Attachment);
            String sanitizedFileName = responseDto.getName();
            sanitizedFileName = sanitizedFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
            responseDto.setDownloadUrl("http://localhost:8080/rest/attachment/download/" + responseDto.getAttachmentId());
            responseDto.setName(sanitizedFileName);
            return responseDto;
        } else {
            return null;
        }
    }



    @Override
    public AttachmentResponseDto saveAttachment(MultipartFile file, AttachmentRequestDto dto) throws IOException {
        AttachmentResponseDto responseDto = new AttachmentResponseDto();
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String targetDirectory;
        switch (extension.toLowerCase()) {
            case "mp4":
            case "csv":
                targetDirectory = videoDirectory;
                break;
            case "jpg":
            case "jpeg":
            case "png":
            case "image":
                targetDirectory = photoDirectory;
                break;
            default:
                throw new RuntimeException("Не поддерживаемый тип файла: " + extension);
        }
        String fileName = Objects.requireNonNull(dto.getType()).concat("№" + UUID.randomUUID().toString()).concat("." + extension);
        Path root = Paths.get(targetDirectory);
        Files.createDirectories(root);
        Path filePath = root.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        responseDto.setFilePath(filePath.toString());
        responseDto.setType(dto.getType());
        responseDto.setExtension(extension);
        responseDto.setName(fileName);
//        responseDto.setAppicationsId(dto.getApplicationsId());
        responseDto.setUserId(dto.getUserProfileId());
        responseDto.setTicketsId(dto.getTicketsId());
        responseDto.setCommentId(dto.getCommentId());
        responseDto.setDescription(dto.getDescription());
        responseDto.setOriginName(dto.getOriginName());
        Attachments attachments = convertDtoToEntity(responseDto);
        Attachments saved = repository.save(attachments);
        responseDto.setAttachmentId(saved.getId());

//        if (dto.getReviewsId() != null) {
//                Review review = reviewService.findById(dto.getReviewsId());
//                CommonReference reference = new CommonReference();
//                if(review.getEcologicFactors()!=null){
//                    reference =review.getEcologicFactors();
//                }
//                if(review.getRoads()!=null){
//                    reference =review.getRoads();
//                }
//                if(review.getRoadSign()!=null){
//                    reference =review.getRoadSign();
//                }
//                if(review.getLights()!=null){
//                    reference =review.getLights();
//                }
//
//            String caption = "Отзыв №: " + review.getId() + "\n" +
//                    "Тип отзыва: " + reference.getTitle() + "\n" +
//                    "Описание: " + review.getDescription() + "\n" +
//                    "Адрес: " + review.getLocationAddress() + "\n" +
//                    "Дата создания: " + review.getCreatedDate();
//                myTelegramBot.sendPhotoWithCaptionToChannel(responseDto.getFilePath(), caption);
//        }
//
//        if (dto.getApplicationsId() != null) {
//            Applications application = applicationsService.findById(dto.getApplicationsId());
//            String caption = "Нарушение №: " + application.getId() + "\n" +
//                    "Тип нарушения: " + application.getTitle() + "\n" +
//                    "Гос номер: " + application.getNumberAuto() + "\n" +
//                    "Адрес: " + application.getPlace() + "\n" +
//                    "Дата нарушения: " + application.getDateOfViolation();
//                myTelegramBot.sendPhotoWithCaptionToChannel(responseDto.getFilePath(), caption);
//        }

        return responseDto;
    }

    private AttachmentResponseDto mapToAttachmentResponseDto(Attachments attachment) {
        AttachmentResponseDto responseDto = new AttachmentResponseDto();
        responseDto.setFilePath(attachment.getPath());
        responseDto.setType(attachment.getType());
        responseDto.setExtension(attachment.getExtension());
        responseDto.setName(attachment.getName());

        if(attachment.getTickets() != null ){
            responseDto.setTicketsId(attachment.getTickets().getId());
        }
        responseDto.setAttachmentId(attachment.getId());
        if(attachment.getUser() != null) {
            responseDto.setUserId(attachment.getUser().getId());
        }
        if(attachment.getComment() != null) {
            responseDto.setCommentId(attachment.getComment().getId());
        }

        return responseDto;
    }


    @Override
    public void deleteByTicketsId(Long id) {
        Attachments attachment = repository.findByTicketsId(id);

        if (attachment == null) {
            throw new MyFileNotFoundException("photo not found for ticket ID: " + id);
        }
        try {
            Files.delete(Paths.get(attachment.getPath()));
            deleteAttachmentById(attachment.getId());
        } catch (IOException e) {
            throw new ServerErrorException("Error deleting photo of ticket Id: " + id+ ", : " + e.getMessage());
        }
    }


    @Override
    public void deleteByCommentId(Long id) {
        Attachments attachment = repository.findByCommentId(id);

        if (attachment == null) {
            throw new MyFileNotFoundException("photo not found for comment ID: " + id);
        }
        try {
            Files.delete(Paths.get(attachment.getPath()));
            deleteAttachmentById(attachment.getId());
        } catch (IOException e) {
            throw new ServerErrorException("Error deleting photo of comment Id: " + id+ ", : " + e.getMessage());
        }
    }


    @Override
    public AttachmentResponseDto getAttachmentById(Long id) {
        Attachments attachment = repository.findById(id).orElse(null);
        if (attachment != null) {
            AttachmentResponseDto responseDto = new AttachmentResponseDto();
            responseDto.setAttachmentId(attachment.getId());
            responseDto.setFilePath(attachment.getPath());
            responseDto.setType(attachment.getType());
            responseDto.setDescription(attachment.getDescription());
            responseDto.setOriginName(attachment.getName());
            responseDto.setExtension(attachment.getExtension());
            if(attachment.getTickets() != null ){
                responseDto.setTicketsId(attachment.getTickets().getId());
            }
            if(attachment.getUser() != null) {
                responseDto.setUserId(attachment.getUser().getId());
            }
            if(attachment.getComment() != null) {
                responseDto.setCommentId(attachment.getComment().getId());
            }
            responseDto.setName(attachment.getName());
            return responseDto;
        } else {
            return null;
        }
    }


    @Override
    public void saveAvatar(MultipartFile file, Long userId) {
        try {
            String avatarDirectory = photoDirectory;

            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            if (!extension.toLowerCase().matches("jpg|jpeg|png")) {
                throw new IllegalArgumentException("invalid type of file, use [jpg, jpeg, png]: " + extension);
            }

            String fileName = "avatar_" + userId + "." + extension;

            Path targetLocation = Paths.get(avatarDirectory + fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Attachments attachment = new Attachments();
            attachment.setName(fileName);
            attachment.setPath(avatarDirectory + fileName);
            attachment.setType("avatar");
            attachment.setUser(userService.findById(userId));
            attachment.setDescription("User Avatar");
            repository.save(attachment);
        } catch (IOException ex) {
            throw new ServerErrorException("Failed to save avatar file " + ex.getMessage());
        }
    }


    @Override
    public void updateAvatar(MultipartFile file, Long userId) {
        deleteAvatar(userId);
        saveAvatar(file, userId);
    }

    @Override
    public void deleteAvatar(Long userId) {
        Attachments currentAvatar = repository.findByUserIdAndType(userId, "avatar");

        if (currentAvatar != null) {
            try {
                Path filePath = Paths.get(currentAvatar.getPath());
                Files.deleteIfExists(filePath);
            } catch (IOException ex) {
                throw new ServerErrorException("Failed to delete avatar file: " + ex.getMessage());
            }

            repository.delete(currentAvatar);
        }
    }




    @Override
    public void deleteAttachmentById(Long id) {
        repository.deleteById(id);
    }

    private Attachments convertDtoToEntity(AttachmentResponseDto responseDto) {
        Attachments attachments = new Attachments();
        attachments.setExtension(responseDto.getExtension());
        attachments.setType(responseDto.getType());
        attachments.setName(responseDto.getName());
        attachments.setPath(responseDto.getFilePath());
        attachments.setDescription(responseDto.getDescription());

        if (responseDto.getTicketsId() != null) {
            Optional<Tickets> ticketsOptional =ticketsRepository.findById(responseDto.getTicketsId());
            ticketsOptional.ifPresent(attachments::setTickets);
        }

        if(responseDto.getUserId() != null) {
            attachments.setUser(userService.findById(responseDto.getUserId()));
        }
        if(responseDto.getCommentId() != null) {
            attachments.setComment(commentRepository.findById(responseDto.getCommentId()).orElse(null));
        }
//        if(responseDto.getReviewsId() != null) {
//            attachments.setReviews(reviewService.findById(responseDto.getReviewsId()));
//        }
        return attachments;
    }
    private AttachmentResponseDto convertEntityToDto(Attachments attachments) {
        AttachmentResponseDto responseDto = new AttachmentResponseDto();
        responseDto.setExtension(attachments.getExtension());
        responseDto.setType(attachments.getType());
        responseDto.setName(attachments.getName());
        responseDto.setFilePath(attachments.getPath());
        responseDto.setAttachmentId(attachments.getId());
        responseDto.setDescription(attachments.getDescription());
        if(attachments.getTickets() != null ){
            responseDto.setTicketsId(attachments.getTickets().getId());
        }
        if(attachments.getUser() != null) {
            responseDto.setUserId(attachments.getUser().getId());
        }
        if(attachments.getComment() != null) {
            responseDto.setCommentId(attachments.getComment().getId());
        }
        return responseDto;
    }

    @Override
    public Resource convertFileFromPath(Attachments attachments) throws IOException {
        Path filePath = Paths.get(attachments.getPath());
        byte[] fileData = Files.readAllBytes(filePath);
        return new ByteArrayResource(fileData);
    }

    @Override
    public AttachmentResponseDto getFileById(Long id) throws IOException {
        Attachments getById = repository.findById(id).get();
        AttachmentResponseDto responseDto = convertEntityToDto(getById);
        responseDto.setFile(convertFileFromPath(getById));
        return responseDto;
    }

    @Override
    public Resource load(String fileName, Path root) {
        try {
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ServerErrorException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new ServerErrorException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }
}
