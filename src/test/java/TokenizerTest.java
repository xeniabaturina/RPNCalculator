import org.junit.jupiter.api.Test;
import token.Brace;
import token.NumberToken;
import token.Operation;
import token.Token;
import tokenizer.Tokenizer;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenizerTest {
    Tokenizer tokenizer;

    @Test
    public void testNumber() {
        String expression = "3";

        tokenizer = new Tokenizer(
                new ByteArrayInputStream(expression.getBytes()),
                Charset.defaultCharset());
        tokenizer.tokenize();

        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(3));

        assertEquals(
                tokens.stream().map(Token::getClass).collect(Collectors.toList()),
                tokenizer.getTokens().stream().map(Token::getClass).collect(Collectors.toList()));
        assertTrue(tokenizer.getTokens().get(0) instanceof NumberToken);
        assertEquals(3, ((NumberToken) tokenizer.getTokens().get(0)).getValue());
    }

    @Test
    public void testOperation() {
        String expression = "3 + 5";

        tokenizer = new Tokenizer(
                new ByteArrayInputStream(expression.getBytes()),
                Charset.defaultCharset());
        tokenizer.tokenize();

        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(3));
        tokens.add(new Operation(Operation.OperationType.ADD));
        tokens.add(new NumberToken(5));

        assertEquals(
                tokens.stream().map(Token::getClass).collect(Collectors.toList()),
                tokenizer.getTokens().stream().map(Token::getClass).collect(Collectors.toList()));
    }

    @Test
    public void testBraces() {
        String expression = "(3 + 5) * 7";

        tokenizer = new Tokenizer(
                new ByteArrayInputStream(expression.getBytes()),
                Charset.defaultCharset());
        tokenizer.tokenize();

        List<Token> tokens = new ArrayList<>();
        tokens.add(new Brace(Brace.BraceType.LEFT));
        tokens.add(new NumberToken(3));
        tokens.add(new Operation(Operation.OperationType.ADD));
        tokens.add(new NumberToken(5));
        tokens.add(new Brace(Brace.BraceType.RIGHT));
        tokens.add(new Operation(Operation.OperationType.MUL));
        tokens.add(new NumberToken(7));

        assertEquals(
                tokens.stream().map(Token::getClass).collect(Collectors.toList()),
                tokenizer.getTokens().stream().map(Token::getClass).collect(Collectors.toList()));
    }
}
