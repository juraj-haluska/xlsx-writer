package net.spacive.xml_core;

import java.io.OutputStream;
import java.io.PrintWriter;

public class OutputStreamContentWriter implements IStringConsumer {

    private final PrintWriter writer;

    public OutputStreamContentWriter(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream);
    }

    @Override
    public void consume(String content) {
        this.writer.print(content);
    }

    public void flush() {
        writer.flush();
    }
}
