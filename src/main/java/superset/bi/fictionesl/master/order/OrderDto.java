package superset.bi.fictionesl.master.order;

import superset.bi.fictionesl.master.productLine.ProductLineDto;

import java.util.Date;
import java.util.List;

public record OrderDto (
        Long clientId,
        Date date,
        List<ProductLineDto> products
) {}