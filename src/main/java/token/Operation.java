package token;

import visitors.TokenVisitor;

public class Operation implements Token {
    public enum OperationType {
        ADD, SUB, MUL, DIV
    }

    private final OperationType type;

    public Operation(OperationType type) {
        this.type = type;
    }

    public OperationType getType() {
        return type;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
