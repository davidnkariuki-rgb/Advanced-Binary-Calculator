package ui;

import logic.BinaryCalculator;
import operations.BinaryOperation;
import operations.Addition;
import operations.Subtraction;
import operations.Multiplication;
import operations.Division;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class CalculatorUI extends JFrame {

    private JTextField txtFirst;
    private JTextField txtSecond;
    private JTextField txtResult;
    private JLabel lblOperation;
    private JLabel lblMode;

    private BinaryCalculator calculator;
    private JTextField activeField;
    private Map<String, JButton> digitButtons = new HashMap<>();

    private String storedOperand = "";
    private BinaryOperation pendingOperation = null;
    private String operationSymbol = "";
    private String currentMode = "BIN";

    public CalculatorUI() {
        calculator = new BinaryCalculator();

        setTitle("Advanced Binary Calculator");
        setSize(560, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(15, 15, 25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(mainPanel);

        JPanel modePanel = new JPanel(new BorderLayout());
        modePanel.setBackground(new Color(15, 15, 25));

        lblMode = new JLabel("MODE: BIN");
        lblMode.setForeground(new Color(255, 180, 50));
        lblMode.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblMode.setOpaque(true);
        lblMode.setBackground(new Color(30, 35, 55));
        lblMode.setBorder(BorderFactory.createLineBorder(new Color(255, 180, 50), 1));
        lblMode.setHorizontalAlignment(JLabel.CENTER);
        lblMode.setPreferredSize(new Dimension(80, 25));
        modePanel.add(lblMode, BorderLayout.WEST);
        mainPanel.add(modePanel, BorderLayout.WEST);

        JLabel lblHeader = new JLabel("Programmer Calculator");
        lblHeader.setForeground(new Color(0, 220, 150));
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 26));
        mainPanel.add(lblHeader, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(new Color(15, 15, 25));

        inputPanel.add(createLabel("First Number"));
        txtFirst = createTextField();
        txtFirst.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                activeField = txtFirst;
            }
        });

        inputPanel.add(txtFirst);

        inputPanel.add(createLabel("Operation"));
        lblOperation = new JLabel("");
        lblOperation.setForeground(new Color(0, 220, 180));
        lblOperation.setFont(new Font("Segoe UI", Font.BOLD, 20));
        inputPanel.add(lblOperation);

        inputPanel.add(createLabel("Second Number"));
        txtSecond = createTextField();
        txtSecond.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                activeField = txtSecond;
            }
        });
        inputPanel.add(txtSecond);

        activeField = txtFirst; 
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(new Color(15, 15, 25));
        resultPanel.setPreferredSize(new Dimension(0, 50));

        txtResult = new JTextField("0");
        txtResult.setEditable(false);
        txtResult.setHorizontalAlignment(JTextField.RIGHT);
        txtResult.setFont(new Font("Consolas", Font.BOLD, 20));
        txtResult.setBackground(new Color(20, 25, 40));
        txtResult.setForeground(new Color(0, 220, 150));
        txtResult.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        resultPanel.add(txtResult, BorderLayout.CENTER);
        mainPanel.add(resultPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(new Color(15, 15, 25));
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));
        buttonPanel.setBackground(new Color(15, 15, 25));
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel numberPanel = new JPanel(new BorderLayout(10, 10));
        numberPanel.setBackground(new Color(15, 15, 25));

        JPanel hexGridPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        hexGridPanel.setBackground(new Color(15, 15, 25));

        String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        for (String digit : hexDigits) {
            JButton btn = createButton(digit, new Color(50, 70, 100));
            btn.addActionListener(e -> appendDigit(digit));
            hexGridPanel.add(btn);
            digitButtons.put(digit, btn);
        }

        numberPanel.add(hexGridPanel, BorderLayout.CENTER);

        JPanel clearPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        clearPanel.setBackground(new Color(15, 15, 25));

        JButton btnDelete = createButton("DEL", new Color(220, 50, 80));
        JButton btnClear = createButton("CLR", new Color(255, 150, 20));

        btnDelete.addActionListener(e -> deleteLastChar());
        btnClear.addActionListener(e -> clearAll());

        clearPanel.add(btnDelete);
        clearPanel.add(btnClear);

        numberPanel.add(clearPanel, BorderLayout.SOUTH);

        buttonPanel.add(numberPanel, BorderLayout.NORTH);

        JPanel operationPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        operationPanel.setBackground(new Color(15, 15, 25));

        JButton btnAdd = createButton("+", new Color(30, 70, 130));
        JButton btnSubtract = createButton("-", new Color(30, 70, 130));
        JButton btnMultiply = createButton("×", new Color(30, 70, 130));
        JButton btnDivide = createButton("÷", new Color(30, 70, 130));
        JButton btnEquals = createButton("=", new Color(30, 70, 130));

        operationPanel.add(btnAdd);
        operationPanel.add(btnSubtract);
        operationPanel.add(btnMultiply);
        operationPanel.add(btnDivide);
        operationPanel.add(btnEquals);

        buttonPanel.add(operationPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> handleOperation(new Addition()));
        btnSubtract.addActionListener(e -> handleOperation(new Subtraction()));
        btnMultiply.addActionListener(e -> handleOperation(new Multiplication()));
        btnDivide.addActionListener(e -> handleOperation(new Division()));
        btnEquals.addActionListener(e -> calculateResult());

        JPanel conversionPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        conversionPanel.setBackground(new Color(15, 15, 25));

        JButton btnBin = createButton("BIN", new Color(100, 130, 150));
        JButton btnHex = createButton("HEX", new Color(140, 100, 160));
        JButton btnDec = createButton("DEC", new Color(100, 150, 130));
        JButton btnOct = createButton("OCT", new Color(160, 130, 80));

        btnBin.addActionListener(e -> {
            try {
                convertAllFieldsToMode("BIN");
                currentMode = "BIN";
                calculator.setMode("BIN");
                updateModeDisplay();
                updateDigitButtons();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnHex.addActionListener(e -> {
            try {
                convertAllFieldsToMode("HEX");
                currentMode = "HEX";
                calculator.setMode("HEX");
                updateModeDisplay();
                updateDigitButtons();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDec.addActionListener(e -> {
            try {
                convertAllFieldsToMode("DEC");
                currentMode = "DEC";
                calculator.setMode("DEC");
                updateModeDisplay();
                updateDigitButtons();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnOct.addActionListener(e -> {
            try {
                convertAllFieldsToMode("OCT");
                currentMode = "OCT";
                calculator.setMode("OCT");
                updateModeDisplay();
                updateDigitButtons();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        conversionPanel.add(btnBin);
        conversionPanel.add(btnHex);
        conversionPanel.add(btnDec);
        conversionPanel.add(btnOct);

        bottomPanel.add(conversionPanel, BorderLayout.NORTH);
        updateDigitButtons();
    }

    private void appendDigit(String digit) {
        if (activeField != null) {
            digit = digit.toUpperCase();
            if (isAllowedDigit(digit)) {
                activeField.setText(activeField.getText() + digit);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    private void deleteLastChar() {
        if (activeField != null) {
            if (activeField == txtSecond && txtSecond.getText().isEmpty() && pendingOperation != null) {
                pendingOperation = null;
                operationSymbol = "";
                lblOperation.setText("");
                activeField = txtFirst;
                return;
            }

            String text = activeField.getText();
            if (text.length() > 0) {
                activeField.setText(text.substring(0, text.length() - 1));
            }
        }
    }

    private void clearAll() {
        txtFirst.setText("");
        txtSecond.setText("");
        txtResult.setText("0");
        lblOperation.setText("");
        storedOperand = "";
        operationSymbol = "";
        pendingOperation = null;
        activeField = txtFirst;
    }

    private void handleOperation(BinaryOperation operation) {
        try {
            String firstInput = txtFirst.getText().trim();

            if (firstInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the first number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (pendingOperation != null && !txtSecond.getText().trim().isEmpty()) {
                calculateResult();
            }

            storedOperand = txtFirst.getText().trim();
            pendingOperation = operation;

            if (operation instanceof Addition) operationSymbol = "+";
            else if (operation instanceof Subtraction) operationSymbol = "-";
            else if (operation instanceof Multiplication) operationSymbol = "×";
            else if (operation instanceof Division) operationSymbol = "÷";

            lblOperation.setText(operationSymbol);
            txtSecond.setText("");
            activeField = txtSecond;
            txtSecond.requestFocus();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calculateResult() {
        try {
            if (pendingOperation == null || storedOperand.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select an operation first", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String secondInput = txtSecond.getText().trim();
            if (secondInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the second number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            calculator.setOperation(pendingOperation);
            String result = calculator.compute(storedOperand, secondInput);
            txtResult.setText(result);


            txtFirst.setText(result);
            txtSecond.setText("");
            lblOperation.setText("");
            storedOperand = result;
            operationSymbol = "";
            pendingOperation = null;
            activeField = txtFirst;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateDigitButtons() {
        for (Map.Entry<String, JButton> entry : digitButtons.entrySet()) {
            String d = entry.getKey();
            JButton btn = entry.getValue();
            boolean enabled;
            switch (currentMode) {
                case "BIN": enabled = d.equals("0") || d.equals("1"); break;
                case "OCT": enabled = d.matches("[0-7]"); break;
                case "DEC": enabled = d.matches("[0-9]"); break;
                case "HEX": enabled = true; break;
                default: enabled = false;
            }
            btn.setEnabled(enabled);
        }
    }

    private boolean isAllowedDigit(String digit) {
        switch (currentMode) {
            case "BIN": return digit.equals("0") || digit.equals("1");
            case "OCT": return digit.matches("[0-7]");
            case "DEC": return digit.matches("[0-9]");
            case "HEX": return digit.matches("[0-9A-F]");
            default: return false;
        }
    }

    private void convertAllFieldsToMode(String targetMode) {
        String oldMode = currentMode;
        calculator.setMode(oldMode);
        try { if (!txtFirst.getText().trim().isEmpty()) {
            String converted = convertValue(txtFirst.getText().trim(), targetMode);
            txtFirst.setText(converted);
        }} catch (Exception ex) {}
        try { if (!txtSecond.getText().trim().isEmpty()) {
            String converted = convertValue(txtSecond.getText().trim(), targetMode);
            txtSecond.setText(converted);
        }} catch (Exception ex) {}
        try { if (!txtResult.getText().trim().isEmpty()) {
            String converted = convertValue(txtResult.getText().trim(), targetMode);
            txtResult.setText(converted);
        }} catch (Exception ex) {}
        calculator.setMode(targetMode);
    }

    private String convertValue(String value, String targetMode) {
        switch (targetMode) {
            case "HEX": return calculator.toHex(value);
            case "DEC": return calculator.toDecimal(value);
            case "OCT": return calculator.toOctal(value);
            case "BIN": default: return calculator.toBinary(value);
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(100, 200, 255));
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private void updateModeDisplay() {
        lblMode.setText("MODE: " + currentMode);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Consolas", Font.BOLD, 16));
        field.setBackground(new Color(35, 40, 60));
        field.setForeground(new Color(0, 255, 150));
        field.setCaretColor(new Color(0, 255, 150));
        field.setBorder(BorderFactory.createLineBorder(new Color(50, 100, 200), 1));
        return field;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setOpaque(true);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(
                        Math.min(color.getRed() + 30, 255),
                        Math.min(color.getGreen() + 30, 255),
                        Math.min(color.getBlue() + 30, 255)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(color);
            }
        });
        return button;
    }
}
