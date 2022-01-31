package dao;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import model.Customer;
import model.DeliveryMan;

public class DeliveryManDAO {

	private Map<String, DeliveryMan> deliverymen = new HashMap<>();
	private String contextPath;
	public DeliveryManDAO() {
		
	}
	
	public DeliveryManDAO(String contextPath) {
		this.contextPath = contextPath;
		loadDeliverymen(contextPath);
	}
	
	public void loadDeliverymen(String contextPath) {
		//ucitavanje svih dostavljaca iz baze(fajla deliverymen.json koji se nalazi u kontekstu)
		try {
			JsonReader reader = new JsonReader(new FileReader(contextPath + "/deliverymen.json"));
			Gson gson = new Gson();
			DeliveryMan[] tempDeliverymen = gson.fromJson(reader,  DeliveryMan[].class);
			for(DeliveryMan dm : tempDeliverymen) {
				this.deliverymen.put(dm.getUsername(), dm);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public int generateId(String naziv) {
		int generatedId = -1;
		if(deliverymen.containsKey(naziv)) {
			ArrayList<DeliveryMan> listaDelivery = (ArrayList<DeliveryMan>) this.deliverymen.values().stream().collect(Collectors.toList());
			List<Integer> listIds = new ArrayList<Integer>();
			
			for(DeliveryMan dm : listaDelivery) {
				listIds.add(dm.getId());
			}
			
			for(int i = 0; i < listIds.size() + 1; i++) {
				if(!listIds.contains(i)) {
					generatedId = i;
					break;
				}
			}
		}
		return generatedId;
	}
	
	//vraca sve dostavljace
	public Collection<DeliveryMan> findAllDeliveryMen(){
		return this.deliverymen.values();
	}
	//vraca dostavljaca sa konkretnim korisnickim imenom
	public DeliveryMan findDeliveryMan(String username) {
		return this.deliverymen.containsKey(username) ? this.deliverymen.get(username) : null;
	}
	//dodavanje novog dostavljaca i njegovo cuvanje u bazu
	public DeliveryMan addNewDeliveryMan(DeliveryMan deliveryMan) {
		if(!this.deliverymen.containsKey(deliveryMan.getUsername())) {
			this.deliverymen.put(deliveryMan.getUsername(), deliveryMan);
			this.deliverymen.get(deliveryMan.getUsername()).setId(this.generateId(deliveryMan.getUsername()));
			Gson gson = new Gson();
			//String temp = gson.toJson(this.deliverymen);
			
			Collection<DeliveryMan> tmp = this.deliverymen.values();
			String fileInput = gson.toJson(tmp);
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "deliverymen.json", false))){
				System.out.println("Upis novog dostavljaca u bazu.");
				bw.append(fileInput);
				bw.append("\n");
				bw.close();
				
			}catch(IOException e) {
				e.printStackTrace();
			}
			return deliveryMan;
		}
		return null;
	}
	
	//izmena postojeceg dostavljaca i smestanje u bazu
	public DeliveryMan updateDeliveryMan(DeliveryMan deliveryMan) {
		if(this.deliverymen.containsKey(deliveryMan.getUsername())) {
			DeliveryMan c = this.deliverymen.get(deliveryMan.getUsername());
			//menjam mu polja
			c.setName(deliveryMan.getName()); c.setLastname(deliveryMan.getLastname());
			c.setDateOfBirth(deliveryMan.getDateOfBirth()); c.setPassword(deliveryMan.getPassword());
			c.setGender(deliveryMan.getGender());
//			this.deliverymen.replace(deliveryMan.getUsername(), deliveryMan);
			Gson gson = new Gson();
			String temp = gson.toJson(this.deliverymen.values());
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "deliverymen.json", false))){
				bw.append(temp);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return deliveryMan;
		}
		return null;
	}
	
	//brisanje dostavljaca iz baze
	public DeliveryMan removeDeliveryMan(DeliveryMan deliveryMan) {
		if(this.deliverymen.containsKey(deliveryMan.getUsername())) {
			this.deliverymen.remove(deliveryMan.getUsername());
			Gson gson = new Gson();
			String temp = gson.toJson(this.deliverymen);
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(contextPath + "deliverymen.json", false))){
				bw.append(temp);
				bw.append("\n");
				bw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return deliveryMan;
		}
		return null;
	}
	public boolean checkPassword(String tempPassword, String username) {
		System.out.println("Provera sifri");
		if(deliverymen.containsKey(username)) {
			String tempSifra = deliverymen.get(username).getPassword();
			if(tempSifra.equals(tempPassword)) {
				return true;
			}
		}
		return false;
	}
	
}
