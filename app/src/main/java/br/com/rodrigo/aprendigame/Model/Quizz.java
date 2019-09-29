package br.com.rodrigo.aprendigame.Model;

public class Quizz {

    private Long id;
    private String title;
    private Teacher teacher;
    private CoursesUnit courseUnit;
    private String amountOfQuestions;

    public CoursesUnit getCourseUnit() {
        return courseUnit;
    }

    public void setCourseUnit(CoursesUnit courseUnit) {
        this.courseUnit = courseUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public void setAmountOfQuestions(String amountOfQuestions) {
        this.amountOfQuestions = amountOfQuestions;
    }
}
