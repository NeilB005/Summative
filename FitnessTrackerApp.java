import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FitnessTrackerApp extends JFrame {
    private Map<String, ArrayList<ExerciseEntry>> workoutHistory;
    private ArrayList<ExerciseEntry> currentExercises;


    private JComboBox<String> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> yearComboBox;
    private JTextField exerciseTypeField;
    private JTextField durationField;
    private JTextField commentsField;
    private JTextArea logArea;
    private JButton addButton;
    private JButton finishWorkoutButton;
    private CalendarView calendarView;


    public FitnessTrackerApp() {
        workoutHistory = new HashMap<>();
        currentExercises = new ArrayList<>();
        createUI();
    }


    private void createUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // Main panel with BoxLayout for vertical stacking
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


        // Date Selection Panel
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBorder(BorderFactory.createTitledBorder("Date Selection"));
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = Integer.toString(i);
        }
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        String[] years = {"2019", "2020", "2021", "2022", "2023"}; // Example years


        dayComboBox = new JComboBox<>(days);
        monthComboBox = new JComboBox<>(months);
        yearComboBox = new JComboBox<>(years);


        datePanel.add(new JLabel("Day:"));
        datePanel.add(dayComboBox);
        datePanel.add(new JLabel("Month:"));
        datePanel.add(monthComboBox);
        datePanel.add(new JLabel("Year:"));
        datePanel.add(yearComboBox);
        mainPanel.add(datePanel);


        // Exercise Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Exercise Details"));
        exerciseTypeField = new JTextField();
        durationField = new JTextField();
        commentsField = new JTextField();


        inputPanel.add(new JLabel("Exercise Type:"));
        inputPanel.add(exerciseTypeField);
        inputPanel.add(new JLabel("Duration (min):"));
        inputPanel.add(durationField);
        inputPanel.add(new JLabel("Comments:"));
        inputPanel.add(commentsField);
        mainPanel.add(inputPanel);


        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Exercise");
        finishWorkoutButton = new JButton("Finish Workout");
        buttonPanel.add(addButton);
        buttonPanel.add(finishWorkoutButton);
        mainPanel.add(buttonPanel);


        // Log Area
        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Workout Log"));
        mainPanel.add(scrollPane);


        // Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExercise();
            }
        });


        finishWorkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishWorkout();
            }
        });


        // Add main panel to frame
        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        calendarView = new CalendarView(workoutHistory);
    }


    private void addExercise() {
        String exerciseType = exerciseTypeField.getText().trim();
        String duration = durationField.getText().trim();
        String comments = commentsField.getText().trim();


        if (!exerciseType.isEmpty() && !duration.isEmpty()) {
            ExerciseEntry entry = new ExerciseEntry(exerciseType, duration, comments);
            currentExercises.add(entry);
            logArea.append("Added: " + entry + "\n");
            clearFields();
        }
    }


    private void finishWorkout() {
        String selectedDay = (String) dayComboBox.getSelectedItem();
        String selectedMonth = String.format("%02d", monthComboBox.getSelectedIndex() + 1);
        String selectedYear = (String) yearComboBox.getSelectedItem();
        String dateKey = selectedYear + "-" + selectedMonth + "-" + selectedDay;


        workoutHistory.putIfAbsent(dateKey, new ArrayList<>());
        workoutHistory.get(dateKey).addAll(currentExercises);
        calendarView.updateCalendar();


        logArea.append("Workout Finished for " + dateKey + ". Total Exercises: " + currentExercises.size() + "\n");
        currentExercises.clear();  // Clear the current exercises list
    }


    private void clearFields() {
        exerciseTypeField.setText("");
        durationField.setText("");
        commentsField.setText("");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FitnessTrackerApp();
            }
        });
    }
}
