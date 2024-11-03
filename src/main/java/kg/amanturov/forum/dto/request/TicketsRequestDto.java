package kg.amanturov.forum.dto.request;

import lombok.Data;

@Data
public class TicketsRequestDto {
    private Long categoryTypeId;
    private Long ticketTypeId;
    private Long userId;
    private String title;
    private String description;
    private Boolean is_archived;
}
