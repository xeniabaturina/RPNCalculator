package visitors;

import token.Brace;
import token.Brace.BraceType;
import token.NumberToken;
import token.Operation;
import token.Operation.OperationType;
import token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    private final List<Token> tokens;
    private final List<Token> listRPN;
    private final Stack<Token> stack;

    public ParserVisitor(List<Token> tokens) {
        this.tokens = tokens;
        this.listRPN = new ArrayList<>();
        this.stack = new Stack<>();
    }

    private int precedence(Token token) {
        if (token instanceof Operation) {
            OperationType type = ((Operation) token).getType();
            return switch (type) {
                case MUL, DIV -> 2;
                case  ADD,  SUB -> 1;
            };
        }
        return 0;
    }

    @Override
    public List<Token> getTokens() {
        return tokens;
    }

    public List<Token> getRPN() {
        return listRPN;
    }

    @Override
    public void visit(NumberToken token) {
        listRPN.add(token);
    }

    @Override
    public void visit(Brace token) {
        if (token.getType() == BraceType.LEFT) {
            stack.push(token);
        } else {
            while (!stack.isEmpty() &&
                    !(stack.peek() instanceof Brace && ((Brace) stack.peek()).getType() == BraceType.LEFT)) {
                listRPN.add(stack.pop());
            }
            stack.pop();
        }
    }

    @Override
    public void visit(Operation token) {
        if (!stack.empty() && precedence(stack.peek()) >= precedence(token)) {
            while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(token)) {
                listRPN.add(stack.pop());
            }
        }
        stack.push(token);
    }

    @Override
    public void end() {
        while (!stack.isEmpty()) {
            listRPN.add(stack.pop());
        }
    }
}
