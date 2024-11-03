package kg.amanturov.forum.service;

import kg.amanturov.forum.dto.request.CommentRequestDto;
import kg.amanturov.forum.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto addComment(Long ticketId, CommentRequestDto requestDto);

    List<CommentResponseDto> getCommentsByTicket(Long ticketId);

    void deleteComment(Long commentId, Long userId);
}
