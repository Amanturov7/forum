package kg.amanturov.forum.service;

import kg.amanturov.forum.dto.request.LikeRequestDto;
import kg.amanturov.forum.dto.response.LikeResponseDto;
import kg.amanturov.forum.mapper.LikeMapper;
import kg.amanturov.forum.model.Like;
import kg.amanturov.forum.model.Tickets;
import kg.amanturov.forum.model.User;
import kg.amanturov.forum.repository.LikeRepository;
import kg.amanturov.forum.repository.TicketsRepository;
import kg.amanturov.forum.repository.UserRepository;
import kg.amanturov.forum.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final TicketsRepository ticketsRepository;
    private final UserRepository userRepository;
    private final LikeMapper likeMapper;

    @Override
    public LikeResponseDto addLike(Long ticketId, LikeRequestDto requestDto) {
        Tickets ticket = ticketsRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Like like = likeMapper.toLike(requestDto);
        like.setTicket(ticket);
        like.setUser(user);
        like.setLikedDate(new Timestamp(System.currentTimeMillis()));

        Like savedLike = likeRepository.save(like);

        return likeMapper.toLikeResponseDto(savedLike);
    }

    @Override
    public void removeLike(Long ticketId, Long userId) {
        Like like = likeRepository.findByTicketIdAndUserId(ticketId, userId)
                .orElseThrow(() -> new RuntimeException("Like not found"));
        likeRepository.delete(like);
    }

    @Override
    public List<LikeResponseDto> getLikesByTicket(Long ticketId) {

        return likeRepository.findByTicketId(ticketId)
                .stream()
                .map(likeMapper::toLikeResponseDto)
                .collect(Collectors.toList());
    }
}
