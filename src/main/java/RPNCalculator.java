import token.Token;
import tokenizer.Tokenizer;
import visitors.CalcVisitor;
import visitors.ParserVisitor;
import visitors.PrintVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

public class RPNCalculator {
    InputStream inputStream;
    OutputStream outputStream;

    public RPNCalculator(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void convertToRPN() {
        Charset encoding = Charset.defaultCharset();
        Tokenizer tokenizer = new Tokenizer(inputStream, encoding);
        tokenizer.tokenize();

        List<Token> tokens = tokenizer.getTokens();

        ParserVisitor parserVisitor = new ParserVisitor(tokens);
        parserVisitor.visit();

        PrintVisitor printVisitor = new PrintVisitor(tokens, outputStream);
        printVisitor.visit();

        CalcVisitor calcVisitor = new CalcVisitor(parserVisitor.getRPN(), outputStream);
        calcVisitor.visit();

        close();
    }

    public void close() {
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
