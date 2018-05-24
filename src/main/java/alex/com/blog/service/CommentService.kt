package alex.com.blog.service

import alex.com.blog.domaine.Article
import alex.com.blog.domaine.Comment
import java.util.concurrent.Future

interface CommentService {

    fun addComment(comment : Comment?)
    fun deleteComment(comment : Comment?)
    fun updateComment(comment : Comment?)
    fun findAllByArticleAsync(article : Article?) : Future<MutableList<Comment>>
    fun findAllByArticle(article : Article?) : MutableList<Comment>?

}