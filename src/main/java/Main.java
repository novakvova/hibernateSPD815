import models.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

public class Main {
    public static void main(String []args) {
        try
        {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            Role r = new Role("admin");
            session.save(r);
            tx.commit();
            session.close();
        }
        catch (Exception ex) {
            System.out.println("Propblem "+ex.getMessage());
        }


    }
}
