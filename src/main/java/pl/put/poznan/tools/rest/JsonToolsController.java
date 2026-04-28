package pl.put.poznan.tools.rest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import pl.put.poznan.tools.logic.JsonTools;

import java.util.List;


@RestController
@RequestMapping("/api/json")
public class JsonToolsController {

    private static final Logger logger = LoggerFactory.getLogger(JsonToolsController.class);

    @PostMapping(value = "/minify", produces = "application/json")
    public String minify(@RequestBody String jsonInput) {
        logger.debug("Received request to minify JSON");
        
        JsonTools tools = new JsonTools();
        return tools.minify(jsonInput);
    }

    @PostMapping(value = "/beautify", produces = "application/json")
    public String beautify(@RequestBody String jsonInput) {
        logger.debug("Received request to beautify JSON");
        
        JsonTools tools = new JsonTools();
        return tools.beautify(jsonInput);
    }

    @PostMapping(value = "/filter/whitelist", produces = "application/json")
    public String filterWhitelist(@RequestBody String jsonInput, @RequestParam List<String> keys) {
        logger.debug("Received request for whitelist filtering with keys: {}", keys);
        
        JsonTools tools = new JsonTools();
        return tools.filterWhitelist(jsonInput, keys);
    }

    @PostMapping(value = "/filter/blacklist", produces = "application/json")
    public String filterBlacklist(@RequestBody String jsonInput, @RequestParam List<String> keys) {
        logger.debug("Received request for blacklist filtering with keys: {}", keys);
        
        JsonTools tools = new JsonTools();
        return tools.filterBlacklist(jsonInput, keys);
    }


    @PostMapping(value = "/diff", produces = "text/plain")
    public String diff(@RequestBody String jsonInputA, @RequestParam String jsonInputB) {
        logger.debug("Received request to compare two JSON structures");
        
        JsonTools tools = new JsonTools();
        return tools.compare(jsonInputA, jsonInputB);
    }



}


