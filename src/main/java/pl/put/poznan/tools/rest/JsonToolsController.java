package pl.put.poznan.tools.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import pl.put.poznan.tools.logic.*;

import java.util.List;

@RestController
@RequestMapping("/api/json")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @PostMapping(value = "/minify", produces = "application/json")
    public String minify(@RequestBody String jsonInput) {
        logger.info("Received request to minify JSON");
        logger.debug("Input: {}", jsonInput);

        JsonToolsTransformer transformer = new MinifyDecorator(new JsonToolsBase());
        String result = transformer.transform(jsonInput);

        logger.debug("Output: {}", result);
        return result;
    }

    @PostMapping(value = "/beautify", produces = "application/json")
    public String beautify(@RequestBody String jsonInput) {
        logger.info("Received request to beautify JSON");
        logger.debug("Input: {}", jsonInput);

        JsonToolsTransformer transformer = new BeautifyDecorator(new JsonToolsBase());
        String result = transformer.transform(jsonInput);

        logger.debug("Output: {}", result);
        return result;
    }

    @PostMapping(value = "/filter/whitelist", produces = "application/json")
    public String filterWhitelist(@RequestBody String jsonInput, @RequestParam List<String> keys) {
        logger.info("Received request for whitelist filtering with keys: {}", keys);
        logger.debug("Input: {}", jsonInput);

        JsonToolsTransformer transformer = new WhitelistFilterDecorator(new JsonToolsBase(), keys);
        String result = transformer.transform(jsonInput);

        logger.debug("Output: {}", result);
        return result;
    }

    @PostMapping(value = "/filter/blacklist", produces = "application/json")
    public String filterBlacklist(@RequestBody String jsonInput, @RequestParam List<String> keys) {
        logger.info("Received request for blacklist filtering with keys: {}", keys);
        logger.debug("Input: {}", jsonInput);

        JsonToolsTransformer transformer = new BlacklistFilterDecorator(new JsonToolsBase(), keys);
        String result = transformer.transform(jsonInput);

        logger.debug("Output: {}", result);
        return result;
    }

    @PostMapping(value = "/diff", produces = "text/plain")
    public String diff(@RequestBody String jsonInputA, @RequestParam String jsonInputB) {
        logger.info("Received request to compare two JSON structures");
        logger.debug("Input A: {}, Input B: {}", jsonInputA, jsonInputB);

        JsonTools tools = new JsonTools();
        String result = tools.compare(jsonInputA, jsonInputB);

        logger.debug("Diff result: {}", result);
        return result;
    }
}
