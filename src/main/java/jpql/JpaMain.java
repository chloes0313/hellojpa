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
            Member member = new Member();
            member.setUsername("chloe");
            member.setAge(20);
            em.persist(member);

//            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
//            query.setParameter("username", "chloe");
//            Member singleResult = query.getSingleResult();

            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = ?1", Member.class);
            query.setParameter(1, "chloe");
            Member singleResult = query.getSingleResult();

            System.out.println("singleResult = "+ singleResult);

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
