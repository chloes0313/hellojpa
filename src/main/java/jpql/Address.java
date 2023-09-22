package jpql;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String city;

    private String street;

    @Column(length = 5)
    private String zipcode;
}
