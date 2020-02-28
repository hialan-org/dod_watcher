package usf.sdlc.form;

public class GoogleResponse {
    public GoogleResponse() {
    }

    public GoogleResponse(String aud, String email, int expires_in) {
        this.aud = aud;
        this.email = email;
        this.expires_in = expires_in;
    }

    private String aud;
    private String email;
    private int expires_in;

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
