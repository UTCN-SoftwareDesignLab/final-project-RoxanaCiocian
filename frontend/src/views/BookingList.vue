<template>
  <v-card>
    <v-card-title>
      Bookings - Employee
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addBooking">Add Booking</v-btn>
    </v-card-title>
    All Bookings
    <v-data-table
      :headers="headers"
      :items="booking"
      :search="search"
      @click:row="editFlight"
    ></v-data-table>

    <BookingDialog
      :opened="dialogVisible"
      :booking="selectedBooking"
      @refresh="refreshList"
    >
    </BookingDialog>
    All Customers
    <v-spacer></v-spacer>
    <v-data-table
      :headers="customers"
      :items="users"
      :search="search"
    ></v-data-table>
    All Flights
    <v-data-table
      :headers="flightsss"
      :items="flights"
      :search="search"
    ></v-data-table>
  </v-card>
</template>

<script>
import BookingDialog from "../components/BookingDialog";
import api from "../api";

export default {
  name: "BookingList",
  components: { BookingDialog },
  data() {
    return {
      booking: [],
      users: [],
      flights: [],
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
      customers: [
        {
          text: "Customer ID",
          align: "start",
          sortable: false,
          value: "id",
        },
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "username",
        },
        { text: "Name", value: "name" },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      flightsss: [
        { text: "Flight ID", value: "id" },
        {
          text: "Destination",
          align: "start",
          sortable: false,
          value: "destination",
        },
        { text: "Date", value: "dateTime" },
        { text: "Available seats", value: "available_seats" },
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
    addBooking() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBooking = {};
      this.booking = await api.booking.allBookings();
      this.users = await api.booking.allCustomers();
      this.flights = await api.booking.allFlights();
    },
  },

  async created() {
    this.booking = await api.booking.allBookings();
    this.refreshList();
    this.users = await api.booking.allCustomers();
    this.flights = await api.booking.allFlights();
  },
};
</script>

<style scoped></style>
