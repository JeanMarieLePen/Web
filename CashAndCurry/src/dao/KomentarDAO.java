package dao;
import java.util.Map;

import com.google.gson.Gson;

import model.Customer;
import model.Komentar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class KomentarDAO {

	private Map<String, Komentar> comments = new HashMap<>();
	public KomentarDAO() {
		
	}
	public KomentarDAO(String contextPath) {
		loadComments(contextPath);
	}
	public void loadComments(String contextPath) {
		BufferedReader in = null;
		try {
			Gson gson = new Gson();
			File file = new File(contextPath + "komentari.json");
			in = new BufferedReader(new FileReader(file));
			ArrayList<Komentar> listOfComments = new ArrayList<Komentar>();
			String line;
			while((line = in.readLine()) != null) {
				Komentar k = gson.fromJson(line, Komentar.class);
				listOfComments.add(k);
				//dodajem i u mapu za svaki slucaj ako mapa bude zgodnija
				comments.put(k.getKupacKomentator(), k);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if(in!= null) {
				try {
					in.close();
				}catch(Exception e) {
					
				}
			}
		}
	}
	
	public Collection<Komentar> findAllComments(){
		return comments.values();
	}
	public Komentar findKomentar(String username) {
		return comments.containsKey(username) ? comments.get(username) : null;	
	}
	public Komentar save(Komentar comment) {
		comments.put(comment.getKupacKomentator(), comment);
		Gson gson = new Gson();
		String temp = gson.toJson(comment);
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("comments.json", true))){
			bw.append(temp);
			bw.append("\n");
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return comment;
	}
	
}
