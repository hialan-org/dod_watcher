package usf.sdlc.form;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class UserCreateForm {

    @NotBlank
    private String email;

    public UserCreateForm() {
    }

    public UserCreateForm(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
