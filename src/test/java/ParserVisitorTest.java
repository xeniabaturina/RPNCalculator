import org.junit.jupiter.api.Test;
import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;
import visitors.ParserVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserVisitorTest {
    ParserVisitor parserVisitor;

    @Test
    public void testNumber() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(3));

        parserVisitor = new ParserVisitor(tokens);
        parserVisitor.visit();

        List<Token> RPN = new ArrayList<>();
        RPN.add(new NumberToken(3));

        assertEquals(
                RPN.stream().map(Token::getClass).collect(Collectors.toList()),
                parserVisitor.getRPN().stream().map(Token::getClass).collect(Collectors.toList()));
        assertTrue(parserVisitor.getRPN().get(0) instanceof NumberToken);
        assertEquals(3, ((NumberToken) parserVisitor.getRPN().get(0)).getValue());
    }

    @Test
    public void testOperation() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(3));
        tokens.add(new Operation(Operation.OperationType.ADD));
        tokens.add(new NumberToken(5));

        parserVisitor = new ParserVisitor(tokens);
        parserVisitor.visit();

        List<Token> RPN = new ArrayList<>();
        RPN.add(new NumberToken(3));
        RPN.add(new NumberToken(5));
        RPN.add(new Operation(Operation.OperationType.ADD));

        assertEquals(
                RPN.stream().map(Token::getClass).collect(Collectors.toList()),
                parserVisitor.getRPN().stream().map(Token::getClass).collect(Collectors.toList()));
    }

    @Test
    public void testBraces() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Brace(Brace.BraceType.LEFT));
        tokens.add(new NumberToken(3));
        tokens.add(new Operation(Operation.OperationType.ADD));
        tokens.add(new NumberToken(5));
        tokens.add(new Brace(Brace.BraceType.RIGHT));

        parserVisitor = new ParserVisitor(tokens);
        parserVisitor.visit();

        List<Token> RPN = new ArrayList<>();
        RPN.add(new NumberToken(3));
        RPN.add(new NumberToken(5));
        RPN.add(new Operation(Operation.OperationType.ADD));

        assertEquals(
                RPN.stream().map(Token::getClass).collect(Collectors.toList()),
                parserVisitor.getRPN().stream().map(Token::getClass).collect(Collectors.toList()));
    }
}
