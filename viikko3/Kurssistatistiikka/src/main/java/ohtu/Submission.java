package ohtu;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Submission {

    private int week, hours;
    private int[] exercises;
    private Course course;

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }

    public int[] getExercises() {
        return exercises;
    }

    public int getExerciseCount() {
        return getExercises().length;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        String ret = "\tviikko "+getWeek()+":";
        ret += " tehtyjä tehtäviä yhteensä: "+getExerciseCount()+" (maksimi " + getCourse().getMaxExercises(getWeek()) + "),";
        ret += " aikaa kului "+getHours()+" tuntia,";
        ret += " tehdyt tehtävät: " + Arrays.stream(getExercises()).mapToObj(Integer::toString).collect(Collectors.joining(", "));
        return ret;
    }
    
}