package alex.com.blog.dto;

import alex.com.blog.domaine.Article;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArticlePageableLinkDto  implements Serializable {

    private static final long serialVersionUID = -5916392949051714151L;

    private String uid;
    private String header;
    private String contentInfo;

    public ArticlePageableLinkDto() {}

    public List<ArticlePageableLinkDto> getDtoList(final List<Article> article) {
       return article.stream().map(this::mapCompanion).collect(Collectors.toList());
    }

    private ArticlePageableLinkDto mapCompanion(final Article a) {
        ArticlePageableLinkDto apld = new ArticlePageableLinkDto();
        apld.setHeader(a.getHeader());
        apld.setUid(a.getUid());
        apld.setContentInfo(a.getContent().substring(0,20) + "...");
        return apld;
    }

    public String getUid() {
        return uid;
    }
    public String getHeader() {
        return header;
    }
    public String getContentInfo() {
        return contentInfo;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticlePageableLinkDto)) return false;
        ArticlePageableLinkDto that = (ArticlePageableLinkDto) o;
        return Objects.equals(uid, that.uid) &&
                Objects.equals(header, that.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, header);
    }
}
