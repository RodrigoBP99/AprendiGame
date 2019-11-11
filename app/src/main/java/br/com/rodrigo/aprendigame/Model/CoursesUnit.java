package br.com.rodrigo.aprendigame.Model;

import java.util.List;

import lombok.Data;


@Data
public class CoursesUnit {

    private Long id;
    private String name;
    private List<Teacher> teacher;
    private List<CourseClass> courseClasses;

}