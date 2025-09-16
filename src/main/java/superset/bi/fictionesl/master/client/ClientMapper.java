package superset.bi.fictionesl.master.client;

import org.mapstruct.Mapper;
import superset.bi.fictionesl.shared.GenericMapper;

@Mapper(componentModel = "spring")
public interface ClientMapper extends GenericMapper<Client, ClientDto> { }
