package tokenizer;

import token.NumberToken;

import java.io.IOException;

public class NumberState implements TokenizerState {
    String buffer;

    public NumberState() {
        this.buffer = "";
    }

    @Override
    public void next(Tokenizer tokenizer) {
        try {
            while (true) {
                int charCode = tokenizer.getNext();
                if (charCode == -1) {
                    tokenizer.setState(new EndState());
                    break;
                }
                char currentChar = (char) charCode;
                if (Character.isWhitespace(currentChar)) {
                    tokenizer.setState(new StartState());
                    break;
                }
                if (Character.isDigit(currentChar)) {
                    buffer += currentChar;
                } else {
                    tokenizer.pushBack(currentChar);
                    break;
                }
            }
            tokenizer.addToken(new NumberToken(Double.parseDouble(buffer)));
            tokenizer.setState(new StartState());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printStatus() {
        System.out.println("We are at the number.");
    }
}
