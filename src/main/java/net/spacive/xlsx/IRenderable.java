package net.spacive.xlsx;

import net.spacive.xml_core.IStringConsumer;

public interface IRenderable {
    IRenderable EMPTY = contentWriter -> { };
    void renderInto(IStringConsumer contentWriter);
}
