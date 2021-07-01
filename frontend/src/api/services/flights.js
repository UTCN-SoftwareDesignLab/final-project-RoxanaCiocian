import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allFlights() {
    return HTTP.get(BASE_URL + "/flights", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  createFlight(flight) {
    return HTTP.post(BASE_URL + "/flights", flight, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  updateFlight(id, flight) {
    return HTTP.put(BASE_URL + "/flights/" + id, flight, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  deleteFlight(id) {
    return HTTP.delete(BASE_URL + "/flights/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  downloadPDF() {
    return HTTP.get(BASE_URL + "/flights/download", {
      responseType: "blob",
      headers: authHeader(),
    }).then((response) => {
      const flieURL = window.URL.createObjectURL(new Blob([response.data]));
      const fileLink = document.createElement("a");
      fileLink.href = flieURL;
      fileLink.setAttribute("download", "Flights report.pdf");
      document.body.appendChild(fileLink);
      fileLink.click();
    });
  },
};
