package kg.amanturov.forum.service;

import kg.amanturov.forum.dto.request.TicketsRequestDto;
import kg.amanturov.forum.dto.response.TicketsResponseDto;
import kg.amanturov.forum.exception.ServerErrorException;
import kg.amanturov.forum.mapper.TicketsMapper;
import kg.amanturov.forum.model.Tickets;
import kg.amanturov.forum.repository.*;
import lombok.AllArgsConstructor;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketsServiceImpl implements TicketsService {

    private final TicketsRepository ticketsRepository;
    private final LikeRepository likeRepository;
    private final RepostRepository repostRepository;
    private final CommentRepository commentRepository;


    private final TicketsMapper ticketsMapper;



    @Override
    public TicketsResponseDto createTicket(TicketsRequestDto requestDto) {
        Tickets ticket = ticketsMapper.toEntity(requestDto);

        try {
            ticket = ticketsRepository.save(ticket);
            return ticketsMapper.toResponseDto(ticket);
        } catch (Exception e) {
            throw new ServerErrorException("Error creating ticket: " + e.getCause().getLocalizedMessage());
        }
    }

    @Override
    public TicketsResponseDto updateTicket(Long id, TicketsRequestDto requestDto) {
        Tickets ticket = ticketsMapper.toEntity(requestDto);
        ticket.setId(id);
        try {
            ticket = ticketsRepository.save(ticket);
            return ticketsMapper.toResponseDto(ticket);
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Error updating ticket: " + e.getMessage());
        }
    }

    @Override
    public TicketsResponseDto getTicketById(Long id, Long currentUserId) {
        try {
            return ticketsRepository.findById(id).map(ticket -> {
                TicketsResponseDto responseDto = ticketsMapper.toResponseDto(ticket);
                responseDto.setCommentCount(commentRepository.countByTicketId(id));
                responseDto.setLikeCount(likeRepository.countByTicketId(id));
                responseDto.setRepostCount(repostRepository.countByTicketId(id));

                responseDto.setIsLikedByCurrentUser(likeRepository.existsByTicketIdAndUserId(id, currentUserId));
                responseDto.setIsLikedByCurrentUser(likeRepository.existsByTicketIdAndUserId(id, currentUserId));
                return responseDto;
            }).orElse(null);
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Error getting ticket: " + e.getMessage());
        }
    }


    @Override
    public List<TicketsResponseDto> getAllTickets() {
        try {
            return ticketsRepository.findAll().stream()
                    .map(ticketsMapper::toResponseDto)
                    .collect(Collectors.toList());
        } catch (ServerErrorException e) {
            throw new ServerErrorException("Error getting tickets: " + e.getMessage());
        }
    }

    @Override
    public void deleteTicket(Long id) {
        ticketsRepository.deleteById(id);
    }
}
