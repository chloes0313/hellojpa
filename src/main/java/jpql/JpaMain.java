package jpql;

import javax.persistence.*;
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
            member.setUsername(null);
            member.setAge(9);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);

            em.persist(member);


            em.flush();
            em.clear();

            // CASE
//            String query =
//                    "select"+
//                    "   case when m.age <= 10 then '학생요금'" +
//                    "        when m.age >= 60 then '경로요금'" +
//                    "        else '일반요금'" +
//                    "   end " +
//                    "from Member m";
//            List<String> resultList = em.createQuery(query, String.class)
//                    .getResultList();
//

            // COALESCE
//            String query =
//                    "select coalesce(m.username, 'Unknown') from Member m";
//            List<String> resultList = em.createQuery(query, String.class)
//                    .getResultList();

            // NULLIF
            String query =
                    "select nullif(m.username, '관리자') from Member m";
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for(String s : resultList) {
                System.out.println("s : "+ s);
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