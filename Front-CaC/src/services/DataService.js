import axios from "axios";


const API_URL = "http://localhost:8080/WebProjekat2021/rest";

// if (JSON.parse(localStorage.getItem('token')) != null){
//     const token = JSON.parse(localStorage.getItem('token'));
//     axios.defaults.headers.common['Authorization'] = 'Bearer ' + token.accesToken;
// }

class DataService{

    //Customer
    getAllCustomers(){
        return axios.get(`${API_URL}/customers/`)
    }
    getCustomer(username){
        
        return axios.get(`${API_URL}/customers/${username}`);
    }
    addCustomer(customer){
        return axios.post(`${API_URL}/customers/`, customer);
    }
    updateCustomer(customer){
        return axios.put(`${API_URL}/customers/`, customer);
    }
    getQuitters(){
        return axios.get(`${API_URL}/customers/quitters/`);
    }

    //Manager
    addManager(manager){
        console.log("DataService:addManager");
        return axios.post(`${API_URL}/managers/`, manager);
    }
    getFreeManagers(){
        return axios.get(`${API_URL}/managers/free/`);
    }
    getAllManagers(){
        return axios.get(`${API_URL}/managers/`);
    }
    getManager(id){
        return axios.get(`${API_URL}/managers/${id}`);
    }
    updateManager(manager){
        return axios.put(`${API_URL}/managers/`, manager);
    }
    updateManagerRestoran(manager){
        return axios.put(`${API_URL}/managers/dodelaRestorana`, manager);
    }

    //DeliveryMan
    addDeliveryMan(deliveryMan){
        console.log("DataService:addDeliveryMan");
        return axios.post(`${API_URL}/deliverymen/`, deliveryMan);
    }
    getDeliveryMan(id){
        return axios.get(`${API_URL}/deliverymen/${id}`);
    }
    getAllDeliveryMen(){
        return axios.get(`${API_URL}/deliverymen/`);
    }
    updateDeliveryMan(deliveryMan){
        return axios.put(`${API_URL}/deliverymen/`, deliveryMan);
    }

    //Restaurants
    getAllRestaurants(){
        return axios.get(`${API_URL}/restaurants/`)
    }
    getAllRestaurantsWithNoManager(){
        return axios.get(`${API_URL}/restaurants/noManager`);
    }
    addRestaurant(restaurant){
        return axios.post(`${API_URL}/restaurants/`, restaurant);
    }
    searchRestaurant(zahtev){
        return axios.get(`${API_URL}/restaurants/filtered/${zahtev}`);
    }
    getRestaurantsWithManager(){
        return axios.get(`${API_URL}/restaurants/hasManager`);
    }
    getRestaurant(id){
        return axios.get(`${API_URL}/restaurants/${id}`);
    }
    addProduct(id, product){
        return axios.put(`${API_URL}/restaurants/${id}`, product)
    }
    getProducts(id){
        return axios.get(`${API_URL}/restaurants/yours/${id}/`)
    }
    getRestaurantByManager(id){
        return axios.get(`${API_URL}/restaurants/bymanager/${id}`);
    }


    //kreiranje komentara i cuvanje u bazu
    addComment(comment){
        return axios.post(`${API_URL}/comments/`, comment);
    }
    getAllCommentsByRestaurantId(idRestorana){
        return axios.get(`${API_URL}/comments/byRestoran/${idRestorana}`);
    }
    getAllCommentsById(idManager){
        return axios.get(`${API_URL}/comments/${idManager}`);
    }
    getAllUnmoderatedComments(idManager){
        return axios.get(`${API_URL}/comments/unmoderated/${idManager}`);
    }
    sendModeratedCommentList(idManagera, komentari){
        return axios.post(`${API_URL}/comments/moderated/${idManagera}`, komentari);
    }



    //admin
    getAdmin(id){
        return axios.get(`${API_URL}/administrators/${id}`);
    }
    updateAdmin(admin){
        return axios.put(`${API_URL}/administrators`, admin);
    }
    suspendUser(username){
        return axios.get(`${API_URL}/bannedUsers/ban/${username}`)
    }
    unsuspendUser(username){
        return axios.get(`${API_URL}/bannedUsers/unban/${username}`)
    }

    //Order
    addOrder(order){
        return axios.post(`${API_URL}/orders/`, order);
    }
    getOrderById(id){
        return axios.get(`${API_URL}/orders/byOrderId/${id}`);
    }

    //porudzbine kupac
    getSvojePorudzbine(username){
        return axios.get(`${API_URL}/orders/${username}`);
    }
    getNedostavljenePorudzbine(id){
        return axios.get(`${API_URL}/orders/notDelivered/${id}`);
    }
    
    //porudzbine manager
    getSvePorudzbine(name){
        return axios.get(`${API_URL}/orders/byRestaurantName/${name}`);
    }

    //porudzbine dostavljac
    getSveCekanje(){
        return axios.get(`${API_URL}/orders/noDeliveryMan`);
    }
    getSvojeNedostavljenePorudzbine(id){
        return axios.get(`${API_URL}/orders/notYetDelivered/${id}`);
    }
    getZaduzenePorudzbine(id){
        return axios.get(`${API_URL}/orders/deliveryMan/${id}`);
    }

    searchPorudzbine(searchedQuery){
        return axios.get(`${API_URL}/orders/filtered/${searchedQuery}`);
    }

    getAllPorudzbine(){
        return axios.get(`${API_URL}/ads/img`);
    }

    cancelOrder(orderId){
        return axios.put(`${API_URL}/orders/cancel/${orderId}`);
    }

    takeOrder(order){
        return axios.put(`${API_URL}/orders/`, order);
    }
}

export default new DataService();