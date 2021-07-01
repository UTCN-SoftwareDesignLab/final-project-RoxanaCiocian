<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-card-actions>
          <v-btn @click="donloadTicket">Download Ticket</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "CustomerDialog",
  props: {
    booking: Object,
    opened: Boolean,
  },
  methods: {
    donloadTicket() {
      api.booking
        .downloadTicket(this.booking.id)
        .then(() => this.$emit("refresh"));
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
