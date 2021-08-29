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

import model.Restoran;

public class RestoranDAO {
		private Map<String, Restoran> restorani = new HashMap<>();
		
		public RestoranDAO()
		{
			
		}
		
		public RestoranDAO(String contextPath)
		{
			loadRestorani(contextPath);
		}
		
		public void loadRestorani(String contextPath)
		{
			BufferedReader in = null;
			try
			{
				Gson gson = new Gson();
				File file = new File(contextPath + "restorani.json");
				in = new BufferedReader(new FileReader(file));
				ArrayList<Restoran> listOfRestorani = new ArrayList<Restoran>();
				String line;
				while((line = in.readLine()) != null)
				{
					Restoran r = gson.fromJson(line, Restoran.class);
					listOfRestorani.add(r);
					restorani.put(r.getName(), r);
				}
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				if(in != null) {
					try {
						in.close();
					}
					catch(Exception e) {
						
					}
				}
			}
		}
		public Collection<Restoran> findAllRestorani()
		{
			return restorani.values();
		}
		public Restoran findRestoran(String name)
		{
			return restorani.containsKey(name) ? restorani.get(name) : null;
		}
		
		public Restoran save(Restoran restoran)
		{
			restorani.put(restoran.getName(), restoran); // put ili post?
			Gson gson = new Gson();
			String temp = gson.toJson(restoran);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter("restorani.json",true))) {
				System.out.println("Upis novog restorana u bazu.");
				bw.append(temp);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();				
			}
			return restoran;
		}
}
