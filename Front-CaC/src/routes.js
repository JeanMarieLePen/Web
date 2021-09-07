import Home from './components/Home'
import NewCustomer from './components/NewCustomer'
import NewManager from './components/CreateManager'
import NewDeliveryMan from './components/CreateDeliveryMan'
import SearchRestaurants from './components/SearchRestaurants'
import Login from './components/Login'
import Homepage from './components/Homepage'
import NewRestaurant from './components/CreateRestaurant'

export default[
    {
        path:'/',
        name:'Home',
        component:Home
    },
    {
        path:'/newCustomer',
        name:'NewCustomer',
        component:NewCustomer
    },
    {
        path:'/newManager',
        name:'NewManager',
        component:NewManager
    },
    {
        path:'/newDeliveryMan',
        name:'NewDeliveryMan',
        component:NewDeliveryMan
    },
    {
        path:'/searchRestaurants',
        name:'SearchRestaurants',
        component:SearchRestaurants
    },
    {
        path:'/login',
        name:'Login',
        component:NewCustomer
    },
    {
        path:'/about',
        name:'Homepage',
        component:Homepage
    },
    {
        path:'/newRestaurant',
        name:'NewRestaurant',
        component:NewRestaurant
    }
    

]