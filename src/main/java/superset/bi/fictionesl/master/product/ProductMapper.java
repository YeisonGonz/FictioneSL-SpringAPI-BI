package superset.bi.fictionesl.master.product;

import org.mapstruct.Mapper;
import superset.bi.fictionesl.shared.GenericMapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends GenericMapper<Product, ProductDto> {}
