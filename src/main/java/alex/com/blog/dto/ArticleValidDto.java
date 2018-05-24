package alex.com.blog.dto;

import alex.com.blog.domaine.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class ArticleValidDto  implements Serializable {


    private static final long serialVersionUID = 6442256161505797372L;

    @NotNull @Size(min = 10, max = 250, message = "Размер заголовка не должен быть менее 10 символов и не должен превышать 250 символов.")
    private String header;

    @NotNull @Size(min = 50, max = 5000, message = "Размер статьи не должен быть менее 50 символов и не должен превышать 5000 символов.")
    private String content;

    private String uid;

    private User user;

    public ArticleValidDto() {}

    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleValidDto)) return false;
        ArticleValidDto that = (ArticleValidDto) o;
        return Objects.equals(header, that.header) &&
                Objects.equals(uid, that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, uid);
    }
}
