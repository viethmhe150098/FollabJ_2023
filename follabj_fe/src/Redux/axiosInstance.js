import axios from "axios";

const instance = axios.create({
    baseURL: 'http://localhost:8080',
});


instance.interceptors.request.use(function (config) {
    // Do something before request is sent
    const access_token = "Bearer " + localStorage.getItem('access_token')

    config.headers.Authorization =  access_token;
    return config;
  }, function (error) {
    // Do something with request error
    return Promise.reject(error);
  });


// use interceptors to do something when received response (or send request)
instance.interceptors.response.use(function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data

    return response;
}, async function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error

    // console.log(typeof(error.response.data.error))
    // console.log(typeof(error.response.status))

    const errorMessage = error.response.data.error
    const originalConfig = error.config


    switch (error.response.status) {
        case 401 :
            // send refresh token to get new token
            if( errorMessage.includes("The Token has expired")) {
                //console.log("token expired")
                const response = await axios.get("http://localhost:8080/token/refresh", {
                    headers : {
                        'Authorization' : "Bearer "+ localStorage.getItem('refresh_token')
                    }
                }).catch((error) => {
                    localStorage.clear();
                })

                localStorage.setItem('access_token', response.data.access_token);
                localStorage.setItem('refresh_token', response.data.refresh_token);

            }
            return instance(originalConfig)
        default:
            break;
    }
    //console.log(typeof(error.response.data.error))

    console.log(error);

    return Promise.reject(error);
});

export default instance;