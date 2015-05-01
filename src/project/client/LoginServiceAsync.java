package project.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void checkUser(String login, String passwd, AsyncCallback<Boolean> callback);

}
