package superset.bi.fictionesl.master.order;

import org.springframework.stereotype.Service;
import superset.bi.fictionesl.master.client.Client;
import superset.bi.fictionesl.master.client.ClientRepository;
import superset.bi.fictionesl.master.product.ProductRepository;
import superset.bi.fictionesl.master.productLine.ProductLine;
import superset.bi.fictionesl.master.productLine.ProductLineDto;

import java.util.ArrayList;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        ClientRepository clientRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public Order saveOrUpdate(OrderDto dto, Long id) {
        Order order = id == null ? new Order() : orderRepository.findById(id).orElseThrow();

        order.setDate(dto.date());

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        order.setClient(client);

        if (order.getProductLineList() == null) {
            order.setProductLineList(new ArrayList<>());
        }

        order.getProductLineList().clear();

        for (ProductLineDto plDto : dto.products()) {
            ProductLine line = new ProductLine();
            line.setOrders(order);
            line.setAmount(plDto.amount());
            line.setProduct(
                    productRepository.findById(plDto.productId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"))
            );
            order.getProductLineList().add(line);
        }

        return orderRepository.save(order);
    }
}