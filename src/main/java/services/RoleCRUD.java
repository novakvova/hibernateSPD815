package services;

import emums.QuestionType;
import models.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateSessionFactoryUtil;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RoleCRUD {

    public static void Runner() {

        try
        {
            Scanner scanner = new Scanner(System.in);
            int action =0;
            do {
                System.out.println("0. Вихід");
                System.out.println("1. Додати");
                System.out.println("2. Показати");
                System.out.println("3. Оновити");
                System.out.println("4. Видалити");
                try {
                    action = scanner.nextInt();
                }catch (InputMismatchException imex) { action = -1; scanner.nextLine(); }
                MenuRunAction(action, scanner);

            }while(action!=0);
        }
        catch (Exception ex) {
            System.out.println("Propblem "+ex.getMessage());
        }


    }
    private static void MenuRunAction(int action, Scanner scanner) {
        try {
            switch (action) {
                case 1: {
                    System.out.println("Введіть назву ролі:");
                    String name = scanner.next();
                    AddRole(name);
                    break;
                }
                case 2: {
                    System.out.println("------Список ролей-----");
                    List<Role> roles = GetListRoles();
                    for (Role role : roles) {
                        System.out.println(role);
                    }
                    break;
                }
                case 3: {
                    System.out.println("Введіть id ролі:");
                    int id = scanner.nextInt();
                    System.out.println("Введіть назву ролі:");
                    String name = scanner.next();
                    UpdateRole(id, name);
                    break;
                }
                case 4: {
                    System.out.println("Введіть id ролі:");
                    int id = scanner.nextInt();
                    DeleteRole(id);
                    break;
                }
            }
        }
        catch(SQLException sqlex) {
            System.out.println("Проблема при роботі з БД! "+ sqlex.getMessage());
        }
        catch(InputMismatchException imex) {
            System.out.println("Wrong input! "+ imex.getMessage());
            scanner.nextLine();
        }
        catch (Exception ex) {
            System.out.println("Propblem "+ ex.getMessage());

        }
    }

    private static void AddRole(String name) throws SQLException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Role r = new Role(name);
        session.save(r);
        tx.commit();
        session.close();
    }

    private static List<Role> GetListRoles() throws SQLException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        List<Role> roles = session.createQuery("FROM Role").list();
        tx.commit();
        session.close();
        return roles;
    }

    private static void UpdateRole(int id, String name) throws SQLException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Role role = session.get(Role.class, id);
        if(role!=null) {
            role.setName(name);
            session.update(role);
        }
        tx.commit();
        session.close();
    }

    private static void DeleteRole(int id) throws SQLException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Role role = session.get(Role.class, id);
        if(role!=null) {
            session.delete(role);
        }
        tx.commit();
        session.close();
    }

}
