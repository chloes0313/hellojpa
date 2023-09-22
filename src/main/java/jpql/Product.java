package jpql;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Product {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private int stockAmonut;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;

}
