package kg.amanturov.forum.controller;

import kg.amanturov.forum.dto.request.TicketsRequestDto;
import kg.amanturov.forum.dto.response.TicketsResponseDto;
import kg.amanturov.forum.service.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/ticket")
public class TicketsController {

    private final TicketsService ticketsService;

    @Autowired
    public TicketsController(TicketsService ticketsService) {
        this.ticketsService = ticketsService;
    }

    @PostMapping("/create")
    public ResponseEntity<TicketsResponseDto> createTicket(@RequestBody TicketsRequestDto requestDto) {
        return ResponseEntity.ok(ticketsService.createTicket(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketsResponseDto> updateTicket(@PathVariable Long id, @RequestBody TicketsRequestDto requestDto) {
        return ResponseEntity.ok(ticketsService.updateTicket(id, requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketsResponseDto> getTicketById(@PathVariable Long id, @RequestParam(name = "userId") Long userId) {
        TicketsResponseDto ticket = ticketsService.getTicketById(id,userId);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TicketsResponseDto>> getAllTickets() {
        return ResponseEntity.ok(ticketsService.getAllTickets());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketsService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
