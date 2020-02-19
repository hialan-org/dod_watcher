package usf.sdlc.form;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected
public class UserUpdateForm {
    @NotNull
    private Long userId;

    @NotBlank
    private String email;

    public UserUpdateForm() {
    }

    public UserUpdateForm(Long id, String email) {
        this.userId = id;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
