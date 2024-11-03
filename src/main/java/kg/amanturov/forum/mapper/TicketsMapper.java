package kg.amanturov.forum.mapper;



import kg.amanturov.forum.dto.request.TicketsRequestDto;
import kg.amanturov.forum.dto.response.TicketsResponseDto;
import kg.amanturov.forum.model.Tickets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketsMapper {

    @Mapping(source = "categoryTypeId", target = "categoryType.id")
    @Mapping(source = "ticketTypeId", target = "ticketType.id")

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "is_archived", target = "isArchived")
    Tickets toEntity(TicketsRequestDto requestDto);

    @Mapping(source = "categoryType.id", target = "categoryTypeId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "ticketType.id", target = "ticketTypeId")
    TicketsResponseDto toResponseDto(Tickets ticket);
}
