import axios from "axios";


const api = axios.create({

    baseURL: import.meta.env.VITE_API_URL,

    headers: {
        "Content-Type": "application/json",
    },

});


api.interceptors.request.use(
    config => {

        const publicPaths = [
            "/users",
            "/auth/login",
            "/auth/refresh",
        ];


        const isPublic =
            publicPaths.includes(config.url);


        if (!isPublic) {

            const token =
                localStorage.getItem("accessToken");


            if (token) {

                config.headers.Authorization =
                    `Bearer ${token}`;

            }
        }


        return config;

    }
);


export default api;