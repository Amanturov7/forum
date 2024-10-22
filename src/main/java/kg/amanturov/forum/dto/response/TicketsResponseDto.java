package kg.amanturov.forum.dto.response;

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
}
