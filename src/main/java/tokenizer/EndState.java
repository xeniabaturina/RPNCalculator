package tokenizer;

public class EndState implements TokenizerState {
    @Override
    public void next(Tokenizer tokenizer) {}

    @Override
    public void printStatus() {
        System.out.println("We are at the end.");
    }
}
