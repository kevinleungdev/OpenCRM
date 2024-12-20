import { Space } from "antd";
import React from "react";
import { CustomAvatar } from "./custom-avatar";
import { Text } from "./text";

type Props = {
  name?: string;
  avatarUrl?: string;
  shape?: "circle" | "square";
};

export const SelectOptionWithAvatar: React.FC<Props> = ({
  avatarUrl,
  name,
  shape,
}) => {
  return (
    <Space>
      <CustomAvatar shape={shape} src={avatarUrl} name={name} />
      <Text>{name}</Text>
    </Space>
  );
};
