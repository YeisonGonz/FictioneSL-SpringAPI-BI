package superset.bi.fictionesl.master.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import superset.bi.fictionesl.master.product.ProductMapper;
import superset.bi.fictionesl.shared.GenericMapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderMapper extends GenericMapper<Order, OrderDto> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "productLineList", target = "products")
    @Override
    OrderDto toDto(Order entity);

    @Mapping(source = "clientId", target = "client.id")
    @Mapping(source = "products", target = "productLineList")
    @Override
    Order toEntity(OrderDto dto);
}
