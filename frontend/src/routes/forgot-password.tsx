import { Title } from "@/components/layout/title";
import { AuthPage } from "@refinedev/antd";
import React from "react";

export const ForgotPasswordPage: React.FC = () => {
  return <AuthPage type="forgotPassword" title={<Title collapsed={false} />} />;
};
