package usf.sdlc;

import io.micronaut.core.annotation.TypeHint;
import io.micronaut.runtime.Micronaut;

@TypeHint(typeNames = "org.postgresql.Driver.class")
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}