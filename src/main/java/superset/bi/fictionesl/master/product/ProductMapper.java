package superset.bi.fictionesl.master.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import superset.bi.fictionesl.shared.GenericMapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends GenericMapper<Product, ProductDto> {
    @Override
    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDto dto);
}
