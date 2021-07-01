import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(user) {
    return HTTP.post(BASE_URL + "/users", user, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  edit(id, user) {
    return HTTP.put(BASE_URL + "/users/" + id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteById(id) {
    return HTTP.delete(BASE_URL + "/users/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
};
