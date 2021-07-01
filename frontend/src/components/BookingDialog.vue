<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create reservation" : "Edit reservation" }}
        </v-toolbar>
        <v-form>
          <v-text-field v-model="booking.customerId" label="Customer ID" />
          <v-text-field v-model="booking.destination" label="Destination" />
          <v-text-field v-model="booking.nr_seats" label="Nr of seats" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookingDialog",
  props: {
    booking: Object,
    opened: Boolean,
  },
  methods: {
    persist() {
      if (this.isNew) {
        api.booking
          .createBooking({
            customerId: this.booking.customerId,
            destination: this.booking.destination,
            nr_seats: this.booking.nr_seats,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.booking
          .updateBooking(this.booking.id, {
            id: this.booking.id,
            customerId: this.customerId,
            username: this.booking.username,
            destination: this.booking.destination,
            date: this.booking.date,
            nr_seats: this.booking.nr_seats,
            total_price: this.booking.total_price,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.booking.id;
    },
  },
};
</script>

<style scoped></style>
