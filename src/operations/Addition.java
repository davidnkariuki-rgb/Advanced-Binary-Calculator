package operations;

public class Addition implements BinaryOperation{

    @Override
    public long execute(long a, long b) {
        return a + b;
    }
}
