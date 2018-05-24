package alex.com.blog.dao

import alex.com.blog.domaine.Article
import alex.com.blog.domaine.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentDao : JpaRepository<Comment, Long> {

    fun findAllByArticle(article : Article) : MutableList<Comment>?

}