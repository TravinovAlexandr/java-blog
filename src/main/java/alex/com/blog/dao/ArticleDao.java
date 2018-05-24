package alex.com.blog.dao;

import alex.com.blog.domaine.Article;
import alex.com.blog.domaine.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ArticleDao extends JpaRepository <Article, Long> {

    Article findByUid(String uid);
    List<Article> findByUserOrderByIdDesc(User user, Pageable pageable);
    List<Article> findByIdBetween(Long first, Long last);

    @Query(value = "select coalesce(max(a.id), 0) from Article a")
    Long selectMaxId();
}
