package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hellojpa");
        EntityManager em = emf.createEntityManager ();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("Chloeseong");
            member.setAge(9);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("Eunyoung");
            member2.setAge(9);
            member2.setType(MemberType.ADMIN);
            member2.setTeam(team);

            em.persist(member2);

            em.flush();
            em.clear();

            String query =
                    "select m.username FROM Team t join t.members m";
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for(String s : resultList) {
                System.out.println("member : " + s);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}