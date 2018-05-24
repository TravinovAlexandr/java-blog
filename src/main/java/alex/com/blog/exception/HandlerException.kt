package alex.com.blog.exception

class HandlerException : RuntimeException{
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String, cause: Throwable?): super(message, cause)
    constructor(cause: Throwable?): super(cause)
}