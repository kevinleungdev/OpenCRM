import React from "react";
import styles from "./index.module.css";
import { useList } from "@refinedev/core";
import { AUDITS_QUERY, DEALS_QUERY } from "./queries";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import {
  LatestActivitiesAuditsQuery,
  LatestActivitiesDealsQuery,
} from "@/graphql/types";
import { Card, Skeleton as AntdSkeleton } from "antd";
import { UnorderedListOutlined } from "@ant-design/icons";
import { CustomAvatar, Text } from "@/components";
import dayjs from "dayjs";

export const DashboardLatestActivities: React.FC<{ limit?: number }> = ({
  limit = 5,
}) => {
  const { data: deals, isLoading: isLoadingDeals } = useList<
    GetFieldsFromList<LatestActivitiesDealsQuery>
  >({
    resource: "deals",
    pagination: {
      mode: "off",
    },
    meta: {
      gqlQuery: DEALS_QUERY,
    },
  });

  const {
    data: audits,
    isLoading: isLoadingAudits,
    isError,
    error,
  } = useList<GetFieldsFromList<LatestActivitiesAuditsQuery>>({
    resource: "audits",
    pagination: {
      pageSize: limit,
    },
    sorters: [
      {
        field: "createdAt",
        order: "desc",
      },
    ],
    filters: [
      {
        field: "action",
        operator: "in",
        value: ["CREATE", "UPDATE"],
      },
      {
        field: "targetEntity",
        operator: "eq",
        value: "Deal",
      },
    ],

    meta: {
      gqlQuery: AUDITS_QUERY,
    },
  });

  if (isError) {
    console.error("Error fetching latest activities", error);
    return null;
  }

  const isLoading = isLoadingDeals || isLoadingAudits;

  return (
    <Card
      styles={{
        header: {
          padding: "16px",
        },
        body: {
          padding: "0 1rem",
        },
      }}
      title={
        <div
          style={{
            display: "flex",
            alignItems: "center",
            gap: "8px",
          }}
        >
          <UnorderedListOutlined />
          <Text
            size="sm"
            style={{
              marginLeft: ".5rem",
            }}
          >
            Latest Activities
          </Text>
        </div>
      }
    >
      {isLoading &&
        Array.from({ length: limit }).map((_, i) => <Skeleton key={i} />)}
      {!isLoading &&
        audits?.data.map(({ id, action, user, targetId, createdAt }) => {
          const deal =
            deals?.data.find((task) => task.id === `${targetId}`) || undefined;

          return (
            <div key={id} className={styles.item}>
              <div className={styles.avatar}>
                <CustomAvatar
                  shape="square"
                  size={48}
                  name={deal?.company.name}
                  src={deal?.company.avatarUrl}
                />
              </div>
              <div
                className={styles.action}
                style={{ textOverflow: "ellipsis", overflow: "hidden" }}
              >
                <Text type="secondary" size="xs">
                  {dayjs(createdAt).fromNow()}
                </Text>

                <Text className={styles.detail}>
                  <Text className={styles.name} strong>
                    {user?.name}
                  </Text>
                  <Text>{action === "CREATE" ? "created" : "moved"}</Text>
                  <Text strong>{deal?.title}</Text>
                  <Text>deal</Text>
                  <Text strong>{action === "CREATE" ? "in" : "to"}</Text>
                  <Text strong>{deal?.stage?.title || "Unassigned"}</Text>
                </Text>
              </div>
            </div>
          );
        })}
    </Card>
  );
};

const Skeleton = () => {
  return (
    <div className={styles.item}>
      <AntdSkeleton.Avatar
        active
        size={48}
        shape="square"
        style={{
          borderRadius: "4px",
          marginRight: "16px",
        }}
      />
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
        }}
      >
        <AntdSkeleton.Button
          active
          style={{
            height: "16px",
          }}
        />
        <AntdSkeleton.Button
          active
          style={{
            height: "16px",
            width: "300px",
          }}
        />
      </div>
    </div>
  );
};
