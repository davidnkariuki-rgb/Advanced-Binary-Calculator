package operations;

public class Division implements BinaryOperation {

    @Override
    public long execute(long a, long b) {
        if (b == 0) {
            throw new ArithmeticException("Division by Zero");
        }
        return a / b;
    }
}
