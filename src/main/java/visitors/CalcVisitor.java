package visitors;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Operation.OperationType;
import token.Token;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    private final List<Token> tokens;
    private final Stack<Double> stack;
    private final Writer writer;

    public CalcVisitor(List<Token> tokens, OutputStream outputStream) {
        this.tokens = tokens;
        this.stack = new Stack<>();
        this.writer = new OutputStreamWriter(outputStream);
    }

    private double evaluate(double a, double b, OperationType operationType) {
        switch (operationType) {
            case ADD:
                return a + b;
            case SUB:
                return a - b;
            case MUL:
                return a * b;
            case DIV:
                if (b == 0) throw new IllegalArgumentException("Cannot divide by zero! :(");
                return a / b;
            default:
                throw new IllegalArgumentException("Unknown operation");
        }
    }

    @Override
    public List<Token> getTokens() {
        return tokens;
    }

    @Override
    public void visit(NumberToken token) {
        stack.push(token.getValue());
    }

    @Override
    public void visit(Brace token) {
        throw new IllegalArgumentException("Braces should not be in RPN!");
    }

    @Override
    public void visit(Operation token) {
        double a = stack.pop();
        double b = stack.pop();
        double res = evaluate(b, a, token.getType());
        stack.push(res);
    }

    @Override
    public void end() {
        try {
            writer.write("Result " + stack.pop() + '\n');
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
