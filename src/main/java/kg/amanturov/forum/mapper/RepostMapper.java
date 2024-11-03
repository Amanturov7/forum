package kg.amanturov.forum.mapper;

import kg.amanturov.forum.dto.request.RepostRequestDto;
import kg.amanturov.forum.dto.response.RepostResponseDto;
import kg.amanturov.forum.model.Repost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepostMapper {


    RepostResponseDto toRepostResponseDto(Repost repost);

    Repost toRepost(RepostRequestDto requestDto);
}
