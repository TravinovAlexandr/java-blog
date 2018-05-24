package alex.com.blog.util.image.imagesave;

public class ScarlFactory implements ImageFactory{
    @Override
    public MultiPartConverter getConverter() {
        return new ScarlMultiPartConverter();
    }
}
