package kg.amanturov.forum.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long userId;
    private String content;
}
