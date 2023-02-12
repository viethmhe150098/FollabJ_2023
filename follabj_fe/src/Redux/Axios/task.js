import axios from "axios";

export const task = axios.create({
    baseURL: "http://localhost:8080/task"
  });
