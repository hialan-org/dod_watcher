package usf.sdlc;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micronaut.http.annotation.*;

@Controller("/")
public class ExampleController {
    private Logger log = LoggerFactory.getLogger(ExampleController.class);

    @Get()
    public String index() {
        log.trace("Ping API");
        return "DoD API";
    }

    @Get("/ping")
    public String ping() {
        log.trace("Ping API");
        return "DoD API";
    }
}
