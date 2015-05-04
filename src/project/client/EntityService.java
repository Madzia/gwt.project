package project.client;

import java.util.List;
import project.shared.Articles;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("entity")
public interface EntityService extends RemoteService {
	
	Boolean deleteArticle(String id);
	
	boolean addArticle(String article, String autor, java.util.Date date);
	
	Boolean editArticle(String id, String article);
	
	List<Articles> getAllEntities();
	
	String getArticle(String id);

	int addLike(String id);
	
	int addDisLike(String id);
	
}
