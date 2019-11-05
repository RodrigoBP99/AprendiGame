package br.com.rodrigo.aprendigame.Model;

import java.util.List;

import lombok.Data;

@Data
public class CourseClass {

    private Long id;
    private List<Student> students;
    private CoursesUnit coursesUnit;
    private String status;
}
