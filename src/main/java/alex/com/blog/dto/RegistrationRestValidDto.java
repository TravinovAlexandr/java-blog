package alex.com.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

public class RegistrationRestValidDto implements Serializable {

    private static final long serialVersionUID = -1856637245129350437L;

    @JsonIgnore
    private boolean flag = false;
    private String nick = "correct";
    private String email = "correct";
    private String password = "correct";
    private String message = "";

    public RegistrationRestValidDto() {}

    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean mark) {
        this.flag = mark;
    }

}

