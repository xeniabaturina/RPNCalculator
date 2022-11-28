import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import token.NumberToken;
import token.Operation;
import token.Token;
import visitors.CalcVisitor;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcVisitorTest {
    CalcVisitor calcVisitor;
    ByteArrayOutputStream byteArrayOutputStream;

    @BeforeEach
    public void setUp() {
        byteArrayOutputStream = new ByteArrayOutputStream(12);
    }

    @Test
    public void testNumber() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(3));

        calcVisitor = new CalcVisitor(tokens, byteArrayOutputStream);
        calcVisitor.visit();

        assertEquals("Result 3.0\n", byteArrayOutputStream.toString());
    }

    @Test
    public void testAdd() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(3));
        tokens.add(new NumberToken(5));
        tokens.add(new Operation(Operation.OperationType.ADD));

        calcVisitor = new CalcVisitor(tokens, byteArrayOutputStream);
        calcVisitor.visit();

        assertEquals("Result 8.0\n", byteArrayOutputStream.toString());
    }

    @Test
    public void testSub() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(3));
        tokens.add(new NumberToken(5));
        tokens.add(new Operation(Operation.OperationType.SUB));

        calcVisitor = new CalcVisitor(tokens, byteArrayOutputStream);
        calcVisitor.visit();

        assertEquals("Result -2.0\n", byteArrayOutputStream.toString());
    }

    @Test
    public void testMul() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(3));
        tokens.add(new NumberToken(5));
        tokens.add(new Operation(Operation.OperationType.MUL));

        calcVisitor = new CalcVisitor(tokens, byteArrayOutputStream);
        calcVisitor.visit();

        assertEquals("Result 15.0\n", byteArrayOutputStream.toString());
    }

    @Test
    public void testDiv() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(3));
        tokens.add(new NumberToken(5));
        tokens.add(new Operation(Operation.OperationType.DIV));

        calcVisitor = new CalcVisitor(tokens, byteArrayOutputStream);
        calcVisitor.visit();

        assertEquals("Result 0.6\n", byteArrayOutputStream.toString());
    }

    @Test
    public void testExpression() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new NumberToken(3));
        tokens.add(new NumberToken(5));
        tokens.add(new Operation(Operation.OperationType.DIV));
        tokens.add(new NumberToken(4));
        tokens.add(new Operation(Operation.OperationType.MUL));
        tokens.add(new NumberToken(7));
        tokens.add(new Operation(Operation.OperationType.SUB));

        calcVisitor = new CalcVisitor(tokens, byteArrayOutputStream);
        calcVisitor.visit();

        assertEquals("Result -4.6\n", byteArrayOutputStream.toString());
    }
}
