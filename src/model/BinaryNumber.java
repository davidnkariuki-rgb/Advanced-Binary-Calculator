package model;

public class BinaryNumber {

    private String value;

    public BinaryNumber(String value) {
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (!isValidBinary(value)) {
            throw new IllegalArgumentException("Invalid Binary Number");
        }
        this.value = value;
    }

    public int toDecimal() {
        return Integer.parseInt(value);
    }

    public static BinaryNumber fromDecimal(int decimal) {
        return new BinaryNumber(Integer.toBinaryString(decimal));
    }

    private boolean isValidBinary(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        for (char c : value.toCharArray()) {
            if(c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }
}

