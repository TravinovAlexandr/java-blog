package alex.com.blog.util.image.imageload

import alex.com.blog.domaine.Avatar
import alex.com.blog.exception.UtilException
import org.imgscalr.Scalr
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import org.apache.log4j.Logger


open class ImageLoadResizeImpl : ImageLoadResize {

    private val L : Logger = Logger.getLogger(ImageLoadResizeImpl::class.java.simpleName)

    override fun getDbAvatar(avatar: ByteArray?, size: Int): ByteArray {
        if (avatar === null) throw UtilException(NullPointerException())
        try {
            val originalImage = ImageIO.read(ByteArrayInputStream(avatar))
            val scaledImg = Scalr.resize(originalImage,
                    Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, size, Scalr.OP_ANTIALIAS)
            val baos = ByteArrayOutputStream()
            ImageIO.write(scaledImg, "png", baos)
            baos.flush()
            val imageBytes = baos.toByteArray()
            baos.close()

            return imageBytes
        } catch(e : RuntimeException) {
            L.debug("Can not Convert image", e)
            throw UtilException("Can not Convert image")
        }
    }

    override fun getDbAvatar(avatar: Avatar?, size : Int) : ByteArray {
        if (avatar === null || avatar.image === null) throw UtilException(NullPointerException())
        return getDbAvatar(avatar.image, size)
    }

}