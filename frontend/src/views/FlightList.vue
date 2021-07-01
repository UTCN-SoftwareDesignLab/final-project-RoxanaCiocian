<template>
  <v-card>
    <v-card-title>
      Flights - Admin
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addFlight">Add Flight</v-btn>
      <v-btn @click="downloadPDF">Download PDF</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="flights"
      :search="search"
      @click:row="editFlight"
    ></v-data-table>
    <FlightDialog
      :opened="dialogVisible"
      :flight="selectedFlight"
      @refresh="refreshList"
    >
    </FlightDialog>
    <v-btn @click="goToUsers">Go to Users</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import FlightDialog from "../components/FlightDialog";
import router from "../router";
export default {
  name: "FlightList",
  components: { FlightDialog },
  data() {
    return {
      flights: [],
      search: "",
      headers: [
        {
          text: "Destination",
          align: "start",
          sortable: false,
          value: "destination",
        },
        { text: "Date", value: "dateTime" },
        { text: "Available seats", value: "available_seats" },
        { text: "Ticket price", value: "ticket_price" },
      ],
      dialogVisible: false,
      selectedFlight: {},
    };
  },
  methods: {
    editFlight(flight) {
      this.selectedFlight = flight;
      this.dialogVisible = true;
    },
    addFlight() {
      this.dialogVisible = true;
    },
    downloadPDF() {
      api.flights.downloadPDF();
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedFlight = {};
      this.flights = await api.flights.allFlights();
    },
    goToUsers() {
      router.push("/users");
    },
  },

  async created() {
    this.flights = await api.flights.allFlights();
  },
};
</script>

<style scoped></style>
