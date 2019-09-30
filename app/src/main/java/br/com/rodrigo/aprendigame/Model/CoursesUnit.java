package br.com.rodrigo.aprendigame.Model;

import java.util.ArrayList;
import java.util.List;

public class CoursesUnit {

    private Long id;
    private String name;
    private List<Teacher> teacher;
    private List<CourseClass> courseClasses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachers() {
        return teacher;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teacher = teachers;
    }

    public List<CourseClass> getCourseClasses() {
        return courseClasses;
    }

    public void setCourseClasses(List<CourseClass> courseClasses) {
        this.courseClasses = courseClasses;
    }
}