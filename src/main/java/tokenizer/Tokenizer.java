package tokenizer;

import token.Token;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final PushbackReader reader;
    private final List<Token> tokens;
    private TokenizerState state = new StartState();

    public Tokenizer(InputStream inputStream, Charset encoding) {
        this.reader = new PushbackReader(new InputStreamReader(inputStream, encoding));
        this.tokens = new ArrayList<>();
    }

    public void setState(TokenizerState state){
        this.state = state;
    }

    public void tokenize() {
        while (!(state instanceof EndState)) {
            nextState();
        }
    }

    public void nextState() {
        state.next(this);
    }

    public void printStatus() {
        state.printStatus();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public int getNext() throws IOException {
        return reader.read();
    }

    public void pushBack(char ch) throws IOException {
        reader.unread(ch);
    }

    public void addToken(Token token) {
        tokens.add(token);
    }
}
