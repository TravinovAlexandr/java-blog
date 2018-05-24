package alex.com.blog.domaine;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "email_verification")
public class EmailVerificationToken  implements Serializable {

    private static final long serialVersionUID = -5842891512541423826L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "token")
    private String token;

    @Basic @NotNull
    @Column(name = "expire_time")
    private LocalTime expireTime;

    @Basic @NotNull
    @Column(name="expire_date")
    private LocalDate expireDate;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public EmailVerificationToken() {}

    public EmailVerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expireTime = LocalTime.now();
        this.expireDate = LocalDate.now();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public LocalTime getExpireTime() {
        return expireTime;
    }
    public void setExpireTime(LocalTime expireTime) {
        this.expireTime = expireTime;
    }
    public LocalDate getExpireDate() {
        return expireDate;
    }
    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
