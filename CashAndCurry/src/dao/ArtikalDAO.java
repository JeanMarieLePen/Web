package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import model.Artikal;
import com.google.gson.Gson;

public class ArtikalDAO {
	private Map<String, Artikal> artikli = new HashMap<>();
	public ArtikalDAO() {
		
	}
	public ArtikalDAO(String contextPath) {
		loadArtikle(contextPath);
	}
	public void loadArtikle(String contextPath) {
		BufferedReader in = null;
		try {
			Gson gson = new Gson();
			File file = new File(contextPath + "artikli.json");
			in = new BufferedReader(new FileReader(file));
			ArrayList<Artikal> listOfArtikli = new ArrayList<Artikal>();
			String line;
			while((line = in.readLine()) != null) {
				Artikal a = gson.fromJson(line,  Artikal.class);
				listOfArtikli.add(a);
				artikli.put(a.getNaziv(), a);
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
	public Collection<Artikal> findAllArtikli(){
		return artikli.values();
	}
	public Artikal findArtikal(String nazivArtikla) {
		return artikli.containsKey(nazivArtikla) ? artikli.get(nazivArtikla) : null;
	}
	public Artikal save(Artikal artikal) {
		artikli.put(artikal.getNaziv(), artikal);
		Gson gson = new Gson();
		String temp = gson.toJson(artikal);
		try(BufferedWriter bw = new BufferedWriter(new FileWriter("artikli.json", true))){
			bw.append(temp);
			bw.append("\n");
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return artikal;
	}
}


