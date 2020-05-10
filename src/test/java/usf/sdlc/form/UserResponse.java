package usf.sdlc.form;

public class UserResponse {
    private float userId;
    private String email;
    private float createdDate;
    private String accessToken;
    private String role;
    private float active;


    // Getter Methods

    public float getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public float getCreatedDate() {
        return createdDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRole() {
        return role;
    }

    public float getActive() {
        return active;
    }

    // Setter Methods

    public void setUserId(float userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreatedDate(float createdDate) {
        this.createdDate = createdDate;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setActive(float active) {
        this.active = active;
    }
}
