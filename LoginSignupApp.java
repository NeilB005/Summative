import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username = "";
    private String password = "";

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

public class LoginSignupApp {
    private List<User> registeredUsers;
    public static final String FILE_PATH = "user_data.ser";
    int invalid = 0;

    public LoginSignupApp() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveUsersToFile(registeredUsers);
        }));
        registeredUsers = loadUsersFromFile();
        JFrame frame = new JFrame("Login / Signup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);
        frame.setLayout(null);
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(255, 63, 128));
        leftPanel.setBounds(0, 0, 200, 300);
        JLabel title = new JLabel("Login/Registration Page");
        title.setBounds(50, 100, 50, 50);
        title.setForeground(Color.white);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(leftPanel);
        leftPanel.add(title);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);

        rightPanel.setBounds(frame.getWidth() / 2, 0, frame.getWidth() / 2, frame.getHeight());
        frame.add(rightPanel);
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.white);
        loginButton.setForeground(new Color(207, 135, 235));
        JButton signupButton = new JButton("Signup");

        signupButton.setBackground(new Color(255, 255, 255));
        signupButton.setForeground(new Color(207, 135, 235));
        int componentWidth = 150;
        int componentHeight = 30;

        int xPosition = (rightPanel.getWidth() - componentWidth) / 2;
        usernameField.setBounds(xPosition, 50, componentWidth, 40);
        passwordField.setBounds(xPosition, 100, componentWidth, 40);
        loginButton.setBounds(xPosition, 150, componentWidth, componentHeight);
        signupButton.setBounds(xPosition, 200, componentWidth, componentHeight);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Username");

        titledBorder.setTitleColor(new Color(135, 206, 235));
        usernameField.setBorder(titledBorder);
        titledBorder = BorderFactory.createTitledBorder("Password");
        passwordField.setBorder(titledBorder);
        titledBorder.setTitleColor(new Color(135, 206, 235));

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            User loggedInUser = isValidLogin(username, password);

            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(frame, "Login successful for user: " + username);

                // Create an instance of FoodTrackerApp here
                FoodTrackerApp foodTrackerApp = new FoodTrackerApp(username, registeredUsers);

                frame.dispose();
            } else {
                invalid++;
                if (invalid == 3) {
                    JOptionPane.showMessageDialog(frame, "You have failed too many times, try again later!");
                    System.exit(0);
                }
                JOptionPane.showMessageDialog(frame, "Invalid username or password! " + (3 - invalid) + " more attempts");
            }
        });

        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.");
            } else {
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "User registered successfully: " + username);
                } else {
                    JOptionPane.showMessageDialog(frame, "Username already exists!");
                }
            }
        });

        rightPanel.add(usernameField);
        rightPanel.add(passwordField);
        rightPanel.add(loginButton);
        rightPanel.add(signupButton);
        frame.setVisible(true);
    }

    private User isValidLogin(String username, String password) {
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private boolean registerUser(String username, String password) {
        for (User user : registeredUsers) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        User newUser = new User(username, password);
        registeredUsers.add(newUser);
        saveUsersToFile(registeredUsers);
        return true;
    }

    private void saveUsersToFile(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<User> loadUsersFromFile() {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                users = (List<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginSignupApp::new);
    }
}
