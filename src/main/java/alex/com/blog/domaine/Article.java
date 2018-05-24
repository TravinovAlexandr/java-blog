package alex.com.blog.domaine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Document(indexName = "blog", type = "article" , shards = 1)
public class Article implements Serializable {

    private static final long serialVersionUID = 7803802208093232682L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Size(min = 12, max = 12)
    private String uid;

    @NotNull @Size(min = 10, max = 250)
    private String header;

    @NotNull @Size(min = 50, max = 5000)
    private String content;

    @JsonIgnore @NotNull
    @Basic @Column(name = "creation_date")
    private LocalDate creationDate;

    @JsonIgnore @NotNull
    @Basic @Column(name = "creation_time")
    private LocalTime creationTime;

    @JsonIgnore @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "article")
    private List<Comment> comments;

    public Article() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) &&
                Objects.equals(uid, article.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid);
    }
}
