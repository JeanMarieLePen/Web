import Home from './components/Home'
import Registration from './components/Registration'
import NewManager from './components/CreateManager'
import NewDeliveryMan from './components/CreateDeliveryMan'
import SearchRestaurants from './components/SearchRestaurants'
import NewRestaurant from './components/CreateRestaurant'
import Login from './components/Login'
import Homepage from './components/Homepage'
import UserProfile from './components/UserProfile'
import UserProfileUpdate from './components/UserProfileUpdate'
import About from './components/About'
import CreateProduct from './components/CreateProduct'


export default[
    {
        path:'/',
        name:'Home',
        component:Home
    },
    {
        path:'/registration',
        name:'Registration',
        component:Registration
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
        component:Login
    },
    // {
    //     path:'/about',
    //     name:'About',
    //     component:About
    // },
    {
        path:'/newRestaurant',
        name:'NewRestaurant',
        component:NewRestaurant
    },
    {
        path:'/home',
        name:'Homepage',
        component:Homepage
    }
    ,
    {
        path:'/profile',
        name:'UserProfile',
        component:UserProfile
    },
    {
        path:'/profile/:id/update',
        name:'UserProfileUpdate',
        component:UserProfileUpdate
    },
    {
        path:'/about',
        name:'CreateProduct',
        component:CreateProduct
    }
    

]