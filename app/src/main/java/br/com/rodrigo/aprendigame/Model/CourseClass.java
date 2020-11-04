package br.com.rodrigo.aprendigame.Model;

import java.util.List;

import lombok.Data;

@Data
public class CourseClass {

    private Long id;
    private String code;
    private String name;
    private List<Student> students;
    private CoursesUnit courseUnit;
    private Teacher teacher;
    private List<Quizz> quizzes;
}
