package tokenizer;

import token.Brace;
import token.Brace.BraceType;
import token.Operation;
import token.Operation.OperationType;

import java.io.IOException;

public class StartState implements TokenizerState {
    @Override
    public void next(Tokenizer tokenizer) {
        try {
            int charCode = tokenizer.getNext();
            if (charCode == -1) {
                tokenizer.setState(new EndState());
                return;
            }
            char currentChar = (char) charCode;
            if (Character.isWhitespace(currentChar)) {
                tokenizer.setState(new StartState());
                return;
            }

            switch (currentChar) {
                case '+' -> tokenizer.addToken(new Operation(OperationType.ADD));
                case '*' -> tokenizer.addToken(new Operation(OperationType.MUL));
                case '/' -> tokenizer.addToken(new Operation(OperationType.DIV));
                case '(' -> tokenizer.addToken(new Brace(BraceType.LEFT));
                case ')' -> tokenizer.addToken(new Brace(BraceType.RIGHT));
                case '-' -> {
                    charCode = tokenizer.getNext();
                    if (charCode == -1) {
                        tokenizer.setState(new EndState());
                        return;
                    }
                    currentChar = (char) charCode;
                    if (Character.isWhitespace(currentChar)) {
                        tokenizer.addToken(new Operation(OperationType.SUB));
                        tokenizer.setState(new StartState());
                        return;
                    }
                    if (Character.isDigit(currentChar)) {
                        tokenizer.pushBack(currentChar);
                        tokenizer.pushBack('-');
                        tokenizer.setState(new NumberState());
                    } else
                        tokenizer.setState(new ErrorState("Unknown token:" + currentChar));
                    return;
                }
                default -> {
                    if (Character.isDigit(currentChar)) {
                        tokenizer.pushBack(currentChar);
                        tokenizer.setState(new NumberState());
                    } else
                        tokenizer.setState(new ErrorState("Unknown token:" + currentChar));
                    return;
                }
            }
            tokenizer.setState(new StartState());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printStatus() {
        System.out.println("We are at the start.");
    }
}
