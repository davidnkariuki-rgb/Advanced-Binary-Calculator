package operations;

public class Multiplication implements BinaryOperation {

    @Override
    public long execute (long a, long b){
        return a * b;
    }
}
