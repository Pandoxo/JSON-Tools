package pl.put.poznan.tools.logic;

/**
 * Interface for JSON transformation operations.
 * Implementations can be chained using the Decorator pattern
 * to dynamically compose multiple transformations.
 */
public interface JsonToolsTransformer {

    /**
     * Transforms the given JSON string.
     *
     * @param json the input JSON string to transform
     * @return the transformed JSON string
     */
    String transform(String json);
}
