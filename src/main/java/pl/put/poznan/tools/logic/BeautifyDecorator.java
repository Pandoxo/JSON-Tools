package pl.put.poznan.tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Decorator that beautifies JSON by adding proper indentation
 * and newlines for improved readability.
 */
public class BeautifyDecorator extends JsonToolsDecorator {

    private static final Logger logger = LoggerFactory.getLogger(BeautifyDecorator.class);
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructs a BeautifyDecorator wrapping the given transformer.
     *
     * @param wrapped the transformer to wrap
     */
    public BeautifyDecorator(JsonToolsTransformer wrapped) {
        super(wrapped);
    }

    /**
     * Applies the wrapped transformation first, then beautifies the result.
     *
     * @param json the input JSON string
     * @return the beautified JSON string, or an error JSON if input is invalid
     */
    @Override
    public String transform(String json) {
        String result = wrapped.transform(json);
        logger.info("Applying beautify transformation");
        logger.debug("Beautify input length: {}", result.length());
        try {
            JsonNode jsonNode = mapper.readTree(result);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            logger.debug("Beautify failed: {}", e.getMessage());
            return "{\n  \"error\": \"Invalid JSON input format.\"\n}";
        }
    }
}
