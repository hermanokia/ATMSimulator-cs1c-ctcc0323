import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class ATMGUI1 implements ActionListener {

    private JFrame frame;
    private JPanel loginPanel, mainPanel, transactionPanel, settingsPanel;
    private JLabel titleLabel, accountNumberLabel, pinLabel, messageLabel, balanceLabel, newPinLabel;
    private JTextField accountNumberField, newPinField;
    private JPasswordField pinField;
    private JButton loginButton, withdrawButton, depositButton, settingsButton, changePinButton, logoutButton, cancelChangePinButton, transactionButton, clearButton, exitTransactionButton, backButton;
    private JButton[] numberButtons;
    private HashMap<String, String> accounts;
    private String currentAccount;
    private HashMap<String, Double> balances;
    private boolean loggedIn = false;
    private String transactionType;
    private StringBuilder amountBuilder;

    public ATMGUI1() {
        // Initialize accounts and balances
        accounts = new HashMap<>();
        accounts.put("123456789", "1234");
        accounts.put("987654321", "4321");
        accounts.put("111122223", "5678");

        balances = new HashMap<>();
        balances.put("123456789", 1500000.00);
        balances.put("987654321", 2500000.00);
        balances.put("111122223", 3500000.00);

        // Login Screen
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(100, 149, 237));

        titleLabel = new JLabel("ATM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);

        accountNumberLabel = new JLabel("Account Number: ", SwingConstants.RIGHT);
        accountNumberLabel.setFont(new Font("Verdana", Font.PLAIN, 14));
        accountNumberLabel.setForeground(Color.WHITE);

        pinLabel = new JLabel("PIN: ", SwingConstants.RIGHT);
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        pinLabel.setForeground(Color.WHITE);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(Color.RED);

        accountNumberField = new JTextField(15);
        pinField = new JPasswordField(15);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 14));
        loginButton.addActionListener(this);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        loginPanel.add(titleLabel, c);

        c.gridwidth = 1;
        c.gridy = 1;
        loginPanel.add(accountNumberLabel, c);

        c.gridx = 1;
        loginPanel.add(accountNumberField, c);

        c.gridx = 0;
        c.gridy = 2;
        loginPanel.add(pinLabel, c);

        c.gridx = 1;
        loginPanel.add(pinField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        loginPanel.add(loginButton, c);

        c.gridy = 4;
        loginPanel.add(messageLabel, c);

        // Main ATM Screen
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(100, 149, 237));

        JLabel welcomeLabel = new JLabel("Welcome to ATM!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
        welcomeLabel.setForeground(Color.WHITE);

        balanceLabel = new JLabel("<html>Account Balance: <br>PHP 0.00</html>", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        balanceLabel.setForeground(Color.WHITE);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Arial", Font.PLAIN, 14));
        withdrawButton.setPreferredSize(new Dimension(120, 30));
        withdrawButton.addActionListener(this);

        depositButton = new JButton("Deposit");
        depositButton.setFont(new Font("Arial", Font.PLAIN, 14));
        depositButton.setPreferredSize(new Dimension(120, 30));
        depositButton.addActionListener(this);

        settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        settingsButton.setPreferredSize(new Dimension(120, 30));
        settingsButton.addActionListener(this);

        c = new GridBagConstraints();
        c.insets = new Insets(15, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        mainPanel.add(welcomeLabel, c);

        c.gridy = 1;
        mainPanel.add(balanceLabel, c);

        c.gridwidth = 1;
        c.gridy = 2;
        mainPanel.add(withdrawButton, c);

        c.gridx = 1;
        mainPanel.add(depositButton, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        mainPanel.add(settingsButton, c);

        // Transaction Panel
        transactionPanel = new JPanel(new GridBagLayout());
        transactionPanel.setBackground(new Color(100, 149, 237));

        JLabel transactionLabel = new JLabel("ATM", SwingConstants.CENTER);
        transactionLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
        transactionLabel.setForeground(Color.WHITE);

        JLabel amountLabel = new JLabel("Amount: ", SwingConstants.RIGHT);
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        amountLabel.setForeground(Color.WHITE);

        amountBuilder = new StringBuilder();
        JTextField amountField = new JTextField(15);
        amountField.setFont(new Font("Arial", Font.PLAIN, 16));
        amountField.setEditable(false);

        JPanel numberPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        numberButtons = new JButton[10];
        for (int i = 1; i <= 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            numberButtons[i].addActionListener(e -> {
                amountBuilder.append(((JButton) e.getSource()).getText());
                amountField.setText(amountBuilder.toString());
            });
            numberPanel.add(numberButtons[i]);
        }
        numberPanel.add(new JLabel("")); // This adds an empty label as a placeholder

// Add button 0
numberButtons[0] = new JButton("0");
numberButtons[0].setFont(new Font("Arial", Font.PLAIN, 16));
numberButtons[0].addActionListener(e -> {
    amountBuilder.append(((JButton) e.getSource()).getText());
    amountField.setText(amountBuilder.toString());
});
numberPanel.add(numberButtons[0]);


        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.addActionListener(e -> {
            amountBuilder.setLength(0);
            amountField.setText("");
        });
        numberPanel.add(clearButton);

        transactionButton = new JButton("Withdraw");
        transactionButton.setFont(new Font("Arial", Font.PLAIN, 14));
        transactionButton.setPreferredSize(new Dimension(120, 20));
        transactionButton.addActionListener(this);

        exitTransactionButton = new JButton("Back");
        exitTransactionButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitTransactionButton.setPreferredSize(new Dimension(120, 20));
        exitTransactionButton.addActionListener(e -> {
            frame.setContentPane(mainPanel);
            frame.revalidate();
        });

        JPanel transactionButtonPanel = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        transactionButtonPanel.add(transactionButton, c);
        c.gridx = 1;
        transactionButtonPanel.add(exitTransactionButton, c);

        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        transactionPanel.add(transactionLabel, c);

        c.gridwidth = 1;
        c.gridy = 1;
        transactionPanel.add(amountLabel, c);

        c.gridx = 1;
        transactionPanel.add(amountField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        transactionPanel.add(numberPanel, c);

        c.gridy = 3;
        transactionPanel.add(transactionButtonPanel, c);

        // Settings Screen
        settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setBackground(new Color(100, 149, 237));

        JLabel settingsLabel = new JLabel("Settings", SwingConstants.CENTER);
        settingsLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
        settingsLabel.setForeground(Color.WHITE);

        newPinLabel = new JLabel("New PIN: ", SwingConstants.RIGHT);
        newPinLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        newPinLabel.setForeground(Color.WHITE);
        newPinLabel.setVisible(false);

        newPinField = new JTextField(15);
        newPinField.setVisible(false);

        changePinButton = new JButton("Change PIN");
        changePinButton.setFont(new Font("Arial", Font.PLAIN, 14));
        changePinButton.addActionListener(this);

        cancelChangePinButton = new JButton("Cancel");
        cancelChangePinButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelChangePinButton.setVisible(false);
        cancelChangePinButton.addActionListener(e -> {
            newPinLabel.setVisible(false);
            newPinField.setVisible(false);
            cancelChangePinButton.setVisible(false);
            changePinButton.setText("Change PIN");
            // Show logoutButton again after canceling PIN change
            logoutButton.setVisible(true);
            // Show the back button again
            backButton.setVisible(true);
        });

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            frame.setContentPane(mainPanel);
            frame.revalidate();
        });

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.addActionListener(this);

        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        settingsPanel.add(settingsLabel, c);

        c.gridy = 1;
        c.gridwidth = 1;
        settingsPanel.add(newPinLabel, c);

        c.gridx = 1;
        settingsPanel.add(newPinField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        settingsPanel.add(changePinButton, c);

        c.gridy = 3;
        settingsPanel.add(cancelChangePinButton, c);

        c.gridy = 4;
        settingsPanel.add(backButton, c);

        c.gridy = 5;
        settingsPanel.add(logoutButton, c);
        logoutButton.setVisible(true);

        // Frame settings
        frame = new JFrame("ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(loginPanel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String enteredAccountNumber = accountNumberField.getText();
            String enteredPin = new String(pinField.getPassword());
            if (accounts.containsKey(enteredAccountNumber) && accounts.get(enteredAccountNumber).equals(enteredPin)) {
                loggedIn = true;
                currentAccount = enteredAccountNumber;
                messageLabel.setText("Login successful!");
                frame.setContentPane(mainPanel);
                frame.revalidate();
                balanceLabel.setText("<html>Account Balance<br>PHP " + String.format("%.2f", balances.get(currentAccount)) + "</html>");
            } else {
                messageLabel.setText("Invalid account number or PIN!");
            }
        } else if (e.getSource() == withdrawButton) {
            transactionType = "Withdraw";
            transactionButton.setText("Withdraw");
            amountBuilder.setLength(0);
            frame.setContentPane(transactionPanel);
            frame.revalidate();
        } else if (e.getSource() == depositButton) {
            transactionType = "Deposit";
            transactionButton.setText("Deposit");
            amountBuilder.setLength(0);
            frame.setContentPane(transactionPanel);
            frame.revalidate();
        } else if (e.getSource() == transactionButton) {
            try {
                double amount = Double.parseDouble(amountBuilder.toString());
                if (transactionType.equals("Withdraw")) {
                    if (amount > 0 && balances.get(currentAccount) >= amount) {
                        balances.put(currentAccount, balances.get(currentAccount) - amount);
                        balanceLabel.setText("<html>Account Balance<br>PHP " + String.format("%.2f", balances.get(currentAccount)) + "</html>");
                        JOptionPane.showMessageDialog(frame, "Withdrawal successful! PHP " + amount + " has been withdrawn.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Insufficient balance or invalid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (transactionType.equals("Deposit")) {
                    if (amount > 0) {
                        balances.put(currentAccount, balances.get(currentAccount) + amount);
                        balanceLabel.setText("<html>Account Balance<br>PHP " + String.format("%.2f", balances.get(currentAccount)) + "</html>");
                        JOptionPane.showMessageDialog(frame, "Deposit successful! PHP " + amount + " has been deposited.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                frame.setContentPane(mainPanel);
                frame.revalidate();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == settingsButton) {
            newPinLabel.setVisible(false);
            newPinField.setVisible(false);
            cancelChangePinButton.setVisible(false);
            changePinButton.setText("Change PIN");
            frame.setContentPane(settingsPanel);
            frame.revalidate();
        } else if (e.getSource() == changePinButton) {
            if (newPinLabel.isVisible()) { // Confirm button pressed
                String newPin = newPinField.getText();
                if (newPin.length() == 4 && newPin.matches("\\d+")) {
                    accounts.put(currentAccount, newPin);
                    JOptionPane.showMessageDialog(frame, "PIN changed successfully!");
                    newPinLabel.setVisible(false);
                    newPinField.setVisible(false);
                    cancelChangePinButton.setVisible(false);
                    changePinButton.setText("Change PIN");
                    // Show logoutButton again after PIN change completes
                    logoutButton.setVisible(true);
                    // Show the back button again
                    backButton.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid 4-digit PIN.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else { // Change PIN button pressed
                newPinLabel.setVisible(true);
                newPinField.setVisible(true);
                cancelChangePinButton.setVisible(true);
                changePinButton.setText("Confirm");
                // Hide logoutButton during PIN change process
                logoutButton.setVisible(false);
                // Hide the back button while changing PIN
                backButton.setVisible(false);
            }
        } else if (e.getSource() == logoutButton) {
            int response = JOptionPane.showConfirmDialog(frame, "Do you really want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                loggedIn = false;
                frame.setContentPane(loginPanel);
                frame.revalidate();
                accountNumberField.setText("");
                pinField.setText("");
                messageLabel.setText("");
                currentAccount = null;
            }
        } else if (e.getSource() == backButton) {
            frame.setContentPane(mainPanel);
            frame.revalidate();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMGUI1());
    }
}