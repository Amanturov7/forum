package kg.amanturov.forum.dto;


import lombok.Data;

@Data
public class AttachmentRequestDto {
    private String type;
    private String originName;
    private String description;
    private Long userProfileId;
    private Long commentId;
    private Long ticketsId;
}
