package superset.bi.fictionesl.master.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderController(OrderRepository orderRepository,
                           OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders")
    @GetMapping
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Operation(summary = "Get order by ID", description = "Retrieve an order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(
            @Parameter(description = "ID of the order") @PathVariable Long id) {
        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new order", description = "Create a new order with products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid client or product ID")
    })
    @PostMapping
    public Order createOrder(@RequestBody OrderDto dto) {
        return orderService.saveOrUpdate(dto, null);
    }

    @Operation(summary = "Update an existing order", description = "Update an order by ID with new product lines")
    @PutMapping("/{id}")
    public ResponseEntity<Order> update(
            @Parameter(description = "ID of the order to update") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated order data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = OrderDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "clientId": 1,
                                      "date": "2025-09-16T15:00:00",
                                      "products": [
                                        {"productId": 1, "amount": 5},
                                        {"productId": 3, "amount": 1}
                                      ]
                                    }
                                    """)
                    )
            ) @RequestBody OrderDto dto) {
        if (!orderRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Order updated = orderService.saveOrUpdate(dto, id);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete an order", description = "Delete an order by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the order to delete") @PathVariable Long id) {
        if (!orderRepository.existsById(id)) return ResponseEntity.notFound().build();
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}