package ohtu;

public class Course {

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
}
