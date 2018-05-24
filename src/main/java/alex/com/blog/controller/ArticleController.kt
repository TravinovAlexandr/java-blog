package alex.com.blog.controller

import alex.com.blog.annotations.Log
import alex.com.blog.domaine.Article
import alex.com.blog.domaine.User
import alex.com.blog.dto.*
import alex.com.blog.exception.ServiceException
import alex.com.blog.exception.UtilException
import alex.com.blog.service.ArticleService
import alex.com.blog.service.UserService
import alex.com.blog.util.Logger
import alex.com.blog.util.UidGenerator
import alex.com.blog.util.image.imageload.ImageLoadResize
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces

@Controller
open class ArticleController @Autowired constructor(private var userService: UserService,
                                                    private var articleService: ArticleService,
                                                    private var imageLoadResize: ImageLoadResize,
                                                    private val cMessageSource: MessageSource,
                                                    private val articleUid : UidGenerator) {

    @Log
    private val LOGGER: Logger? = null

    @GetMapping("/article")
    fun getIndexPage() : String {
        return "index"
    }

    @GetMapping("/article/{uid}")
    fun getArticlePage(@PathVariable(required = false) uid : String?) : String {
        return "article"
    }

    @PostMapping("/getArticle/{uid}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody fun getArticle(@PathVariable(required = false) uid : String?, request: HttpServletRequest) :ResponseEntity<Any>? {

        if(uid === null) return ResponseEntity(cMessageSource.getMessage("request.error",null, request.locale), HttpStatus.BAD_REQUEST)
        try {
            if(uid.length == 12) {
                val article: Article = articleService.findByUid(uid)
                        ?: return ResponseEntity(cMessageSource.getMessage("article.exist.error",null, request.locale), HttpStatus.NOT_FOUND)
                val user: User = article.user
                val avatar: ByteArray = if (user.avatars.size > 1) imageLoadResize.getDbAvatar(user.avatars.first { a -> a.id != 1L }, 100)
                else imageLoadResize.getDbAvatar(user.avatars.first { a -> a.id == 1L }, 100)
                val articleDto = ArticleWithUserAttachmentDto().convertToDto(article, CommonAccountUserDto().convertToDto(user), avatar)
                return ResponseEntity(articleDto, HttpStatus.OK)
            }
        } catch(e : ServiceException) {
            LOGGER!!.debug(e.message)
            return ResponseEntity(cMessageSource.getMessage("server.error",null, request.locale), HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e : UtilException) {
            LOGGER!!.debug(e.message)
            return ResponseEntity(cMessageSource.getMessage("server.error",null, request.locale), HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return ResponseEntity(cMessageSource.getMessage("request.error",null, request.locale), HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/getLastAddedArticles", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody fun getLastAddedArticles(request : HttpServletRequest) : ResponseEntity<Any> {
        return try {
            val articles : MutableList<ArticlePageableLinkDto>
                    = ArticlePageableLinkDto().getDtoList(articleService.findArticlesInRange(20))
            ResponseEntity(articles, HttpStatus.OK)
        } catch (e : ServiceException) {
            LOGGER!!.debug(e.message)
            ResponseEntity(cMessageSource.getMessage("server.error",null, request.locale), HttpStatus.INTERNAL_SERVER_ERROR)
        } catch (e : RuntimeException) {
            LOGGER!!.debug(e.message)
            ResponseEntity(cMessageSource.getMessage("server.error",null, request.locale), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/addArticle")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody fun addArticle(@ModelAttribute @Valid articleForm: ArticleValidDto, br: BindingResult,
                   request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any> {
        if (br.hasErrors()) {
            return ResponseEntity(cMessageSource.getMessage("article.save.error", null, request.locale), HttpStatus.BAD_REQUEST)
        }
        return try {
            val user = userService.findByNick(request.userPrincipal.name)
            articleForm.user = user
            articleForm.uid = articleUid.generateUid(20)
            articleService.addArticle(articleForm)
            ResponseEntity(cMessageSource.getMessage("article.successes", null, request.locale), HttpStatus.OK)
        } catch (e: ServiceException) {
            LOGGER!!.debug(e.message)
            ResponseEntity(cMessageSource.getMessage("article.error", null, request.locale), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/getPageableArticleLinks", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody fun getPageableArticles(request: HttpServletRequest): ResponseEntity<Any> {
        try {
            val user = userService.findByNick(request.userPrincipal.name)
                    ?: return ResponseEntity(cMessageSource.getMessage("authorization.error", null, request.locale), HttpStatus.UNAUTHORIZED)
            val count = Integer.valueOf(request.getParameter("num"))
            val pageRequest = PageRequest(count, 5)
            val dto = ArticlePageableLinkDto()
                    .getDtoList(articleService.getPageableArticles(user, pageRequest))
            return ResponseEntity(dto, HttpStatus.OK)
        } catch (e: ServiceException) {
            LOGGER!!.debug(e.message)
            return ResponseEntity(cMessageSource.getMessage("server.error", null, request.locale), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}