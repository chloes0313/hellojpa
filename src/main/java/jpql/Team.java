package jpql;

import lombok.Data;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Team {
    @Id @GeneratedValue
    private long id;

    private String name;

    @BatchSize(size = 10)
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
