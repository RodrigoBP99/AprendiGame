package br.com.rodrigo.aprendigame.Model;

import java.util.List;

import lombok.Data;

@Data
public class CourseClass {

    private Long id;
    private String name;
    private List<Student> students;
    private CoursesUnit coursesUnit;
    private Teacher teacher;
    private List<Quizz> quizzes;
}
