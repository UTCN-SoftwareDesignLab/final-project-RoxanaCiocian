import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import { auth as store } from "../store/auth.module";
import Login from "../views/Login";
import FlightList from "../views/FlightList";
import BookingList from "../views/BookingList";
import CustomerList from "../views/CustomerList";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "Flights" });
      }
    },
  },
  {
    path: "/flights",
    name: "flights",
    component: FlightList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Bookings" });
      }
    },
  },
  {
    path: "/booking",
    name: "Bookings",
    component: BookingList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isEmployee) {
        next();
      } else {
        next({ name: "Customers" });
      }
    },
  },
  {
    path: "/booking/customer",
    name: "Customers",
    component: CustomerList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isCustomer) {
        next();
      } else {
        next({ name: "Customers" });
      }
    },
  },

  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  routes,
});

export default router;
