import { Spin } from "antd";
import React from "react";

export const FullscreenLoading: React.FC = () => {
  return (
    <Spin
      size="large"
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        width: "100%",
        height: "100vh",
      }}
    />
  );
};
