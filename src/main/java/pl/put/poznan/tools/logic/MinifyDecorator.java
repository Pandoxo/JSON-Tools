package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Decorator that minifies JSON by removing all unnecessary whitespace
 * and newlines, producing a compact single-line representation.
 */
public class MinifyDecorator extends JsonToolsDecorator {

    private static final Logger logger = LoggerFactory.getLogger(MinifyDecorator.class);
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructs a MinifyDecorator wrapping the given transformer.
     *
     * @param wrapped the transformer to wrap
     */
    public MinifyDecorator(JsonToolsTransformer wrapped) {
        super(wrapped);
    }

    /**
     * Applies the wrapped transformation first, then minifies the result.
     *
     * @param json the input JSON string
     * @return the minified JSON string, or an error JSON if input is invalid
     */
    @Override
    public String transform(String json) {
        String result = wrapped.transform(json);
        logger.info("Applying minify transformation");
        logger.debug("Minify input length: {}", result.length());
        try {
            JsonNode jsonNode = mapper.readTree(result);
            return mapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            logger.debug("Minify failed: {}", e.getMessage());
            return "{\"error\":\"Invalid JSON input format.\"}";
        }
    }
}
