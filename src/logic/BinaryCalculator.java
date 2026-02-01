package logic;

import operations.BinaryOperation;

public class BinaryCalculator {

    private BinaryOperation operation;
    private String currentMode = "BIN"; 
    public void setOperation(BinaryOperation operation) {
        this.operation = operation;
    }

    public void setMode(String mode) {
        this.currentMode = mode;
    }

    private long parseInput(String input) {
        input = input.trim().toUpperCase();
        try {
            switch (currentMode) {
                case "BIN":
                    return Long.parseLong(input, 2);
                case "DEC":
                    return Long.parseLong(input, 10);
                case "HEX":
                    return Long.parseLong(input, 16);
                case "OCT":
                    return Long.parseLong(input, 8);
                default:
                    return Long.parseLong(input, 2);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input for mode " + currentMode);
        }
    }

    private String formatOutput(long decimal) {
        switch (currentMode) {
            case "BIN":
                return Long.toBinaryString(decimal);
            case "DEC":
                return Long.toString(decimal);
            case "HEX":
                return Long.toHexString(decimal).toUpperCase();
            case "OCT":
                return Long.toOctalString(decimal);
            default:
                return Long.toBinaryString(decimal);
        }
    }

    public String compute(String a, String b) {
        if (operation == null) {
            throw new IllegalStateException("No operation selected");
        }

        long firstDecimal = parseInput(a);
        long secondDecimal = parseInput(b);

        long result = operation.execute(firstDecimal, secondDecimal);
        return formatOutput(result);
    }


    public String toHex(String input) {
        long decimal = parseInput(input);
        return Long.toHexString(decimal).toUpperCase();
    }   

    public String toDecimal(String input) {
        long decimal = parseInput(input);
        return Long.toString(decimal);
    }

    public String toOctal(String input) {
        long decimal = parseInput(input);
        return Long.toOctalString(decimal);
    }

    public String toBinary(String input) {
        long decimal = parseInput(input);
        return Long.toBinaryString(decimal);
    }
}
