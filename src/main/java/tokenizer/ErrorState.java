package tokenizer;

public class ErrorState implements TokenizerState {
    IllegalArgumentException exception;

    public ErrorState(String message) {
        this.exception = new IllegalArgumentException(message);
    }

    @Override
    public void next(Tokenizer tokenizer) {
        throw exception;
    }

    @Override
    public void printStatus() {
        System.out.println("We are at the end.");
    }
}
