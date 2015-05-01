package project.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Gwt_project implements EntryPoint {

	Button helloButton = new Button("Tak, to jest przycisk");
	Label nameField = new Label("");
	int num = 0;

	public void onModuleLoad() {
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(helloButton);

		helloButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				nameField.setText("Przycisk został użyty " + num + " razy.");
				num++;
			}
		});
	}
}
