import axios from "axios";


const API_URL = "http://localhost:8080/WebProjekat2021/rest";

if (JSON.parse(localStorage.getItem('token')) != null){
    const token = JSON.parse(localStorage.getItem('token'));
    axios.defaults.headers.common['Authorization'] = 'Bearer ' + token.accesToken;
}

class DataService{

    //Customer
    getAllCustomers(){
        return axios.get(`${API_URL}/customers/`)
    }
    getCustomer(username){
        
        return axios.get(`${API_URL}/customers/${username}`);
    }
    addCustomer(customer){
        console.log("AAAAA")
        return axios.post(`${API_URL}/customers/`, customer);
    }

    //Manager
    addManager(manager){
        console.log("DataService:addManager");
        return axios.post(`${API_URL}/managers/`, manager);
    }
    getFreeManagers(){
        return axios.get(`${API_URL}/managers/free`);
    }
    getAllManagers(){
        return axios.get(`${API_URL}/managers/`);
    }


    //DeliveryMan
    addDeliveryMan(deliveryMan){
        console.log("DataService:addDeliveryMan");
        return axios.post(`${API_URL}/deliverymen/`, deliveryMan);
    }

    //Restaurants
    getAllRestaurants(){
        return axios.get(`${API_URL}/restaurants/`)
    }
    getAllRestaurantsWithNoManager(){
        return axios.get(`${API_URL}/restaurants/noManager`);
    }
    addRestaurant(restaurant){
        return axios.post(`${API_URL}/restaurants/`, restaurant)
    }
    searchRestaurant(restaurant){
        return axios.get(`${API_URL}/restaurants`, restaurant)
    }
}

export default new DataService();