import { Title } from "@/components/layout/title";
import { AuthPage } from "@refinedev/antd";
import React from "react";

const UpdatePasswordPage: React.FC = () => {
  return <AuthPage type="updatePassword" title={<Title collapsed={false} />} />;
};

export default UpdatePasswordPage;
