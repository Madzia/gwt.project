package project.server;

import java.util.ArrayList;
import java.util.List;

import project.client.LoginService;
import project.shared.Users;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7310593766004180849L;
	private List<Users> usersDb = new ArrayList<Users>();

	public LoginServiceImpl(){
			usersDb.add(new Users("admin", "admin"));
			usersDb.add(new Users("admin2", "admin2"));
			usersDb.add(new Users("admin3", "admin3"));
			System.out.println(usersDb.size());
	}

	public Boolean checkUser(String login, String password) {

		for(Users bu : usersDb){
			if ( bu.getLogin().equals(login) && bu.getPassword().equals(password)){
				return true;
			}
		}
		return false;
	}
	
	

}