import { Title } from "@/components/layout/title";
import { demoCredentials } from "@/providers";
import { AuthPage } from "@refinedev/antd";
import { useLogin } from "@refinedev/core";
import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";

export const LoginPage: React.FC = () => {
  const [searchParams] = useSearchParams();
  // This hook calls login method from AuthProvider.
  const { mutate } = useLogin();

  const accessToken = localStorage.getItem("access_token");
  const refreshToken = localStorage.getItem("refresh_token");

  const emailFromSearchParams = searchParams.get("email");
  const initialValues = emailFromSearchParams
    ? { email: emailFromSearchParams }
    : demoCredentials;

  useEffect(() => {
    if (accessToken && refreshToken) {
      mutate({ accessToken, refreshToken });
    }
  }, [accessToken, refreshToken]);

  return (
    <AuthPage
      type="login"
      formProps={{ initialValues }}
      contentProps={{ className: "auth_page" }}
      title={<Title collapsed={false} />}
    />
  );
};
