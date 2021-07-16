package net.spacive.xlsx.util;

import net.spacive.xml_core.IStringConsumer;

/**
 * Adds ability to IContentWriter to mark it as closed.
 */
public class CloseableStringConsumer implements IStringConsumer {

    private final IStringConsumer consumer;

    private boolean isClosed = false;

    public CloseableStringConsumer(IStringConsumer consumer) {
        this.consumer = consumer;
    }

    public void close() {
        isClosed = true;
    }

    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void consume(String content) {
        if (isClosed) {
            throw new RuntimeException("writer is closed");
        }

        consumer.consume(content);
    }
}
