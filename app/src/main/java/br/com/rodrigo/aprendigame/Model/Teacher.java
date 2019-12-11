package br.com.rodrigo.aprendigame.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Teacher implements Serializable {

    private Long id;
    private String name;
    private List<CoursesUnit> courses;

}
