package project.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class Gwt_project implements EntryPoint {

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	
	Button loginButton = new Button("Log In");
	Button logoutButton = new Button("Log Out");
	TextBox loginTextField = new TextBox();
	TextBox passwordTextField = new TextBox();
	Label loginName = new Label("");
	int num = 0;

	private void CreateLoginPanel(){
		RootPanel.get("loginTbHolder").add(loginTextField);
		RootPanel.get("passwdTbHolder").add(passwordTextField);
		RootPanel.get("signinBtnHolder").add(loginButton);
		DOM.getElementById("loginPanel").getStyle().setDisplay(Display.BLOCK);
		DOM.getElementById("siginErr").getStyle().setDisplay(Display.NONE);
		DOM.getElementById("loggedinPanel").getStyle().setDisplay(Display.NONE);
		

		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final String login = loginTextField.getText();
				String passwd = passwordTextField.getText();
				loginService.checkUser(login, passwd, new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						if(result){
							DOM.getElementById("loggedinPanel").getStyle().setDisplay(Display.BLOCK);
							DOM.getElementById("siginErr").getStyle().setDisplay(Display.NONE);
							DOM.getElementById("loginPanel").getStyle().setDisplay(Display.NONE);
							loginName.setText(login);
							RootPanel.get("userNameLblHolder").add(loginName);
							RootPanel.get("logoutBtnHolder").add(logoutButton);
							logoutButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									RootPanel.get("userNameLblHolder").remove(0);
									RootPanel.get("logoutBtnHolder").remove(0);
									CreateLoginPanel();
								}
							});
						}
						else {
							DOM.getElementById("siginErr").getStyle().setDisplay(Display.BLOCK);
						}
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						DOM.getElementById("siginErr").getStyle().setDisplay(Display.BLOCK);
						
					}
				});
		}
	});
	}
	
	public void onModuleLoad() {
		CreateLoginPanel();
	}
}
