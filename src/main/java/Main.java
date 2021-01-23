import emums.QuestionType;
import models.Question;
import models.QuestionItem;
import models.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.RoleCRUD;
import utils.HibernateSessionFactoryUtil;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //RoleCRUD.Runner();
        try {
//            AddQuestion("Вибух Чорнобиля?", QuestionType.RUDIO_BUTTON);
//            AddQuestionItem(1,"1824", false);
//            AddQuestionItem(1,"1814", true);
//            AddQuestionItem(1,"1812", false);
            ReadQuestionById(1);



        } catch (Exception ex) {
            System.out.println("Propblem!"+ ex.getMessage());
        }

    }

    private static void AddQuestion(String text, QuestionType type) throws SQLException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Question question = new Question(text, type);
        session.save(question);
        tx.commit();
        session.close();
    }

    private static void AddQuestionItem(int questionId, String text,
                                        boolean isTrue ) throws SQLException {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Question question = new Question();
        question.setId(questionId);
        QuestionItem qi = new QuestionItem(text, question, isTrue);
        session.save(qi);
        tx.commit();
        session.close();
    }

    private static void ReadQuestionById(int id) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Question question = session.get(Question.class, id);
        if(question!=null) {
            System.out.println(question.getText());
            int i=1;
            List<QuestionItem> listQI = question.getQuestionItems();

            for (QuestionItem qi: listQI) {
                System.out.println(i+". "+qi.getText());
                i++;
            }
            System.out.print("->_");
            int answer = scanner.nextInt();
            if(listQI.get(answer-1).isTrue()) {
                System.out.println("Правильно");
            }
            else
                System.out.println("Не вірно");
        }


        //System.out.println(question);
        tx.commit();
        session.close();
    }
}
