import { User } from "@/graphql/schema.types";
import { LoginMutation, RegisterMutation } from "@/graphql/types";
import { client, dataProvider } from "@/providers/data";
import { API_BASE_URL, API_URL } from "@/providers/urls";
import { AuthProvider } from "@refinedev/core";
import { request } from "@refinedev/nestjs-query";
import {
  GET_IDENTITY_QUERY,
  LOGIN_MUTATION,
  REGISTER_MUTATION,
} from "./queries";

export const emails = ["michael.scott@dundermifflin.com"];

const randomEmail = emails[Math.floor(Math.random() * emails.length)];

export const demoCredentials = {
  email: randomEmail,
  password: "michael",
};

export const authProvider: AuthProvider = {
  login: async ({
    email,
    password,
    providerName,
    accessToken,
    refreshToken,
  }) => {
    if (accessToken && refreshToken) {
      client.setHeaders({
        Authorization: `Bearer ${accessToken}`,
      });

      localStorage.setItem("access_token", accessToken);
      localStorage.setItem("refresh_token", refreshToken);

      return {
        success: true,
        redirectTo: "/",
      };
    }

    if (providerName) {
      window.location.href = `${API_BASE_URL}/auth/${providerName}`;

      return {
        success: true,
      };
    }

    try {
      const response = await request<LoginMutation>(API_URL, LOGIN_MUTATION, {
        email,
        password,
      });

      client.setHeaders({
        Authorization: `Bearer ${response.login.accessToken}`,
      });

      localStorage.setItem("access_token", response.login.accessToken);
      localStorage.setItem("refresh_token", response.login.refreshToken);

      return {
        success: true,
        redirectTo: "/",
      };
    } catch (error: any) {
      return {
        success: false,
        error: {
          message: "message" in error ? error.message : "Login failed",
          name: "name" in error ? error.name : "Invalid email or password",
        },
      };
    }
  },
  register: async ({ email, password }) => {
    try {
      await request<RegisterMutation>(API_URL, REGISTER_MUTATION, {
        email,
        password,
      });

      return {
        success: true,
        redirectTo: `/login/email=${email}`,
      };
    } catch (error: any) {
      return {
        success: false,
        error: {
          message: "message" in error ? error.message : "Register failed",
          name: "name" in error ? error.name : "Invalid email or password",
        },
      };
    }
  },
  logout: async () => {
    client.setHeaders({
      Authorization: "",
    });

    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");

    return {
      success: true,
      redirectTo: "/login",
    };
  },
  check: async () => {
    try {
      await dataProvider.custom({
        url: API_URL,
        method: "post",
        headers: {},
        meta: {
          rawQuery: `
                    query Me {
                        me {
                            name
                        }
                    }
                `,
        },
      });

      return {
        authenticated: true,
      };
    } catch (error) {
      return {
        authenticated: false,
      };
    }
  },
  forgotPassword: async () => {
    return {
      success: true,
      redirectTo: "/update-password",
    };
  },
  updatePassword: async () => {
    return {
      success: true,
      redirectTo: "/login",
    };
  },
  getIdentity: async () => {
    try {
      const { data } = await dataProvider.custom<{ me: User }>({
        url: API_URL,
        method: "post",
        headers: {},
        meta: {
          rawQuery: GET_IDENTITY_QUERY,
        },
      });
      return data.me;
    } catch (error) {
      return undefined;
    }
  },
  onError: async (error) => {
    if (error?.statusCode === "UNAUTHENTICATED") {
      return {
        logout: true,
      };
    }

    return { error };
  },
};
