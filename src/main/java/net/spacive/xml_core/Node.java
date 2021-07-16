package net.spacive.xml_core;

public abstract class Node implements IStringConsumer {

    private final IStringConsumer parent;

    private boolean isOpened = false;
    private boolean isClosed = false;

    public Node(IStringConsumer parent) {
        this.parent = parent;
    }

    @Override
    public void consume(String string) {
        if (!this.isOpened) {
            throw new RuntimeException("Node cannot consume content because it is not opened");
        }
        if (this.isClosed) {
            throw new RuntimeException("Node cannot consume content because it is closed");
        }
        this.parent.consume(string);
    }

    public Node open() {
        if (this.isClosed) {
            throw new RuntimeException("Cannot open node because it is already closed");
        }
        if (this.isOpened) {
            throw new RuntimeException("Cannot open node because it is already opened");
        }
        this.isOpened = true;
        this.parent.consume(getNodeOpening());
        return this;
    }

    public void close() {
        if (!this.isOpened) {
            throw new RuntimeException("Node must be opened first before closing");
        }
        if (this.isClosed) {
            throw new RuntimeException("Cannot close node because it is already closed");
        }

        this.isClosed = true;
        this.parent.consume(getNodeClosing());
    }

    protected abstract String getNodeOpening();

    protected abstract String getNodeClosing();
}
