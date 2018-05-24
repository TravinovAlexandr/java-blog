package alex.com.blog.util.image.imageload;

import alex.com.blog.domaine.Avatar;

public interface ImageLoadResize {
    byte[] getDbAvatar(Avatar avatar, int size);
    byte[] getDbAvatar(byte[] avatar, int size);

}
