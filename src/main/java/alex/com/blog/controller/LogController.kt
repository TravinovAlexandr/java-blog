package alex.com.blog.controller

import alex.com.blog.annotations.Log
import alex.com.blog.exception.HandlerException
import alex.com.blog.handler.LogOutHandler
import alex.com.blog.util.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.security.core.Authentication
import org.springframework.security.web.WebAttributes
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
open class LogController @Autowired constructor(private val cMessageSource : MessageSource,
                                           private val logOutHandler : LogOutHandler) {

    @Log
    private val LOGGER : Logger? = null;

    @GetMapping("/")
    fun indexPage(): String {
        return "index"
    }

    @GetMapping("/login")
    fun login(model: Model, @RequestParam(required = false) error: String?,
              request: HttpServletRequest): String {

        val message = StringBuilder()
        if (error != null) {
            if (request.session.getAttribute(WebAttributes.ACCESS_DENIED_403) !== null) {
                message.append(request.session.getAttribute(WebAttributes.ACCESS_DENIED_403))
            } else
                message.append(cMessageSource.getMessage("data.error", null, request.locale))
                model.addAttribute("validLoginMessage", message)
                return "login"
            }
        model.addAttribute("validLoginMessage", message)
        return "login"
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        try {
            logOutHandler.logout(request, response, authentication)
        } catch (e: HandlerException) {
            try {
                response.sendError(405)
            } catch (e1: IOException) {
                LOGGER!!.debug(e1.message)
            }

        }

    }
}