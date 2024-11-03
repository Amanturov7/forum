package kg.amanturov.forum.service;

import kg.amanturov.forum.dto.request.LikeRequestDto;
import kg.amanturov.forum.dto.response.LikeResponseDto;

import java.util.List;

public interface LikeService {

    LikeResponseDto addLike(Long ticketId, LikeRequestDto requestDto);

    void removeLike(Long ticketId, Long userId);

    List<LikeResponseDto> getLikesByTicket(Long ticketId);
}
