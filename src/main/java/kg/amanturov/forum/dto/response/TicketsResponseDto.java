package kg.amanturov.forum.dto.response;

import kg.amanturov.forum.dto.AttachmentResponseDto;
import lombok.Data;

import java.sql.Timestamp;
@Data
public class TicketsResponseDto {
    private Long id;
    private Long statusId;
    private Long userId;
    private String title;
    private String description;
    private Boolean isArchived;
    private Timestamp createdDate;
    private Timestamp updateDate;
    private Long categoryTypeId;
    private Long ticketTypeId;

    private Long commentCount;
    private Long likeCount;
    private Long repostCount;
    private Boolean isLikedByCurrentUser;
    private AttachmentResponseDto attachmentResponseDto;

}
