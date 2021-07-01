<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create flight" : "Edit flight" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="flight.destination" label="Destination" />
          <v-text-field v-model="flight.dateTime" label="Date" />
          <v-text-field
            v-model="flight.available_seats"
            label="Available seats"
          />
          <v-text-field v-model="flight.ticket_price" label="Ticket price" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
          <v-btn @click="deleteFlight">Delete Flight</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
export default {
  name: "FlightDialog",
  props: {
    flight: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.flights
          .createFlight({
            destination: this.flight.destination,
            dateTime: this.flight.dateTime,
            available_seats: this.flight.available_seats,
            ticket_price: this.flight.ticket_price,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.flights
          .updateFlight(this.flight.id, {
            id: this.flight.id,
            destination: this.flight.destination,
            dateTime: this.flight.dateTime,
            available_seats: this.flight.available_seats,
          })
          .then(() => this.$emit("refresh"));
      }
    },
    deleteFlight() {
      api.flights
        .deleteFlight(this.flight.id)
        .then(() => this.$emit("refresh"));
    },
  },
  computed: {
    isNew: function () {
      return !this.flight.id;
    },
  },
};
</script>

<style scoped></style>
