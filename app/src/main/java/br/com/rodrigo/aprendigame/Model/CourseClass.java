package br.com.rodrigo.aprendigame.Model;

import java.util.List;

public class CourseClass {

    private Long id;
    private List<Student> students;
    private CoursesUnit coursesUnit;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public CoursesUnit getCoursesUnit() {
        return coursesUnit;
    }

    public void setCoursesUnit(CoursesUnit coursesUnit) {
        this.coursesUnit = coursesUnit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
