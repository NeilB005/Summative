
public class ExerciseEntry {
    private String exerciseType;
    private String duration;
    private String comments;


    public ExerciseEntry(String exerciseType, String duration, String comments) {
        this.exerciseType = exerciseType;
        this.duration = duration;
        this.comments = comments;
    }


    public ExerciseEntry(String running, int i, String goodWeather) {
    }


    public String getExerciseType() {
        return exerciseType;
    }


    public String getDuration() {
        return duration;
    }


    public String getComments() {
        return comments;
    }


    @Override
    public String toString() {
        return "Exercise: " + exerciseType + ", Duration: " + duration + " min, Comments: " + comments;
    }
}
