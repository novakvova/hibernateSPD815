package models;

import emums.QuestionType;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tbl_question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "text", nullable = false, length = 500)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    List<QuestionItem> questionItems;

    public Question() {
        this.questionItems = new ArrayList<QuestionItem>();
    }

    public Question(String text, QuestionType questionType) {
        this.text = text;
        this.questionType = questionType;
        this.questionItems = new ArrayList<QuestionItem>();
    }

    public List<QuestionItem> getQuestionItems() {
        return questionItems;
    }

    public void setQuestionItems(List<QuestionItem> questionItems) {
        this.questionItems = questionItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (QuestionItem qi : questionItems) {
            str.append(qi.getText()).append(", ");
        }
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", questionType=" + questionType +
                ", questionItems=" + str.toString() +
                '}';
    }
}
