import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class FoodTrackerApp extends JFrame {
    private JComboBox<String> foodItemComboBox;
    private JTextField quantityField;
    private JTextField nameField;
    private JLabel nutritionLabel;
    private List<User> registeredUsers;

    public FoodTrackerApp(String username, List<User> registeredUsers) {
        setTitle("Food Tracker App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Food", createFoodPanel());
        tabbedPane.addTab("Calendar", createCalendarPanel());

        getContentPane().add(tabbedPane);

        setVisible(true);

        if (username == null || username.isEmpty()) {
            setUsername();
        } else {
            nameField.setText(username);
        }

        this.registeredUsers = registeredUsers;
    }

    private void setUsername() {
        String username = JOptionPane.showInputDialog("Enter your name:");
        if (username != null && !username.isEmpty()) {
            nameField.setText(username);
        }
    }

    private JPanel createFoodPanel() {
        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new BorderLayout());

        JComboBox<String> mealTimeComboBox = new JComboBox<>(new String[]{"Breakfast", "Lunch", "Dinner"});
        mealTimeComboBox.setSelectedIndex(0);

        foodItemComboBox = new JComboBox<>();
        quantityField = new JTextField(10);
        nameField = new JTextField(20);
        nameField.setEditable(false);
        JButton logButton = new JButton("Log Entry");
        JButton seeNutritionButton = new JButton("See Nutrition Facts"); // Added button

        nutritionLabel = new JLabel("Nutritional Information:");

        JPanel chartPanel = new JPanel();

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Meal Time:"));
        formPanel.add(mealTimeComboBox);
        formPanel.add(new JLabel("Food Item:"));
        formPanel.add(foodItemComboBox);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        logButton.addActionListener(e -> {
            updateNutritionalDetails((String) foodItemComboBox.getSelectedItem());
            // TODO: Implement logic to handle logging entries
            // You can access values from foodItemComboBox, quantityField, mealTimeComboBox, and nameField
            // Update the nutritional information and chart accordingly
        });

        seeNutritionButton.addActionListener(e -> {
            updateNutritionalDetails((String) foodItemComboBox.getSelectedItem());
        });

        Dimension buttonSize = new Dimension(80, 25);
        logButton.setPreferredSize(buttonSize);
        seeNutritionButton.setPreferredSize(buttonSize);

        mealTimeComboBox.addActionListener(e -> {
            String selectedMealTime = (String) mealTimeComboBox.getSelectedItem();
            updateFoodItemsDropdown(selectedMealTime);
        });

        containerPanel.add(formPanel, BorderLayout.NORTH);
        containerPanel.add(logButton, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(seeNutritionButton);
        foodPanel.add(buttonPanel, BorderLayout.SOUTH);

        foodPanel.add(containerPanel, BorderLayout.NORTH);
        foodPanel.add(nutritionLabel, BorderLayout.CENTER);
        foodPanel.add(chartPanel, BorderLayout.EAST);

        updateFoodItemsDropdown("Breakfast");

        return foodPanel;
    }

    private void updateFoodItemsDropdown(String mealTime) {
        String[] foodItems;

        switch (mealTime) {
            case "Breakfast":
                foodItems = new String[]{"Cereal", "Oatmeal", "Toast", "Eggs"};
                break;
            case "Lunch":
                foodItems = new String[]{"Sandwich", "Salad", "Soup", "Pasta"};
                break;
            case "Dinner":
                foodItems = new String[]{"Chicken", "Fish", "Steak", "Vegetables"};
                break;
            default:
                foodItems = new String[]{};
        }

        DefaultComboBoxModel<String> foodItemModel = new DefaultComboBoxModel<>(foodItems);
        foodItemComboBox.setModel(foodItemModel);

        // Check if selected item is "Oatmeal" and update nutritional details
        if ("Oatmeal".equals(foodItemComboBox.getSelectedItem())) {
            updateNutritionalDetails("Oatmeal");
        }
    }

    private void updateNutritionalDetails(String foodItem) {
        // Placeholder method, replace with actual nutritional information retrieval logic
        String nutritionalDetails = getNutritionalDetails(foodItem);
        nutritionLabel.setText(nutritionalDetails);
    }

    private String getNutritionalDetails(String foodItem) {
        // Placeholder nutritional details for oatmeal
        if ("Oatmeal".equals(foodItem)) {
            return "Calories: 150\nProtein: 5g\nCarbohydrates: 30g\nFat: 3g";
        }
        // Add more cases for other food items as needed
        return "Nutritional details not available.";
    }

    private JPanel createCalendarPanel() {
        JPanel calendarPanel = new JPanel();
        calendarPanel.add(new JLabel("Calendar Panel (To be implemented)"));
        return calendarPanel;
    }

}
