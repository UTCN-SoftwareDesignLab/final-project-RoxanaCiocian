<template>
  <v-card>
    <v-card-title>
      Users - Admin
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addUser">Add Empeeeloyee</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    <UserDialog
      :opened="dialogVisible"
      :user="selectedUser"
      @refresh="refreshList"
    >
    </UserDialog>
    <v-btn @click="goToFlights">Go to flights</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
import router from "../router";

export default {
  name: "UserList",
  components: { UserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "username",
        },
        { text: "id", value: "id" },
        { text: "Name", value: "name" },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },

  methods: {
    editUser(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    addUser() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
    goToFlights() {
      router.push("/flights");
    },
  },

  async created() {
    this.users = await api.users.allUsers();
  },
};
</script>

<style scoped></style>
