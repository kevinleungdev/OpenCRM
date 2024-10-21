import { ThemedLayoutContextProvider } from "@refinedev/antd";
import { Grid, Layout as AntdLayout } from "antd";
import React, { PropsWithChildren } from "react";
import { Sider } from "./sider";
import { Header } from "./header";

export const Layout: React.FC<PropsWithChildren> = ({ children }) => {
  const breakpoint = Grid.useBreakpoint();
  const isSmall = typeof breakpoint.sm === "undefined" ? true : breakpoint.sm;

  return (
    <ThemedLayoutContextProvider>
      <AntdLayout hasSider style={{ minHeight: "100vh" }}>
        <Sider />
        <AntdLayout>
          <Header />
          <AntdLayout.Content style={{ padding: isSmall ? 32 : 16 }}>
            {children}
          </AntdLayout.Content>
        </AntdLayout>
      </AntdLayout>
    </ThemedLayoutContextProvider>
  );
};
