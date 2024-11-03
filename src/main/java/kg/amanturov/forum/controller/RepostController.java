package kg.amanturov.forum.controller;

import kg.amanturov.forum.dto.request.RepostRequestDto;
import kg.amanturov.forum.dto.response.RepostResponseDto;
import kg.amanturov.forum.service.RepostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rest/repost/{ticketId}")
public class RepostController {

    private final RepostService repostService;

    @Autowired
    public RepostController(RepostService repostService) {
        this.repostService = repostService;
    }

    @PostMapping
    public ResponseEntity<RepostResponseDto> addRepost(
            @PathVariable Long ticketId,
            @RequestBody RepostRequestDto requestDto) {
        RepostResponseDto response = repostService.addRepost(ticketId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RepostResponseDto>> getRepostsByTicket(@PathVariable Long ticketId) {
        List<RepostResponseDto> reposts = repostService.getRepostsByTicket(ticketId);
        return ResponseEntity.ok(reposts);
    }
}
