package alex.com.blog.util;

public interface Logger {

    void setLogger(String clazzName);
    void debug(String msg);
    void info(String msg);
}
