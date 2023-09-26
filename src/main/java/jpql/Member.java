package jpql;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString(exclude = "team")
public class Member {
    @Id @GeneratedValue
    private long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Enumerated(EnumType.STRING)
    private MemberType type;

//    @OneToMany(mappedBy = "member")
//    private List<Order> orders = new ArrayList<>();

//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }

}
