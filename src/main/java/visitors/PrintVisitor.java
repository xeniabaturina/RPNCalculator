package visitors;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class PrintVisitor implements TokenVisitor {
    private final List<Token> tokens;
    private final Writer writer;

    public PrintVisitor(List<Token> tokens, OutputStream outputStream) {
        this.tokens = tokens;
        this.writer = new OutputStreamWriter(outputStream);
    }

    private void printToken(String tokenWrapper, String tokenValue) {
        try {
            if (tokenWrapper.isEmpty()) writer.write(tokenValue + " ");
            else writer.write(tokenWrapper + "(" + tokenValue + ") ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Token> getTokens() {
        return tokens;
    }

    @Override
    public void visit(NumberToken token) {
        printToken("NUMBER", String.valueOf(token.getValue()));
    }

    @Override
    public void visit(Brace token) {
        printToken("", String.valueOf(token.getType()));
    }

    @Override
    public void visit(Operation token) {
        printToken("", String.valueOf(token.getType()));
    }

    @Override
    public void end() {
        try {
            writer.write('\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
