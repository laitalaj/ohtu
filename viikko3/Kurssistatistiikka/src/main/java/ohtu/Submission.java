package ohtu;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Submission {

    private int week, hours;
    private int[] exercises;

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

    @Override
    public String toString() {
        String ret = "\tviikko "+getWeek()+":";
        ret += " tehtyjä tehtäviä yhteensä: "+getExerciseCount()+",";
        ret += " aikaa kului "+getHours()+" tuntia,";
        ret += " tehdyt tehtävät: " + Arrays.stream(getExercises()).mapToObj(Integer::toString).collect(Collectors.joining(", "));
        return ret;
    }
    
}