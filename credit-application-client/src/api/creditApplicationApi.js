import axios from "axios";

export const postCreditApplication = (body) => {
  return axios.post("http://localhost:8080/client/creditApplication", body);
};

export const getCreditApplication = (body) => {
  console.log("getCreditApplication request body-> ");
  console.log(body);
  return axios.post("http://localhost:8080/client/findCreditApplication", body);
};

export const updateLastName = (body) => {
  console.log("updateLastName request body-> ");
  console.log(body);
  return axios.post("http://localhost:8080/client/updateClientInformation/lastName", body);
};

export const updateFirstName = (body) => {
  console.log("updateFirstName request body-> ");
  console.log(body);
  return axios.post(
    "http://localhost:8080/client/updateClientInformation/firstName", body);
};

export const updatePhoneNumber = (body) => {
  console.log("updatePhoneNumber request body");
  console.log(body);
  return axios.post(
    "http://localhost:8080/client/updateClientInformation/phoneNumber", body);
}

export const updateCreditApplication = (body) => {
  console.log("updateCreditApplication request body");
  console.log(body);
  return axios.post(
    "http://localhost:8080/creditApplication/updateCreditApplication", body);
};

export const deleteClient = (body) => {
  console.log("deleteClient request body");
  console.log(body);
  return axios.delete(
    "http://localhost:8080/client/deleteClient", {data: body});
}
