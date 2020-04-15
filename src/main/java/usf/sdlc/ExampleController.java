package usf.sdlc;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micronaut.http.annotation.*;

@Controller("/")
public class ExampleController {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleController.class);

    @Get()
    public String index() {
        System.out.println("Ping API");
        return "DoD API";
    }
}
