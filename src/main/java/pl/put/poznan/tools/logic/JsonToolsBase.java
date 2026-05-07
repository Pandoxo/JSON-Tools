package pl.put.poznan.tools.logic;

/**
 * Base implementation of {@link JsonToolsTransformer} that returns
 * the JSON string unchanged. Serves as the starting point for
 * decorator chains.
 */
public class JsonToolsBase implements JsonToolsTransformer {

    /**
     * Returns the JSON string unchanged.
     *
     * @param json the input JSON string
     * @return the same JSON string, unmodified
     */
    @Override
    public String transform(String json) {
        return json;
    }
}
