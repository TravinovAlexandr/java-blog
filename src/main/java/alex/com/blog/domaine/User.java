package alex.com.blog.domaine;

import org.hibernate.validator.constraints.Email;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = -1931956504977707630L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=3, max=18) @NotNull
    @Column(name="nick", nullable = false, unique = true)
    private String nick;

    @Email @NotNull
    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Size(min=1, max=20)
    @Column(name="name")
    private String name;

    @Size(min=1, max=20)
    @Column(name="last_name")
    private String lastName;

    @Size(min=3, max=100) @NotNull
    @Column(name="password", nullable = false, unique = true)
    private String password;

    @Column(name="enable", nullable = false)
    private boolean enable;

    @Column(name="uid", nullable = false)
    private String uid;

    @Basic @Column(name = "creation_date")
    private LocalDate creationDate;

    @Basic @Column(name = "creation_time")
    private LocalTime creationTime;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "user")
    private List<EmailVerificationToken> emailVerificationToken;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Article> articles;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "users_avatars", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "avatar_id"))
    private List<Avatar> avatars;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Comment> comments;

    
    public User() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isEnable() {
        return enable;
    }
    public void setEnable(boolean enable) {
        this.enable = enable;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public List<EmailVerificationToken> getEmailVerificationToken() {
        return emailVerificationToken;
    }
    public void setEmailVerificationToken(List<EmailVerificationToken> emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }
    public List<Article> getArticles() {
        return articles;
    }
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    public LocalTime getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(LocalTime creationTime) {
        this.creationTime = creationTime;
    }
    public List<Avatar> getAvatars() {
        return avatars;
    }
    public void setAvatars(List<Avatar> avatars) {
        this.avatars = avatars;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(nick, user.nick) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nick, email);
    }
}
