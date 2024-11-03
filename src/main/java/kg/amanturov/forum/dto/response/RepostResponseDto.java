package kg.amanturov.forum.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class RepostResponseDto {
    private Long id;
    private Long ticketId;
    private Long userId;
    private Timestamp repostedDate;
}
