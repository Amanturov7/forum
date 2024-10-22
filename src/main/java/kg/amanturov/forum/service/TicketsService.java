package kg.amanturov.forum.service;

import kg.amanturov.forum.dto.request.TicketsRequestDto;
import kg.amanturov.forum.dto.response.TicketsResponseDto;
import java.util.List;

public interface TicketsService {
    TicketsResponseDto createTicket(TicketsRequestDto requestDto);
    TicketsResponseDto updateTicket(Long id, TicketsRequestDto requestDto);
    TicketsResponseDto getTicketById(Long id);
    List<TicketsResponseDto> getAllTickets();
    void deleteTicket(Long id);
}
