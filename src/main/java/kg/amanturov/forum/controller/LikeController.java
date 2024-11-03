package kg.amanturov.forum.controller;

import kg.amanturov.forum.dto.request.LikeRequestDto;
import kg.amanturov.forum.dto.response.LikeResponseDto;
import kg.amanturov.forum.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/res/like/{ticketId}")
public class LikeController {

    private final LikeService likeService;


    @PostMapping
    public ResponseEntity<LikeResponseDto> addLike(
            @PathVariable Long ticketId,
            @RequestBody LikeRequestDto requestDto) {
        LikeResponseDto response = likeService.addLike(ticketId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeLike(
            @PathVariable Long ticketId,
            @RequestParam Long userId) {
        likeService.removeLike(ticketId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LikeResponseDto>> getLikesByTicket(@PathVariable Long ticketId) {
        List<LikeResponseDto> likes = likeService.getLikesByTicket(ticketId);
        return ResponseEntity.ok(likes);
    }
}


