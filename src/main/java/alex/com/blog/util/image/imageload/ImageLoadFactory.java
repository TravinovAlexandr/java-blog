package alex.com.blog.util.image.imageload;

public class ImageLoadFactory {

    public ImageLoadResizeImpl getImageResize(final int ind) {

        if (ind == 0) return new ImageLoadResizeImpl();
        else throw new IllegalArgumentException();
    }
}
