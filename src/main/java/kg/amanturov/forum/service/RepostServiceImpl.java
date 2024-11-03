package kg.amanturov.forum.service;

import kg.amanturov.forum.dto.request.RepostRequestDto;
import kg.amanturov.forum.dto.response.RepostResponseDto;
import kg.amanturov.forum.exception.NotFoundException;
import kg.amanturov.forum.exception.ServerErrorException;
import kg.amanturov.forum.mapper.RepostMapper;
import kg.amanturov.forum.model.Repost;
import kg.amanturov.forum.model.Tickets;
import kg.amanturov.forum.model.User;
import kg.amanturov.forum.repository.RepostRepository;
import kg.amanturov.forum.repository.TicketsRepository;
import kg.amanturov.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepostServiceImpl implements RepostService {

    private final RepostRepository repostRepository;
    private final TicketsRepository ticketsRepository;
    private final UserRepository userRepository;
    private final RepostMapper repostMapper;



    @Override
    public RepostResponseDto addRepost(Long ticketId, RepostRequestDto requestDto) {
        try {
            Tickets ticket = ticketsRepository.findById(ticketId)
                    .orElseThrow(() -> new NotFoundException("Ticket not found"));
            User user = userRepository.findById(requestDto.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));

            Repost repost = repostMapper.toRepost(requestDto);
            repost.setTicket(ticket);
            repost.setUser(user);
            repost.setRepostedDate(new Timestamp(System.currentTimeMillis()));

            Repost savedRepost = repostRepository.save(repost);
            return repostMapper.toRepostResponseDto(savedRepost);
        } catch (Exception e) {
            throw new ServerErrorException("Error creating repost: " + e.getCause().getLocalizedMessage());
        }
    }

    @Override
    public List<RepostResponseDto> getRepostsByTicket(Long ticketId) {
        try {
            return repostRepository.findByTicketId(ticketId)
                    .stream()
                    .map(repostMapper::toRepostResponseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServerErrorException("Error getting repost: " + e.getCause().getLocalizedMessage());
        }
    }
}

