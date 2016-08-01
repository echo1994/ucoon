package com.cn.ucoon.pojo.wx.resp;

import java.util.List;

public class NewsMessage extends BaseMessage{

	private int ArticleCount;// ͼ����Ϣ��������Ϊ10������

	private List<Article> Articles;// ����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ��itemΪ��ͼ

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

}
