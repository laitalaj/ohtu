package ohtu;

public class Course {

    private String name, term;
    private int week;
    private int[] exercises;

    public int[] getExercises() {
        return exercises;
    }

    public void setExercises(int[] excercises) {
        this.exercises = excercises;
    }

    public int getMaxExercises(int week) {
        return getExercises()[week - 1];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return getName() + ", " + getTerm();
    }
}
