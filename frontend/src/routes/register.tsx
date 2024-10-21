import { Title } from "@/components/layout/title";
import { AuthPage } from "@refinedev/antd";
import React from "react";

export const RegisterPage: React.FC = () => {
  return <AuthPage type="register" title={<Title collapsed={false} />} />;
};
