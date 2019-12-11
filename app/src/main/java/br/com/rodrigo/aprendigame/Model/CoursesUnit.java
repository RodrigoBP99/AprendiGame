package br.com.rodrigo.aprendigame.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


@Data
public class CoursesUnit implements Serializable {

    private Long id;
    private String name;
    private List<Teacher> teacher;
    private List<CourseClass> courseClasses;

}