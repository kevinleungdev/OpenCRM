import { useLink } from "@refinedev/core";
import { Space, theme, Typography } from "antd";
import React from "react";
import { Logo } from "./logo";

const { useToken } = theme;

const name = import.meta.env.VITE_APP_TITLE;

type Props = {
  collapsed: boolean;
  wrapperStyles?: {};
};

export const Title: React.FC<Props> = ({ collapsed, wrapperStyles }: Props) => {
  const { token } = useToken();

  // useLink() is a hook that leverages the Link property of the routeProvider to create linkes compatible with the user's route library
  const Link = useLink();

  return (
    <Link
      to="/login"
      style={{ display: "inline-block", textDecoration: "none" }}
    >
      <Space
        style={{
          display: "flex",
          alignItems: "center",
          fontSize: "inherit",
          ...wrapperStyles,
        }}
      >
        <div
          style={{ width: "24px", height: "24px", color: token.colorPrimary }}
        >
          <Logo />
        </div>

        {!collapsed && (
          <Typography.Title
            style={{ fontSize: "inherit", fontWeight: 700, marginBottom: 0 }}
          >
            {name}
          </Typography.Title>
        )}
      </Space>
    </Link>
  );
};
