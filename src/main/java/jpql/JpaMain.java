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

            em.flush();
            em.clear();

            //일반 조인
            // -> 회원1, 팀A (SQL > 영속성 컨텍스트 등록)
            // -> 회원2, 팀A (1차 캐시)
            // -> 회원3, 팀B (SQL > 영속성 컨텍스트 등록)
            // ... 회원 100 -> N+1 문제 발생
//            String query = "select m from Member m";

            //페치 조인
//            String query = "select m from Member m join fetch m.team";
//            List<Member> resultList = em.createQuery(query, Member.class)
//                    .getResultList();
//
//            for(Member s : resultList) {
//                System.out.println("member : " + s.getUsername() + ", " + s.getTeam().getName());
//            }
            
            //컬렉션 페치 조인
//            String query = "select disticnt t from Team t join fetch t.members where t.name='teamA'";
            String query = "select t from Team t ";
            List<Team> resultList = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();
            System.out.println("-- result size : " + resultList.size());
            for(Team team : resultList) {
                System.out.println("team : " + team.getName() + ", members : " + team.getMembers().size());
                for(Member member : team.getMembers()) {
                    System.out.println(" -> member = " + member);
                }
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