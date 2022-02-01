package dao;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import model.BannedUser;
import model.Customer;

public class BannedUserDAO {
	private Map<String, BannedUser> bannedUsers = new HashMap<>();
	private String contextPath;
	
	public BannedUserDAO(String contextPath) {
		this.contextPath = contextPath;
		loadBannedUsers(contextPath);
	}
	
	public void loadBannedUsers(String contextPath) {
		try {
			JsonReader reader = new JsonReader(new FileReader(contextPath + "/bannedUsers.json"));
			Gson gson = new Gson();
			BannedUser[] tempBannedUsers = gson.fromJson(reader, BannedUser[].class);
			for(BannedUser bu : tempBannedUsers) {
				bannedUsers.put(bu.getUsername(), bu);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public BannedUser banUser(String username) {
		// TODO Auto-generated method stub
		if(!this.bannedUsers.containsKey(username)) {
			BannedUser bu = new BannedUser(username, 30);
			bannedUsers.put(bu.getUsername(), bu);
			Gson gson = new Gson();
			Collection<BannedUser> tmp = this.bannedUsers.values();
			String fileInput = gson.toJson(tmp);
			
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "bannedUsers.json", false))){
				System.out.println("Banovan korisnik " + username);
				bw.append(fileInput);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return bu;
		}
		return null;
	}
	public boolean unbanUser(String username) {
		if(this.bannedUsers.containsKey(username)) {
			this.bannedUsers.remove(username);
			Gson gson = new Gson();
			Collection<BannedUser> tmp = this.bannedUsers.values();
			String fileInput = gson.toJson(tmp);
			
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "bannedUsers.json", false))){
				System.out.println("Banovan korisnik " + username);
				bw.append(fileInput);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return true; 
		}
		return false;
	}
	
	
}
