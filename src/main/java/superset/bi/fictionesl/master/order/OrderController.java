package superset.bi.fictionesl.master.order;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import superset.bi.fictionesl.master.client.Client;
import superset.bi.fictionesl.master.client.ClientRepository;
import superset.bi.fictionesl.master.product.ProductRepository;
import superset.bi.fictionesl.master.productLine.ProductLine;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderController(OrderRepository orderRepository,
                           ClientRepository clientRepository,
                           ProductRepository productRepository,
                           OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderDto dto) {
        Order order = new Order();
        order.setDate(dto.date());

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        order.setClient(client);

        List<ProductLine> lines = dto.products().stream().map(plDto -> {
            ProductLine line = new ProductLine();
            line.setOrders(order);
            line.setAmount(plDto.amount());
            line.setProduct(
                    productRepository.findById(plDto.productId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"))
            );
            return line;
        }).toList();

        order.setProductLineList(lines);

        return orderRepository.save(order);
    }
}