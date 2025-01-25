import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import { refreshTokens, shouldRefreshToken } from "./refresh-token";

export const axiosInstance = axios.create({
  headers: {
    "Content-Type": "application/json, text/plain, */*",
    "Apollo-Require-Preflight": "true",
  },
});

axiosInstance.interceptors.request.use(
  async (config) => {
    const accessToken = localStorage.getItem("access_token");
    if (accessToken && config?.headers) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

axiosInstance.interceptors.response.use(
  async (response) => {
    convertAxiosToFetchResponse(response);

    const data = response?.data;
    const errors = data?.errors;
    const originalRequest = response.config as AxiosRequestConfig & {
      _retry: boolean;
    };

    // Refresh token if the current access token was expired
    if (errors) {
      if (shouldRefreshToken(response) && !originalRequest?._retry) {
        const tokens = refreshTokens();
        if (!tokens) throw errors;

        // If the token refresh is successful, it sets `_retry = true` to indicate that the request is being retried
        originalRequest._retry = true;
        // re-sends the oringial request using
        return axiosInstance(originalRequest);
      }

      setResponseOk(response, false);
      throw errors;
    }

    return response;
  },
  async (error: AxiosError) => {
    if (error.status === 401) {
      const refreshToken = localStorage.getItem("refresh_token");
      if (!refreshToken) {
        localStorage.removeItem("access_token");
        localStorage.removeItem("refresh_token");
      }

      const tokens = refreshTokens();
      if (!tokens) console.log("error", error);
    }
  },
);

const convertAxiosToFetchResponse = (response: AxiosResponse) => {
  response.headers["forEach"] = function (callback: any) {
    for (const header in this) {
      if (Object.hasOwn(this, header)) {
        callback(this[header], header, this);
      }
    }
  };

  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-ignore
  response["text"] = function () {
    return JSON.stringify(this.data);
  };
  setResponseOk(response, true);
};

const setResponseOk = (response: AxiosResponse, ok: boolean) => {
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-ignore
  response["ok"] = ok;
};
