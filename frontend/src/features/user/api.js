import api from "../../lib/axios";


export const createUser = async (data) => {

    const response = await api.post(
        "/users",
        data
    );

    return response.data;
};


export const getUser = async (id) => {

    const response = await api.get(
        `/users/${id}`
    );

    return response.data;
};