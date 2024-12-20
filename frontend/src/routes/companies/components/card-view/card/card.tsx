import { useDelete, useNavigation } from "@refinedev/core";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import React from "react";
import { CompanyCardSkeleton } from "./skeleton";
import { CompaniesTableQuery } from "@/graphql/types";
import { Button, Card, Dropdown, Space, Tooltip } from "antd";
import { CustomAvatar, Text } from "@/components";
import AvatarGroup from "../../avatar-group";
import { DeleteOutlined, EyeOutlined, MoreOutlined } from "@ant-design/icons";
import { currencyNumber } from "@/utilities";

type Props = {
  company: GetFieldsFromList<CompaniesTableQuery>;
};

export const CompanyCard: React.FC<Props> = ({ company }) => {
  const { edit } = useNavigation();
  const { mutate } = useDelete();

  if (!company) return <CompanyCardSkeleton />;

  const relatedContactAvatars = company?.contacts?.nodes?.map((contact) => {
    return {
      name: contact.name,
      src: contact.avatarUrl as string | undefined,
    };
  });

  return (
    <Card
      size="small"
      actions={[
        <div
          key="1"
          style={{
            width: "100%",
            height: "60px",
            display: "flex",
            justifyContent: "space-between",
            alignItems: "flex-start",
            padding: "0 16px",
          }}
        >
          <div
            style={{
              display: "flex",
              flexDirection: "column",
              gap: "6px",
              alignItems: "flex-start",
            }}
          >
            <Text size="xs">Related contacts</Text>
            <AvatarGroup
              overlap
              size={"small"}
              gap="4px"
              avatars={relatedContactAvatars}
            />
          </div>
          <div>
            <Text size="xs">Sales owner</Text>
            <Tooltip
              title={company.salesOwner?.name}
              key={company.salesOwner?.id}
            >
              <CustomAvatar
                name={company.salesOwner?.name}
                src={company.salesOwner?.avatarUrl}
              />
            </Tooltip>
          </div>
        </div>,
      ]}
    >
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          position: "relative",
        }}
      >
        <Dropdown
          menu={{
            items: [
              {
                label: "View company",
                key: "1",
                // @ts-ignore
                icon: <EyeOutlined />,
                onClick: () => {
                  edit("companies", company.id);
                },
              },
              {
                danger: true,
                label: "Delete company",
                key: "2",
                // @ts-ignore
                icon: <DeleteOutlined />,
                onClick: () => {
                  mutate({
                    resource: "companies",
                    id: company.id,
                  });
                },
              },
            ],
          }}
          placement="bottom"
          arrow
        >
          <Button
            type="text"
            shape="circle"
            style={{
              position: "absolute",
              top: 0,
              right: 0,
            }}
            // @ts-ignore
            icon={<MoreOutlined style={{ transform: "rotate(90deg)" }} />}
          />
        </Dropdown>

        <CustomAvatar
          name={company.name}
          src={company.avatarUrl}
          shape="square"
          style={{
            width: "48px",
            height: "48px",
          }}
        />
        <Text
          strong
          size="md"
          ellipsis={{
            tooltip: company.name,
          }}
        >
          {company.name}
        </Text>

        <Space
          direction="vertical"
          size={0}
          style={{
            marginTop: "8px",
            alignItems: "center",
          }}
        >
          <Text type="secondary">Open deals amount</Text>
          <Text
            strong
            size="md"
            style={{
              marginTop: "12px",
            }}
          >
            {currencyNumber(company?.dealsAggregate?.[0].sum?.value || 0)}
          </Text>
        </Space>
      </div>
    </Card>
  );
};
