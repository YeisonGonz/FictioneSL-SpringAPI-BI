package superset.bi.fictionesl.master.client;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import superset.bi.fictionesl.shared.GenericMapper;

@Mapper(componentModel = "spring")
public interface ClientMapper extends GenericMapper<Client, ClientDto> {
    @Override
    @Mapping(target = "id", ignore = true)
    Client toEntity(ClientDto dto);
}
