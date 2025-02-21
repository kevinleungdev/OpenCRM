import { UsersSelectQuery } from "@/graphql/types";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import { Space, Tooltip } from "antd";
import React from "react";
import { CustomAvatar } from "../custom-avatar";
import { PlusCircleOutlined } from "@ant-design/icons";

type Props = {
  useOne: GetFieldsFromList<UsersSelectQuery>;
  useTwo: GetFieldsFromList<UsersSelectQuery>;
};

export const Participants: React.FC<Props> = ({ useOne, useTwo }) => {
  return (
    <Space
      size={4}
      style={{
        textTransform: "capitalize",
      }}
    >
      <Tooltip title={useOne.name}>
        <CustomAvatar size="small" src={useOne.avatarUrl} name={useOne.name} />
      </Tooltip>
      <PlusCircleOutlined className="xs tertiary" />
      <Tooltip title={useTwo.name}>
        <CustomAvatar size="small" src={useTwo.avatarUrl} name={useTwo.name} />
      </Tooltip>
    </Space>
  );
};
