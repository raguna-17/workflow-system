import api from "../../lib/axios";

export const getCategories = async (type) => {

    const response = await api.get(
        "/categories",
        {
            params: type ? { type } : {},
        }
    );

    return response.data;
};