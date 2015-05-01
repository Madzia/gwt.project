package project.client;

import java.sql.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EntityServiceAsync {

	void deleteArticle(int id, AsyncCallback<Boolean> callback);

	void addArticle(String article, String autor, Date date,
			AsyncCallback<Boolean> callback);

	void editArticle(int id, String article, AsyncCallback<Boolean> callback);

	
	
}
