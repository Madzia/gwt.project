package project.shared;

import java.io.Serializable;
import java.util.Date;

public class Articles implements Serializable, Comparable<Articles>  {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date date;
	private String autor;
	private String article;
	private int like;
	private int dislike;
	
	public Articles(){	
	}
	
	public Articles(int id, Date date, String autor, String article){
		this.id = id;
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

	@Override
	public int compareTo(Articles o) {
		if(this.getDate().before(o.getDate())){
			return 1;
		}
		else if(this.getDate().after(o.getDate())){
			return -1;
		}else{
			return 0;
		}
	}
}
