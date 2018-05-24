package alex.com.blog.util.image.imagesave;

import alex.com.blog.exception.UtilException;
import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ScarlMultiPartConverter implements MultiPartConverter{

    @Override
    public byte[] convert(final Object o, final int size) {

        if(o == null) throw new UtilException(new NullPointerException());
        if(!(o instanceof MultipartFile)) throw new UtilException(new ClassCastException());

        final MultipartFile multipartFile = (MultipartFile) o;
            try {
                final BufferedImage originalImage = ImageIO.read(multipartFile.getInputStream());
                final BufferedImage scaledImg = Scalr.resize(originalImage,
                        Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC, size, Scalr.OP_ANTIALIAS);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(scaledImg, "png", baos);
                baos.flush();
                byte[] imageBytes = baos.toByteArray();
                baos.close();
                return imageBytes;
            } catch (IOException e) {
                throw new UtilException(e);
            }
    }
}
