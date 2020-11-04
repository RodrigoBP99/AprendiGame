package br.com.rodrigo.aprendigame.Model;

import lombok.Data;

@Data
public class Answer {
    private Long id;
    private String text;
    private AnswerType answerType;
    private Question question;
}
