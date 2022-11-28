package token;

import visitors.TokenVisitor;

public class NumberToken implements Token {
    double value;

    public NumberToken(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
