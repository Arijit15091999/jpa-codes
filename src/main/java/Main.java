import DAO.DAO;
import entity.Address;
import entity.Students;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
//    public static void main(String[] args) {
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        try{
//            transaction.begin();
//            Students Arijit = new Students("Anamika", "Anamika@email.com");
//            entityManager.persist(Arijit);

//            transaction.commit();
//        }finally {
//            if(transaction.isActive()) {
//                transaction.rollback();
//            }
//
//            entityManager.close();
//            entityManagerFactory.close();
//        }


//        try(SessionFactory sessionFactory = HibernateUtils.getSessionFactory()) {
//            Students students = new Students("Mallika", "Mallika@email.com");
//
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//
//            try {
//                session.persist(students);
//
//                transaction.commit();
//            }finally {
//                if(transaction.isActive()) {
//                    transaction.rollback();
//                }
//                session.close();
//            }
//        }
//
//    }

    public static void main(String[] args) {
         try(DAO dao = new DAO()){
//             Students student1 = new Students("Mallika", "Mallika@email.com");
//             Address address1 = new Address(71223L, "Sheoraphuly", "WB");
//             student1.setAddress(address1);
//             Students student2 = new Students("Anamika", "Anamika@email.com");
//             Address address2 = new Address(71223L, "Sheoraphuly", "WB");
//             student2.setAddress(address2);
//
//             dao.create(student1);
//             dao.create(student2);

//             Map<String, Object> map = new HashMap<>();
//
//             map.put("email", "arijitkarmakar056@gmail.com");
//             map.put("name", "Arijit Karmakar");
//
//             Optional<Object> res = dao.update(Students.class, 1, map);
//
//             res.ifPresent(System.out::println);

//             dao.delete(Students.class, 1);

         }catch(Exception e) {
             System.err.println(e.getMessage());
         }

    }
}
