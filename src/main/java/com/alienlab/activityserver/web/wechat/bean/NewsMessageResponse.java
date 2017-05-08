package com.alienlab.activityserver.web.wechat.bean;

import javax.xml.bind.annotation.*;

/**
 * Created by 橘 on 2016/12/23.
 */
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.NONE)
public class NewsMessageResponse extends MessageResponse {
    public NewsMessageResponse() {
        super();
    }

    @XmlElement
    private Integer ArticleCount;
    @XmlElementWrapper(name = "Articles")
    @XmlElement(name = "item")
    private Article[] Articles;

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public Article[] getArticles() {
        return Articles;
    }

    public void setArticles(Article[] articles) {
        Articles = articles;
    }

}
