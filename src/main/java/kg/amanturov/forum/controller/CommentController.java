package kg.amanturov.forum.controller;

import kg.amanturov.forum.dto.request.CommentRequestDto;
import kg.amanturov.forum.dto.response.CommentResponseDto;
import kg.amanturov.forum.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/comment/{ticketId}")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long ticketId,
            @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto response = commentService.addComment(ticketId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getCommentsByTicket(@PathVariable Long ticketId) {
        List<CommentResponseDto> comments = commentService.getCommentsByTicket(ticketId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long ticketId,
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
