package kg.amanturov.forum.service;

import kg.amanturov.forum.dto.request.RepostRequestDto;
import kg.amanturov.forum.dto.response.RepostResponseDto;

import java.util.List;

public interface RepostService {

    RepostResponseDto addRepost(Long ticketId, RepostRequestDto requestDto);
    List<RepostResponseDto> getRepostsByTicket(Long ticketId);
}
