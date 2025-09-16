package superset.bi.fictionesl.master.productLine;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import superset.bi.fictionesl.master.order.Order;
import superset.bi.fictionesl.master.product.Product;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class ProductLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonBackReference // Rompe bucles infinitos en relaciones bidireccionales
    private Order orders;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Product product;

    private int amount;

    public double getSubtotal() {
        if (product == null) return 0.0;
        return amount * product.getPrice();
    }
}
