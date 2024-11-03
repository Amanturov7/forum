package kg.amanturov.forum.mapper;

import kg.amanturov.forum.dto.request.LikeRequestDto;
import kg.amanturov.forum.dto.response.LikeResponseDto;
import kg.amanturov.forum.model.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    LikeResponseDto toLikeResponseDto(Like like);

    Like toLike(LikeRequestDto requestDto);
}
