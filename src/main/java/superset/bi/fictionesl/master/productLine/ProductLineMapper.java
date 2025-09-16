package superset.bi.fictionesl.master.productLine;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import superset.bi.fictionesl.shared.GenericMapper;

@Mapper(componentModel = "spring")
public interface ProductLineMapper extends GenericMapper<ProductLine, ProductLineDto> {

    @Mapping(source = "product.id", target = "productId")
    @Override
    ProductLineDto toDto(ProductLine entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(source = "productId", target = "product.id")
    @Override
    ProductLine toEntity(ProductLineDto dto);
}
