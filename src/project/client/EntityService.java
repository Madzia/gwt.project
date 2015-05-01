package project.client;

import java.sql.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("login")
public interface EntityService extends RemoteService {
	
	Boolean deleteArticle(int id);
	Boolean addArticle(String article, String autor, Date date);
	Boolean editArticle(int id, String article);
}
