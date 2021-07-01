import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBookings() {
    return HTTP.get(BASE_URL + "/booking", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allCustomers() {
    return HTTP.get(BASE_URL + "/booking/customers", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allFlights() {
    return HTTP.get(BASE_URL + "/booking/flights", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  createBooking(booking) {
    return HTTP.post(BASE_URL + "/booking", booking, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  updateBooking(id, booking) {
    return HTTP.put(BASE_URL + "/booking/" + id, booking, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  allBookingsForCustomer(id) {
    return HTTP.get(BASE_URL + "/booking/customer/" + id, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },
  downloadTicket(id) {
    return HTTP.get(BASE_URL + "/booking/customer/download/" + id, {
      responseType: "blob",
      headers: authHeader(),
    }).then((response) => {
      const fileURL = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = fileURL;
      link.setAttribute("download", "Ticket" + id + ".pdf");
      document.body.appendChild(link);
      link.click();
    });
  },
};
