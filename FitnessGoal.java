


public class FitnessGoal {
    private String description;
    private int target;
    private int progress;


    public FitnessGoal(String description, int target) {
        this.description = description;
        this.target = target;
        this.progress = 0;
    }


    public void updateProgress(int value) {
        progress += value;
    }


    public boolean isGoalMet() {
        return progress >= target;
    }


    public String getDescription() {
        return description;
    }


    public int getTarget() {
        return target;
    }


    public int getProgress() {
        return progress;
    }


    @Override
    public String toString() {
        return "Goal: " + description + ", Target: " + target + ", Progress: " + progress;
    }
}
