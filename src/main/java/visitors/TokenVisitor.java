package visitors;

import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;

import java.util.List;

public interface TokenVisitor {
    List<Token> getTokens();
    void visit(NumberToken token);
    void visit(Brace token);
    void visit(Operation token);
    void end();

    default void visit() {
        for (Token token : getTokens()) {
            token.accept(this);
        }
        end();
    }
}
