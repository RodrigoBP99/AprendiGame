package br.com.rodrigo.aprendigame.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Question implements Serializable {
    private Long id;
    private String questionTittle;
    private List<Answer> answers;
    private Quizz quizz;
}
