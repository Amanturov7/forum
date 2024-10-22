package kg.amanturov.forum.mapper;



import kg.amanturov.forum.dto.request.TicketsRequestDto;
import kg.amanturov.forum.dto.response.TicketsResponseDto;
import kg.amanturov.forum.model.Tickets;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketsMapper {

    Tickets toEntity(TicketsRequestDto requestDto);

    TicketsResponseDto toResponseDto(Tickets ticket);
}
