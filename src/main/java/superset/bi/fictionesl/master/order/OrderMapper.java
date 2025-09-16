package superset.bi.fictionesl.master.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import superset.bi.fictionesl.master.product.ProductMapper;
import superset.bi.fictionesl.shared.GenericMapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderMapper extends GenericMapper<Order, OrderDto> {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true) // lo asignamos en el controller
    @Mapping(target = "productLineList", ignore = true) // lo asignamos en el controller
    Order toEntity(OrderDto dto);
}
