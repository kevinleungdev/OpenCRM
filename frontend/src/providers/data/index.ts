import graphqlDataProvider, { GraphQLClient } from "@refinedev/nestjs-query";
import { API_URL } from "@/providers/urls";
import { axiosInstance } from "./axios";

export const client = new GraphQLClient(API_URL, {
  // @ts-ignore
  fetch: async (url: string, options: any) => {
    try {
      const response = await axiosInstance.request({
        url,
        data: options?.body,
        ...options,
      });

      return { ...response, data: response.data };
    } catch (error: any) {
      const message = error?.map((e: any) => e?.message).join("");
      const code = error?.[0]?.extensions?.code;

      return Promise.reject({
        message: message || JSON.stringify(error),
        statusCode: code || 500,
      });
    }
  },
});

export const dataProvider = graphqlDataProvider(client);
