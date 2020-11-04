package br.com.rodrigo.aprendigame.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


@Data
public class CoursesUnit implements Serializable {

    private Long id;
    private String name;
    private String code;
    private List<Teacher> teachers;
    private List<CourseClass> courseClasses;
    private List<Student> students;

}