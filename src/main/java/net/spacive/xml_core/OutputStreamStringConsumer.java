package net.spacive.xml_core;

import java.io.OutputStream;
import java.io.PrintWriter;

public class OutputStreamStringConsumer implements IStringConsumer {

    private final PrintWriter printWriter;

    public OutputStreamStringConsumer(OutputStream outputStream) {
        printWriter = new PrintWriter(outputStream);
    }

    @Override
    public void consume(String string) {
        printWriter.print(string);
    }

    public void flush() {
        printWriter.flush();
    }
}
