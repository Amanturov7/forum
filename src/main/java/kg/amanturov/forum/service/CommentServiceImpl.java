package kg.amanturov.forum.service;

import kg.amanturov.forum.dto.request.CommentRequestDto;
import kg.amanturov.forum.dto.response.CommentResponseDto;
import kg.amanturov.forum.exception.NotFoundException;
import kg.amanturov.forum.exception.ServerErrorException;
import kg.amanturov.forum.mapper.CommentMapper;
import kg.amanturov.forum.model.Comment;
import kg.amanturov.forum.model.Tickets;
import kg.amanturov.forum.model.User;
import kg.amanturov.forum.repository.CommentRepository;
import kg.amanturov.forum.repository.TicketsRepository;
import kg.amanturov.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TicketsRepository ticketsRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(
            CommentRepository commentRepository,
            TicketsRepository ticketsRepository,
            UserRepository userRepository,
            CommentMapper commentMapper
    ) {
        this.commentRepository = commentRepository;
        this.ticketsRepository = ticketsRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentResponseDto addComment(Long ticketId, CommentRequestDto requestDto) {
        try {
            Tickets ticket = ticketsRepository.findById(ticketId)
                    .orElseThrow(() -> new NotFoundException("Ticket not found"));
            User user = userRepository.findById(requestDto.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));

            Comment comment = commentMapper.toComment(requestDto);
            comment.setTicket(ticket);
            comment.setUser(user);
            comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

            Comment savedComment = commentRepository.save(comment);
            return commentMapper.toCommentResponseDto(savedComment);
        } catch (Exception e) {
            throw new ServerErrorException("Error creating comment: " + e.getCause().getLocalizedMessage());
        }
    }

    @Override
    public List<CommentResponseDto> getCommentsByTicket(Long ticketId) {
        try {
            return commentRepository.findByTicketId(ticketId)
                    .stream()
                    .map(commentMapper::toCommentResponseDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServerErrorException("Error getting comments by ticket: " + e.getCause().getLocalizedMessage());
        }
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {

            Comment comment = commentRepository.findByIdAndUserId(commentId, userId)
                    .orElseThrow(() -> new NotFoundException("Comment already deleted"));
            commentRepository.delete(comment);

    }
}

