package project.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	public Boolean deleteArticle (String id){
		for(Articles e : articlesDb){
			if ( String.valueOf(e.getId()).equals(id )){
				articlesDb.remove(e);
				return true;
			}
		}
		return false;
	}
	
	public boolean addArticle (String article, String autor, java.util.Date date){
		Random rnd = new Random();
		Articles newItem = new Articles(rnd.nextInt(), date, autor, article);
		articlesDb.add(0, newItem);
		return true;
	}

	
	public Boolean editArticle (String id, String article){
		for(Articles e : articlesDb){
			if ( String.valueOf(e.getId()).equals(id )){
				e.editedArticle(article);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Articles> getAllEntities() {
		return articlesDb;
	}

	@Override
	public int addLike(String id) {
		for(Articles e : articlesDb){
			if ( String.valueOf(e.getId()).equals(id) ){
				e.addLike();
				return e.getLikes();
			}
		}
		return 0;
	}

	@Override
	public int addDisLike(String id) {
		for(Articles e : articlesDb){
			if ( String.valueOf(e.getId()).equals(id) ){
				e.addDisLike();
				return e.getDislikes();
			}
		}
		return 0;
	}

	@Override
	public String getArticle(String id) {
		for(Articles e : articlesDb){
			if ( String.valueOf(e.getId()).equals(id) ){
				return e.getArticle();
		}
	}
	return "";
	}

}