package project.client;

import com.google.gwt.i18n.shared.DateTimeFormat;

import project.shared.Articles;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Gwt_project implements EntryPoint {

	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	private final EntityServiceAsync entityService = GWT.create(EntityService.class);
	
	Button loginButton = new Button("Log In");
	Button logoutButton = new Button("Log Out");
	Button addArticleBtn = new Button("Add");
	Button clearArticleBtn = new Button("Clear");
	TextBox loginTextField = new TextBox();
	TextBox passwordTextField = new TextBox();
	Label loginName = new Label("");
	RichTextArea articleAdder = new RichTextArea();
	VerticalPanel vp = new VerticalPanel();
	HorizontalPanel hp = new HorizontalPanel();
	DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	Button likes = new Button();
	
	int num = 0;

	private void CreateLoginPanel(){
		RootPanel.get("loginTbHolder").add(loginTextField);
		RootPanel.get("passwdTbHolder").add(passwordTextField);
		RootPanel.get("signinBtnHolder").add(loginButton);
		DOM.getElementById("loginPanel").getStyle().setDisplay(Display.BLOCK);
		DOM.getElementById("siginErr").getStyle().setDisplay(Display.NONE);
		DOM.getElementById("loggedinPanel").getStyle().setDisplay(Display.NONE);
		DOM.getElementById("articlesHolder").getStyle().setDisplay(Display.NONE);
		

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
							DOM.getElementById("articlesHolder").getStyle().setDisplay(Display.BLOCK);
							loginName.setText(login);
							LoadArticles();
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
	
	private void CreateAddArticlesPanel(){
		
	//	hp.add(w);
		vp.add(articleAdder);
		RootPanel.get("addArticleHolder").add(vp);
		RootPanel.get("addArticleBtnHolder").add(clearArticleBtn);
		RootPanel.get("addArticleBtnHolder").add(addArticleBtn);
		clearArticleBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				articleAdder.setText("");
			}
		});
		addArticleBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addArticle(articleAdder.getText());
			}
		});
	}

	private void LoadArticles() {
		
		entityService.getAllEntities(new AsyncCallback<List<Articles>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(List<Articles> result) {
				Clear("allArticlesHolder");
				System.out.print(result.size());
				VerticalPanel vpAll = new VerticalPanel();
				for(Articles e : result){
					HorizontalPanel hp1 = new HorizontalPanel();
					hp1.add(new Label(e.getAutor() + " : "+ e.getDate()));
					Label idLabel = new Label(String.valueOf(e.getId()));
					idLabel.setVisible(false);
					hp1.add(idLabel);
					vpAll.add(hp1);
					HorizontalPanel hp2 = new HorizontalPanel();
					RichTextArea rta = new RichTextArea();
					rta.setText(e.getArticle());				
//[TODO]
//RICHTEXTAREA?
					hp2.add(rta);
					if (e.getAutor().equals(loginTextField.getText())){
						VerticalPanel vp1 = new VerticalPanel();
						vp1.add(new Button("Delete"));
						vp1.add(new Button("Edit"));
						vp1.add(new Button("OK"));
						hp2.add(vp1);
					}
					vpAll.add(hp2);
					HorizontalPanel hp3 = new HorizontalPanel();
					likes = new Button("Likes: "+e.getLikes());
					likes.setTitle(String.valueOf(e.getId()));
					likes.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							entityService.addLike( ((Button)event.getSource()).getTitle(), new AsyncCallback<Integer>() {
								@Override
								public void onFailure(Throwable caught) {						
								}

								@Override
								public void onSuccess(Integer result) {
									LoadArticles();
								}
							});
						}
					});
					hp3.add(likes);
					Button dislikes = new Button("Dislikes: "+e.getDislikes());
					dislikes.setTitle(String.valueOf(e.getId()));
					dislikes.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							entityService.addDisLike( ((Button)event.getSource()).getTitle(), new AsyncCallback<Integer>() {
								@Override
								public void onFailure(Throwable caught) {						
								}

								@Override
								public void onSuccess(Integer result) {
									LoadArticles();
								}
							});
						}
					});
					hp3.add(dislikes);
					vpAll.add(hp3);
				}
				RootPanel.get("allArticlesHolder").add(vpAll);
			}			
		});
	}

	private void Clear(String name) {
		while(RootPanel.get(name).getWidgetCount() > 0){
			RootPanel.get(name).remove(0);
		}
		
	}

	private void addArticle(String text) {
		entityService.addArticle(text, loginTextField.getText(), date, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Boolean result) {	
				articleAdder.setText("");
				LoadArticles();
			}
		});
	}
	
	public void onModuleLoad() {
		CreateLoginPanel();
		CreateAddArticlesPanel();	
	}
}
