package kg.amanturov.forum.service;

import kg.amanturov.forum.dto.request.TicketsRequestDto;
import kg.amanturov.forum.dto.response.TicketsResponseDto;
import kg.amanturov.forum.mapper.TicketsMapper;
import kg.amanturov.forum.model.Tickets;
import kg.amanturov.forum.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketsServiceImpl implements TicketsService {

    private final TicketsRepository ticketsRepository;
    private final TicketsMapper ticketsMapper;

    @Autowired
    public TicketsServiceImpl(TicketsRepository ticketsRepository, TicketsMapper ticketsMapper) {
        this.ticketsRepository = ticketsRepository;
        this.ticketsMapper = ticketsMapper;
    }

    @Override
    public TicketsResponseDto createTicket(TicketsRequestDto requestDto) {
        Tickets ticket = ticketsMapper.toEntity(requestDto);
        ticket = ticketsRepository.save(ticket);
        return ticketsMapper.toResponseDto(ticket);
    }

    @Override
    public TicketsResponseDto updateTicket(Long id, TicketsRequestDto requestDto) {
        Tickets ticket = ticketsMapper.toEntity(requestDto);
        ticket.setId(id);
        ticket = ticketsRepository.save(ticket);
        return ticketsMapper.toResponseDto(ticket);
    }

    @Override
    public TicketsResponseDto getTicketById(Long id) {
        return ticketsRepository.findById(id)
                .map(ticketsMapper::toResponseDto)
                .orElse(null);
    }

    @Override
    public List<TicketsResponseDto> getAllTickets() {
        return ticketsRepository.findAll().stream()
                .map(ticketsMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTicket(Long id) {
        ticketsRepository.deleteById(id);
    }
}
