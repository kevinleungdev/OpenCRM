import { Space, theme, Layout } from "antd";
import React from "react";
import { CurrentUser } from "./current-user";
import { Notification } from "./notification";

const { useToken } = theme;

export const Header: React.FC = () => {
  const { token } = useToken();

  const headerStyles: React.CSSProperties = {
    backgroundColor: token.colorBgElevated,
    display: "flex",
    justifyContent: "flex-end",
    alignItems: "center",
    padding: "0px 24px",
    height: "64px",
    position: "sticky",
    top: 0,
    zIndex: 999,
  };

  return (
    <Layout.Header style={headerStyles}>
      <Space align="center" size={"middle"}>
        <Notification />
        <CurrentUser />
      </Space>
    </Layout.Header>
  );
};
