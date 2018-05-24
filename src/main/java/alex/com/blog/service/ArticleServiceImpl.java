package alex.com.blog.service;

import alex.com.blog.dao.ArticleDao;
import alex.com.blog.domaine.Article;
import alex.com.blog.domaine.User;
import alex.com.blog.dto.ArticleValidDto;
import alex.com.blog.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements  ArticleService {

    private ArticleDao articleDao;
    private ElasticSearchService elasticSearchService;

    public ArticleServiceImpl() {}

    @Override @Transactional(readOnly = true)
    public boolean uidExists(final String uid) {
        try {
            return articleDao.findByUid(uid) != null;
        } catch (RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional @Async
    public void addArticle(final ArticleValidDto a) {
        if (a == null) throw new ServiceException(new NullPointerException());
        Article article = new Article();
        try {
            article.setCreationDate(LocalDate.now());
            article.setCreationTime(LocalTime.now());
            article.setHeader(a.getHeader());
            article.setContent(a.getContent());
            article.setUser(a.getUser());
            article.setUid(a.getUid());
            articleDao.save(article);
            elasticSearchService.save(article);
        } catch (RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public List<Article> getPageableArticles(final User user, final Pageable pageable) {
        try {
            return articleDao.findByUserOrderByIdDesc(user, pageable);
        } catch (RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public List<Article> findArticlesInRange(final Long range) {
        try {
            Long last = articleDao.selectMaxId();
            return articleDao.findByIdBetween(last - range - 1, last)
                    .stream()
                    .sorted((a, b) -> (int) (b.getId() - a.getId())).collect(Collectors.toList());
        } catch(RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public Article findByUid(final String uid) {
        try {
            return articleDao.findByUid(uid);
        } catch(RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Override @Transactional(readOnly = true)
    public List<Article> findAll() {
        try {
            return articleDao.findAll();
        } catch(RuntimeException e) { throw new ServiceException(e.getCause()); }
    }

    @Autowired
    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Autowired
    public void setElasticSearchService(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }
}
