package project.client;

import java.util.List;

import project.shared.Articles;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EntityServiceAsync {

	void deleteArticle(String id, AsyncCallback<Boolean> callback);

	void addArticle(String article, String autor, java.util.Date date, AsyncCallback<Boolean> callback);

	void editArticle(String id, String article, AsyncCallback<Boolean> callback);

	void getAllEntities(AsyncCallback<List<Articles>> callback);

	void addLike(String id, AsyncCallback<Integer> callback);

	void addDisLike(String id, AsyncCallback<Integer> callback);

	void getArticle(String id, AsyncCallback<String> callback);
	
}
