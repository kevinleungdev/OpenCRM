import { User } from "@/graphql/schema.types";
import { useGetIdentity, useLogout } from "@refinedev/core";
import { Button, Popover } from "antd";
import React, { useState } from "react";
import { CustomAvatar } from "../custom-avatar";
import { Text } from "../text";
import { LogoutOutlined, SettingOutlined } from "@ant-design/icons";
import AccountSettings from "./account-settings";

export const CurrentUser: React.FC = () => {
  const [opened, setOpened] = useState(false);
  const { mutate: logout } = useLogout();
  const { data: user } = useGetIdentity<User>();

  const content = (
    <div style={{ display: "flex", flexDirection: "column" }}>
      <Text strong style={{ padding: "12px 20px" }}>
        {user?.name}
      </Text>
      <div
        style={{
          borderTop: "1px solid #d9d9d9",
          padding: "4px",
          display: "flex",
          flexDirection: "column",
          gap: "4px",
        }}
      >
        <Button
          style={{
            textAlign: "left",
          }}
          // @ts-ignore
          icon={<SettingOutlined />}
          type="text"
          onClick={() => setOpened(true)}
          block
        >
          Account settings
        </Button>
        <Button
          style={{
            textAlign: "left",
            justifyContent: "flex-start",
          }}
          // @ts-ignore
          icon={<LogoutOutlined />}
          type="text"
          onClick={() => {
            logout();
          }}
          block
          danger
        >
          Logout
        </Button>
      </div>
    </div>
  );

  return (
    <>
      <Popover
        placement="bottomRight"
        content={content}
        trigger="click"
        overlayInnerStyle={{ padding: 0 }}
        overlayStyle={{ zIndex: 999 }}
      >
        <CustomAvatar
          name={user?.name}
          src={user?.avatarUrl}
          size="default"
          style={{ cursor: "pointer" }}
        />
      </Popover>
      {user && (
        <AccountSettings
          opened={opened}
          setOpened={setOpened}
          userId={user?.id}
        />
      )}
    </>
  );
};
