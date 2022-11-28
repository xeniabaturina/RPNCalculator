package token;

import visitors.TokenVisitor;

public class Brace implements Token {
    public enum BraceType {
        LEFT, RIGHT
    }

    private final BraceType type;

    public Brace(BraceType type) {
        this.type = type;
    }

    public BraceType getType() {
        return type;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
