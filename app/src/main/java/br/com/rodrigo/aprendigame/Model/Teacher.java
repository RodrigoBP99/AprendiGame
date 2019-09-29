package br.com.rodrigo.aprendigame.Model;

import java.util.List;

public class Teacher {

    private Long id;
    private String name;
    private List<CoursesUnit> courses;

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

    public List<CoursesUnit> getCourses() {
        return courses;
    }

    public void setCourses(List<CoursesUnit> courses) {
        this.courses = courses;
    }
}
