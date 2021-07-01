<template>
  <v-card>
    <v-card-title>
      Bookings for customer
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editFlight"
    ></v-data-table>
    <CustomerDialog
      :opened="dialogVisible"
      :booking="selectedBooking"
      @refresh="refreshList"
    >
    </CustomerDialog>
  </v-card>
</template>

<script>
import api from "../api";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import CustomerDialog from "../components/CustomerDialog";

export default {
  name: "CustomerList",
  components: { CustomerDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Customer ID",
          align: "start",
          sortable: false,
          value: "customerId",
        },
        { text: "Username", value: "username" },
        { text: "Destination", value: "destination" },
        { text: "Date", value: "date" },
        { text: "Nr of seats", value: "nr_seats" },
        { text: "Total price", value: "total_price" },
      ],
      dialogVisible: false,
      selectedBooking: {},
    };
  },

  methods: {
    editFlight(flight) {
      this.selectedBooking = flight;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      const id = this.$store.getters["auth/getCustomerID"];
      this.users = await api.booking.allBookingsForCustomer(id);
    },
  },

  async created() {
    console.log(this.$store.getters["auth/getCustomerID"]);
    const id = this.$store.getters["auth/getCustomerID"];
    this.users = await api.booking.allBookingsForCustomer(id);
    this.refreshList();
    this.stompClient = Stomp.over(
      new SockJS("http://localhost:8088/gs-guide-websocket")
    );
    this.stompClient.connect(
      {},
      (frame) => {
        this.connected = true;
        console.log(frame);
        this.stompClient.subscribe("/booking/customer", (message) => {
          console.log(message);
          alert(message.body);
        });
      },
      (error) => {
        console.log(error);
        this.connected = false;
      }
    );
  },
};
</script>

<style scoped></style>
