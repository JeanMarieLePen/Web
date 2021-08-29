package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import model.Administrator;

public class AdministratorDAO {

	private Map<String, Administrator> administrators = new HashMap<>();
	public AdministratorDAO() {
	}
	public AdministratorDAO(String contextPath) {
		loadAdministrators(contextPath);
	}
	public void loadAdministrators(String contextPath) {
		BufferedReader in = null;
		try {
			Gson gson = new Gson();
			File file = new File(contextPath + "administrators.json");
			in = new BufferedReader(new FileReader(file));
			ArrayList<Administrator> listOfAdministrators = new ArrayList<Administrator>();
			String line;
			while((line = in.readLine()) != null) {
				Administrator a = gson.fromJson(line, Administrator.class);
				listOfAdministrators.add(a);
				//dodajem i u mapu za svaki slucaj ako mapa bude zgodnija
				administrators.put(a.getUsername(), a);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if(in != null) {
				try {
					in.close();
				}
				catch(Exception e) {
					
				}
			}
		}
	}
	public Collection<Administrator> findAllAdministrators(){
		return administrators.values();
	}
	public Administrator findAdministrator(String username) {
		return administrators.containsKey(username) ? administrators.get(username) : null;
	}
	public Administrator save(Administrator administrator) {
		administrators.put(administrator.getUsername(), administrator);
		Gson gson = new Gson();
		String temp = gson.toJson(administrator);
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("administrators.json", true))){
			bw.append(temp);
			bw.append("\n");
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return administrator;
	}
	
}
