import {
  BarsOutlined,
  LeftOutlined,
  RightOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import { useThemedLayoutContext } from "@refinedev/antd";
import { CanAccess, ITreeMenu, useLink, useMenu } from "@refinedev/core";
import { Button, Drawer, Grid, Layout, Menu, theme } from "antd";
import React, { CSSProperties } from "react";
import { Title } from "./title";

const drawerButtonStyles: CSSProperties = {
  borderTopLeftRadius: 0,
  borderBottomLeftRadius: 0,
  position: "fixed",
  top: 64,
  zIndex: 1001,
};

const { SubMenu } = Menu;
const { useToken } = theme;

export const Sider: React.FC = () => {
  const { token } = useToken();
  const {
    siderCollapsed,
    setSiderCollapsed,
    mobileSiderOpen,
    setMobileSiderOpen,
  } = useThemedLayoutContext();

  const Link = useLink();

  const breakpoint = Grid.useBreakpoint();
  const isMobile =
    typeof breakpoint.lg === "undefined" ? false : !breakpoint.lg;

  // useMenu hook is used to get menus derived from the resources. This hook can be also used for building custom menus, including multiple-level support
  const { selectedKey, menuItems, defaultOpenKeys } = useMenu();

  const renderTreeView = (tree: ITreeMenu[], selectedKey?: string) => {
    return tree.map((item: ITreeMenu) => {
      const { key, name, children, meta, route } = item;

      if (children.length > 0) {
        return (
          <CanAccess
            key={key}
            resource={name.toLowerCase()}
            action="list"
            params={{ resource: item }}
          >
            <SubMenu
              key={key}
              // @ts-ignore
              icon={meta?.icon ?? <UnorderedListOutlined />}
              title={meta?.label}
            >
              {renderTreeView(children, selectedKey)}
            </SubMenu>
          </CanAccess>
        );
      }

      const isSelected = selectedKey === key;

      return (
        <CanAccess
          key={key}
          resource={name.toLowerCase()}
          action="list"
          params={{ resource: item }}
        >
          <Menu.Item
            key={item.key}
            // @ts-ignore
            icon={meta?.icon ?? <UnorderedListOutlined />}
          >
            <Link to={route ?? ""}>{meta?.label}</Link>
            {!siderCollapsed && isSelected && (
              <div className="ant-menu-tree-arrow" />
            )}
          </Menu.Item>
        </CanAccess>
      );
    });
  };

  const items = renderTreeView(menuItems, selectedKey);

  const renderSider = () => {
    return <>{items}</>;
  };

  const renderMenu = () => {
    return (
      <Menu
        defaultSelectedKeys={selectedKey ? [selectedKey] : []}
        defaultOpenKeys={defaultOpenKeys}
        mode="inline"
        style={{
          paddingTop: "8px",
          border: "none",
          overflow: "auto",
          height: "calc(100% - 72px)",
          background: "transparent",
        }}
        onClick={() => {
          setMobileSiderOpen(true);
        }}
      >
        {renderSider()}
      </Menu>
    );
  };

  const renderDrawerSider = () => {
    return (
      <>
        <Drawer
          open={mobileSiderOpen}
          placement="left"
          closable={false}
          width={256}
          styles={{ body: { padding: 0 } }}
          maskClosable={true}
        >
          <Layout>
            <Layout.Sider>
              <div
                style={{
                  width: "256px",
                  height: "64px",
                  display: "flex",
                  justifyContent: "flex-start",
                  alignItems: "center",
                  backgroundColor: token.colorBgElevated,
                  borderBottom: "none",
                }}
              >
                <Title collapsed={false} />
              </div>
              {renderMenu()}
            </Layout.Sider>
          </Layout>
        </Drawer>
        <Button
          style={drawerButtonStyles}
          size="large"
          onClick={() => setMobileSiderOpen(true)}
          // @ts-ignore
          icon={<BarsOutlined />}
        />
      </>
    );
  };

  if (isMobile) {
    return renderDrawerSider();
  }

  const siderStyles: React.CSSProperties = {
    backgroundColor: token.colorBgContainer,
    borderRight: `1px solid ${token.colorBorderBg}`,
    position: "sticky",
    top: 0,
    left: 0,
    zIndex: 999,
    height: "100vh",
  };

  return (
    <>
      <Layout.Sider
        style={siderStyles}
        width={256}
        collapsible
        collapsed={siderCollapsed}
        onCollapse={(collapsed, type) => {
          if (type === "clickTrigger") {
            setSiderCollapsed(collapsed);
          }
        }}
        collapsedWidth={80}
        breakpoint="lg"
        trigger={
          <Button
            type="text"
            style={{
              borderRadius: 0,
              width: "100%",
              height: "100%",
              backgroundColor: token.colorBgElevated,
              borderRight: `1px solid ${token.colorBorderBg}`,
            }}
          >
            {siderCollapsed ? (
              // @ts-ignore
              <RightOutlined style={{ color: token.colorPrimary }} />
            ) : (
              // @ts-ignore
              <LeftOutlined style={{ color: token.colorPrimary }} />
            )}
          </Button>
        }
      >
        <div
          style={{
            width: siderCollapsed ? "80px" : "256px",
            height: "64px",
            padding: siderCollapsed ? "0" : "0 16px",
            display: "flex",
            justifyContent: siderCollapsed ? "center" : "flex-start",
            alignItems: "center",
            backgroundColor: token.colorBgElevated,
            fontSize: "14px",
          }}
        >
          <Title collapsed={siderCollapsed} />
        </div>
        {renderMenu()}
      </Layout.Sider>
    </>
  );
};
