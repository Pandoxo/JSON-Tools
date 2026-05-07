package pl.put.poznan.tools.logic;

/**
 * Abstract decorator for {@link JsonToolsTransformer}.
 * Wraps another transformer and delegates to it, allowing
 * subclasses to add behavior before or after the delegation.
 */
public abstract class JsonToolsDecorator implements JsonToolsTransformer {

    /** The wrapped transformer to delegate to. */
    protected final JsonToolsTransformer wrapped;

    /**
     * Constructs a decorator wrapping the given transformer.
     *
     * @param wrapped the transformer to wrap
     */
    public JsonToolsDecorator(JsonToolsTransformer wrapped) {
        this.wrapped = wrapped;
    }
}
