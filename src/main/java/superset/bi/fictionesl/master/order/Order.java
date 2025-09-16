package superset.bi.fictionesl.master.order;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import superset.bi.fictionesl.master.client.Client;
import superset.bi.fictionesl.master.productLine.ProductLine;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //Indica que solo se serializa de este lado y no del hijo (ProductLine)
    private List<ProductLine> productLineList;

    public double getTotal() {
        if (productLineList == null) return 0.0;
        return productLineList.stream().mapToDouble(ProductLine::getSubtotal).sum();
    }
}
