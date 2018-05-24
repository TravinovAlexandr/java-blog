package alex.com.blog.service

import alex.com.blog.dao.CommentDao
import alex.com.blog.domaine.Article
import alex.com.blog.domaine.Comment
import alex.com.blog.exception.ServiceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future
import org.springframework.transaction.support.DefaultTransactionDefinition
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import javax.transaction.Transaction
import javax.transaction.TransactionManager

@Service
open class CommentServiceImpl @Autowired constructor(private var commentDao : CommentDao) : CommentService {

    @Transactional(readOnly = true)
    override fun findAllByArticle(article: Article?): MutableList<Comment>? {
        if (article === null) throw ServiceException(NullPointerException())
        try {
            return commentDao.findAllByArticle(article)
        } catch (e : RuntimeException) { throw ServiceException(e.cause) }
    }

    @Async @Transactional(readOnly = true)
    override fun findAllByArticleAsync(article: Article?): Future<MutableList<Comment>> {
        if(article === null) throw ServiceException(NullPointerException())
        try {
            return CompletableFuture.completedFuture(commentDao.findAllByArticle(article))
        } catch(e : RuntimeException) {throw ServiceException(e.cause)}
    }

    @Async @Transactional
    override fun addComment(comment: Comment?) {
        if(comment === null || comment.content === null) throw ServiceException(NullPointerException())
        try {
            if(comment.content!!.contains("\n"))
                comment.content = comment.content!!.split("\n").map { "$it</br>" }.reduce{f,l -> f + l}
            commentDao.save(comment)
        } catch(e : RuntimeException) {throw ServiceException(e.cause)}
    }

    @Async @Transactional
    override fun deleteComment(comment: Comment?) {
        if(comment === null) throw ServiceException(NullPointerException())
        try {
            commentDao.delete(comment)
        } catch(e : RuntimeException) {throw ServiceException(e.cause)}
    }

    @Async @Transactional
    override fun updateComment(comment: Comment?) {
        if(comment === null) throw ServiceException(NullPointerException())
        try {
            comment.creationDate = LocalDate.now()
            commentDao.save(comment)
        } catch(e : RuntimeException) {throw ServiceException(e.cause)}
    }
}