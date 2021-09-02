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


}

export default new DataService();