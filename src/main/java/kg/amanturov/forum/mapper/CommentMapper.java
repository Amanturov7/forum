package kg.amanturov.forum.mapper;

import kg.amanturov.forum.dto.request.CommentRequestDto;
import kg.amanturov.forum.dto.response.CommentResponseDto;
import kg.amanturov.forum.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
    CommentResponseDto toCommentResponseDto(Comment comment);
    Comment toComment(CommentRequestDto requestDto);
}
