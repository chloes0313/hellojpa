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
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);
            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("Member-1");
            member1.setAge(33);
            member1.setType(MemberType.ADMIN);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("Member-2");
            member2.setAge(10);
            member2.setType(MemberType.USER);
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("Member-3");
            member3.setAge(60);
            member3.setType(MemberType.USER);
            member3.setTeam(teamB);
            em.persist(member3);

            Member member4 = new Member();
            member4.setUsername("Member-4");
            member4.setAge(60);
            member4.setType(MemberType.USER);
            em.persist(member4);

            //flush 자동 호출( -> DB 직접 반영)
            String query = "update Member m set m.age = 20 where m.age >= 60";
            int resultCount = em.createQuery(query)
                    .executeUpdate();
            // 영속성 컨텍스트 갱신 전 조회(age : 60)
            Member findMember = em.find(Member.class, member3.getId());
            System.out.println("-- findMember : " + findMember );
            // 영속성 컨텍스트 초기화 후 재 조회
            em.clear();
            findMember = em.find(Member.class, member3.getId());
            System.out.println("-- findMemberAfter : " + findMember );

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