package project.shared;

import java.util.Date;

import com.google.gwt.user.client.Random;

public class Articles {
	private int id;
	private Date date;
	private String autor;
	private String article;
	private int like;
	private int dislike;
	
	public Articles(){	
	}
	
	public Articles(Date date, String autor, String article){
		this.id = Random.nextInt(Integer.MAX_VALUE);
		this.date = date;
		this.autor = autor;
		this.article = article;
		this.like = 0;
		this.dislike = 0;
	}
	public int getId(){
		return id;
	}
	
	public Date getDate(){
		return date;
	}
	
	public String getAutor(){
		return autor;
	}
	
	public String getArticle(){
		return article;
	}
	
	public int getLikes(){
		return like;
}
	
	public int getDislikes(){
		return dislike;
	}
	
	public void editedArticle(String article){
		this.article = article;
	}
	
	public void addLike(){
		this.like +=1;
	}
	
	public void addDisLike(){
		this.dislike +=1;
	}
}
