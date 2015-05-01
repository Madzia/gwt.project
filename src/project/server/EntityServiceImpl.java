package project.server;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import project.client.EntityService;
import project.shared.Articles;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EntityServiceImpl extends RemoteServiceServlet implements EntityService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Articles> articlesDb = new ArrayList<Articles>();

	public EntityServiceImpl(){
	}
	
	public Boolean deleteArticle (int id){
		for(Articles e : articlesDb){
			if ( e.getId() == id ){
				articlesDb.remove(e);
				return true;
			}
		}
		return false;
	}
	
	public Boolean addArticle (String article, String autor, Date date){
		Articles newItem = new Articles(date, autor, article);
		articlesDb.add(newItem);
		return true;
	}

	
	public Boolean editArticle (int id, String article){
		for(Articles e : articlesDb){
			if ( e.getId() == id ){
				e.editedArticle(article);
				return true;
			}
		}
		return false;
	}

}