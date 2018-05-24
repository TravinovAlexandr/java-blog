package alex.com.blog.controller

import alex.com.blog.annotations.Log
import alex.com.blog.domaine.Article
import alex.com.blog.domaine.Comment
import alex.com.blog.dto.CommentDto
import alex.com.blog.exception.DtoException
import alex.com.blog.exception.ServiceException
import alex.com.blog.service.ArticleService
import alex.com.blog.service.CommentService
import alex.com.blog.service.UserService
import alex.com.blog.util.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.core.task.TaskExecutor
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Controller
open class CommentController  @Autowired constructor(private var userService: UserService,
                                                private var articleService: ArticleService,
                                                private var commentService: CommentService,
                                                private var taskExecutor: TaskExecutor,
                                                private val cMessageSource: MessageSource) {

    @Log
    private val LOGGER: Logger? = null


    @PostMapping("/addComment/{uid}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun addComment(@ModelAttribute @Valid commentForm : Comment?, brResult : BindingResult, request : HttpServletRequest,
                   @PathVariable(required = false) articleUid : String?) : ResponseEntity<Any>? {

        if(articleUid === null || articleUid.length != 12)
            return ResponseEntity(cMessageSource.getMessage("article.exist.error",null, request.locale), HttpStatus.BAD_REQUEST)

        if (request.userPrincipal?.name === null)
            return ResponseEntity(cMessageSource.getMessage("authorization.error",null, request.locale), HttpStatus.BAD_REQUEST)
        if(brResult.hasErrors())
            return ResponseEntity(cMessageSource.getMessage("comment.error",null, request.locale), HttpStatus.BAD_REQUEST)
        try {
            val article = articleService.findByUid(articleUid)
            val user = userService.findByNick(request.userPrincipal.name)
            taskExecutor.execute({
                commentForm!!.article = article
                commentForm.user = user
                commentService.addComment(commentForm)
            })
        } catch (e : ServiceException) {
            LOGGER!!.debug(e.message)
            return ResponseEntity(cMessageSource.getMessage("server.error",null, request.locale), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(cMessageSource.getMessage("comment.successes",null, request.locale), HttpStatus.OK)
    }

    @PostMapping("/getAllComments/{articleUuid}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun getAllArticleComments(@PathVariable(required = false) articleUuid : String?, request: HttpServletRequest) : ResponseEntity<Any>? {

        if(articleUuid === null || articleUuid.length != 12)
            return ResponseEntity(cMessageSource.getMessage("request.error",null, request.locale), HttpStatus.BAD_REQUEST)
        return try {
            val article : Article? = articleService.findByUid(articleUuid) ?:
            return ResponseEntity(cMessageSource.getMessage("article.exist.error",null, request.locale), HttpStatus.NOT_FOUND)
            val commentsDto : List<CommentDto> = article!!.comments.map { CommentDto().convertToDto(it) }
            ResponseEntity(commentsDto, HttpStatus.OK)
        } catch (e : ServiceException) {
            LOGGER!!.debug(e.message)
            ResponseEntity(cMessageSource.getMessage("server.error",null, request.locale), HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e : DtoException) {
            LOGGER!!.debug(e.message)
            ResponseEntity(cMessageSource.getMessage("article.exist.error",null, request.locale), HttpStatus.NOT_FOUND)
        } catch (e : RuntimeException) {
            LOGGER!!.debug(e.message)
            ResponseEntity(cMessageSource.getMessage("server.error",null, request.locale), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}