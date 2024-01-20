import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;


public class CalendarView extends JFrame {
    private Map<String, ArrayList<ExerciseEntry>> workoutHistory;
    private int year, month;
    private JButton[] dayButtons;


    public CalendarView(Map<String, ArrayList<ExerciseEntry>> workoutHistory) {
        this.workoutHistory = workoutHistory;
        Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        createUI();
    }


    private void createUI() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());


        // Navigation Panel
        JPanel navigationPanel = new JPanel();
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        JLabel monthYearLabel = new JLabel(getMonthName(month) + " " + year, JLabel.CENTER);
        navigationPanel.add(prevButton);
        navigationPanel.add(monthYearLabel);
        navigationPanel.add(nextButton);


        // Calendar Panel
        JPanel calendarPanel = new JPanel(new GridLayout(0, 7)); // 7 for days of the week
        dayButtons = new JButton[31]; // Assuming a maximum of 31 days in a month
        for (int i = 0; i < 31; i++) {
            final int day = i + 1;
            dayButtons[i] = new JButton(String.valueOf(day));
            dayButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String dateKey = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", day);
                    showWorkouts(dateKey);
                }
            });
            calendarPanel.add(dayButtons[i]);
        }


        // Add components to frame
        add(navigationPanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);


        // Navigation Button Actions
        prevButton.addActionListener(e -> changeMonth(-1));
        nextButton.addActionListener(e -> changeMonth(1));


        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }


    private void showWorkouts(String dateKey) {
        ArrayList<ExerciseEntry> exercises = workoutHistory.get(dateKey);
        if (exercises != null && !exercises.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for (ExerciseEntry entry : exercises) {
                message.append(entry).append("\n");
            }
            JOptionPane.showMessageDialog(this, message.toString(), "Workouts on " + dateKey, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No workouts found for " + dateKey, "No Workouts", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public void updateCalendar() {
        // Loop through all days in the current month
        for (int i = 0; i < dayButtons.length; i++) {
            final int day = i + 1;
            String dateKey = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", day);


            // Check if there are workouts for this day
            ArrayList<ExerciseEntry> exercises = workoutHistory.get(dateKey);
            if (exercises != null && !exercises.isEmpty()) {
                // If workouts exist, perhaps change the button color or add a marker
                dayButtons[i].setBackground(Color.GREEN); // Example: set background to green
            } else {
                // If no workouts, reset to default (or another color)
                dayButtons[i].setBackground(null); // Reset to default
            }
        }


        // Refresh the panel to reflect these changes
        getContentPane().revalidate();
        getContentPane().repaint();
    }


    private void changeMonth(int delta) {
        month += delta;
        if (month < 0) {
            month = 11;
            year--;
        } else if (month > 11) {
            month = 0;
            year++;
        }


        // Update Month and Year Label
        JPanel navigationPanel = (JPanel) getContentPane().getComponent(0);
        JLabel monthYearLabel = (JLabel) navigationPanel.getComponent(1);
        monthYearLabel.setText(getMonthName(month) + " " + year);


        // Optionally, refresh the calendar view here to reflect the correct number of days
    }




    // Main method for testing (if needed)
    public static void main(String[] args) {
        // Example workout history
        Map<String, ArrayList<ExerciseEntry>> workoutHistory = new HashMap<>();
        workoutHistory.put("2024-01-15", new ArrayList<>(Arrays.asList(new ExerciseEntry("Running", 30, "Good weather"))));
        // Create and show the calendar view
        new CalendarView(workoutHistory);
    }
}





