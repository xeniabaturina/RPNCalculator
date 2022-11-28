import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        String initialString = "(30 + 2) / 8";
        System.out.println("Expression: " + initialString);

        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());

        RPNCalculator calculator = new RPNCalculator(targetStream, System.out);
        calculator.convertToRPN();
    }
}
