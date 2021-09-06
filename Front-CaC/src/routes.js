import Home from './components/Home'
import NewCustomer from './components/NewCustomer'
import NewManager from './components/CreateManager'
import NewDeliveryMan from './components/CreateDeliveryMan'
import SearchRestaurants from './components/SearchRestaurants'

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
    }
    

]