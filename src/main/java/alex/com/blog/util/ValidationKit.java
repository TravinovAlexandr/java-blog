package alex.com.blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationKit {

    public boolean containsIllegals(final String toExamine) {
        Pattern pattern = Pattern.compile("[~#*+%{}<>\\[\\]|\"\\_^]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }

    public boolean containsDog(final String toExamine) {
        Pattern pattern = Pattern.compile("[@]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }

    public boolean isUID(final String uid) {
        final Pattern pattern = Pattern.compile("^[0-9A-Fa-f]{8}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{4}\\-[0-9A-Fa-f]{12}$");
        final Matcher matcher = pattern.matcher(uid);
        return matcher.find();
    }
}
