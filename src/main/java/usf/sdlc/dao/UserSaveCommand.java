package usf.sdlc.dao;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class UserSaveCommand {

    @NotBlank
    private String email;

    public UserSaveCommand() {
    }

    public UserSaveCommand(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
