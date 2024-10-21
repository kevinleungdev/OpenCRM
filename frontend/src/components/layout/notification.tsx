import { BellOutlined } from "@ant-design/icons";
import { Badge, Button, Popover, Spin } from "antd";
import React, { useState } from "react";

export const Notification: React.FC = () => {
  const [open, setOpen] = useState(false);

  const loadingContent = (
    <div
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        height: 20,
      }}
    >
      <Spin />
    </div>
  );

  return (
    <Popover
      placement="bottomRight"
      trigger="click"
      content={loadingContent}
      onOpenChange={(newOpen) => setOpen(newOpen)}
      overlayStyle={{ width: 400 }}
    >
      <Badge dot>
        <Button
          shape="circle"
          // @ts-ignore
          icon={<BellOutlined />}
          style={{ border: 0 }}
        />
      </Badge>
    </Popover>
  );
};
