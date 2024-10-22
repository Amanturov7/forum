package kg.amanturov.forum.dto.request;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TicketsRequestDto {
    private Long statusId;
    private Long userId;
    private String title;
    private String description;
}
