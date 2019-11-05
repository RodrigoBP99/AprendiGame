package br.com.rodrigo.aprendigame.Model;

import java.util.List;

import lombok.Data;

@Data
public class Teacher {

    private Long id;
    private String name;
    private List<CoursesUnit> courses;

}
