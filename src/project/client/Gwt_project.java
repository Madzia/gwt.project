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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
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
	TextArea articleAdder = new TextArea();
	VerticalPanel vp;
	HorizontalPanel hp = new HorizontalPanel();
	DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	Button likes = new Button();
	HorizontalPanel all = new HorizontalPanel();
	VerticalPanel vpAll = new VerticalPanel();
	
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
							CreateAddArticlesPanel();
							RootPanel.get("userNameLblHolder").add(loginName);
							RootPanel.get("logoutBtnHolder").add(logoutButton);
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
		all.clear();
		Clear("articlesHolder");
		all = new HorizontalPanel();
		all.setSpacing(140);
		all.add(vp);
		LoadArticles();
		RootPanel.get("articlesHolder").add(all);
	}

	private void LoadArticles() {
		
		entityService.getAllEntities(new AsyncCallback<List<Articles>>() {

			@Override
			public void onFailure(Throwable caught) {
			}
			
			@Override
			public void onSuccess(List<Articles> result) {
				vpAll.clear();
				vpAll.setSpacing(20);
				
				for(Articles e : result) {
					VerticalPanel vp2 = new VerticalPanel();
					HorizontalPanel hp1 = new HorizontalPanel();
					hp1.add(new Label(e.getAutor() + " : "+ e.getDate()));
					Label idLabel = new Label(String.valueOf(e.getId()));
					idLabel.setVisible(false);
					hp1.add(idLabel);
					vp2.add(hp1);
					HorizontalPanel hp2 = new HorizontalPanel();
					String art = e.getArticle();
					TextArea rotb = new TextArea();
					rotb.setReadOnly(true);
					rotb.setCharacterWidth(35);
					rotb.getElement().setAttribute("wrap", "off");
					rotb.setText(art);
				    rotb.setVisibleLines((art.length() - art.replace("\n", "").length())+1);
				    rotb.getElement().getStyle().setColor("#000000");
					hp2.add(rotb);
					if (e.getAutor().equals(loginTextField.getText())){
						VerticalPanel vp1 = new VerticalPanel();
						Button delete = new Button("Delete");
						delete.setTitle(String.valueOf(e.getId()));
						delete.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								entityService.deleteArticle( ((Button)event.getSource()).getTitle(), new AsyncCallback<Boolean>() {
									@Override
									public void onFailure(Throwable caught) {						
									}

									@Override
									public void onSuccess(Boolean result) {
										CreateAddArticlesPanel();
									}
								});
							}
						});
						
						vp1.add(delete);
						Button edit = new Button("Edit");
						edit.setTitle(String.valueOf(e.getId()));
						edit.addClickHandler(new ClickHandler() {
							public void onClick(ClickEvent event) {
								articleAdder.setTitle(((Button)event.getSource()).getTitle());
								entityService.getArticle(articleAdder.getTitle(), new AsyncCallback<String>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void onSuccess(String result) {
										articleAdder.setText(result);
										
									}
								});
								}
						});
						vp1.add(edit);
						hp2.add(vp1);
					}
					vp2.add(hp2);
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
									CreateAddArticlesPanel();
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
									CreateAddArticlesPanel();
								}
							});
						}
					});
					hp3.add(dislikes);
					vp2.add(hp3);
					vpAll.add(vp2);
				}
				all.add(vpAll);
			}			
		});
	}

	private void Clear(String name) {
		while(RootPanel.get(name).getWidgetCount() > 0){
			RootPanel.get(name).remove(0);
		}
		articleAdder.setTitle("");
	}

	private void addArticle(String text) {
		date = new Date();
		entityService.addArticle(text, loginTextField.getText(), date, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Boolean result) {	
				articleAdder.setText("");
			}
		});
	}
	
	public void onModuleLoad() {
		buttonscontrol();
		CreateLoginPanel();
	}

	private void buttonscontrol() {

		articleAdder.setCharacterWidth(35);
		articleAdder.setVisibleLines(15);
		
		vp = new VerticalPanel();
		VerticalPanel vp3 = new VerticalPanel();
		vp.setSpacing(30);
		vp3.add(articleAdder);
		HorizontalPanel btns = new HorizontalPanel();
		btns.add(clearArticleBtn);
		btns.add(addArticleBtn);
		vp3.add(btns);
		vp.add(vp3);
		
		clearArticleBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				articleAdder.setText("");
				articleAdder.setTitle("");
			}
		});
		addArticleBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (articleAdder.getTitle().equals("")) {
					addArticle(articleAdder.getText());
					CreateAddArticlesPanel();
				}
				else
					entityService.editArticle(articleAdder.getTitle(), articleAdder.getText(), new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {						
					}

					@Override
					public void onSuccess(Boolean result) {
						articleAdder.setText("");
						articleAdder.setTitle("");
						CreateAddArticlesPanel();
					}
				});
			}
		});
		logoutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Clear("userNameLblHolder");
				Clear("logoutBtnHolder");
				Clear("articlesHolder");
				all.clear();
				CreateLoginPanel();
			}
		});
		
	}
}
