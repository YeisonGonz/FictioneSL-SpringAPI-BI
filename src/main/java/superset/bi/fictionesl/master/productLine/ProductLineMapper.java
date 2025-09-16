package superset.bi.fictionesl.master.productLine;

import org.mapstruct.Mapper;
import superset.bi.fictionesl.shared.GenericMapper;

@Mapper(componentModel = "spring")
public interface ProductLineMapper extends GenericMapper<ProductLine, ProductLineDto> { }
