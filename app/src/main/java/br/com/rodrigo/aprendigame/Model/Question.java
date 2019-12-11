package br.com.rodrigo.aprendigame.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Question implements Serializable {
    private String questionTitle;
//    private List<Answer> answers;
}
