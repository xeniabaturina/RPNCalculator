package tokenizer;

public interface TokenizerState {
    void next(Tokenizer tokenizer);
    void printStatus();
}
