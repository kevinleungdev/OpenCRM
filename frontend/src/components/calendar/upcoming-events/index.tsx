import { CardProps, Skeleton as AntdSkeleton, Card, Button } from "antd";
import React from "react";
import styles from "./index.module.css";
import { useList, useNavigation } from "@refinedev/core";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import { UpcomingEventsQuery } from "@/graphql/types";
import { CALENDAR_UPCOMING_EVENTS_QUERY } from "./queries";
import dayjs from "dayjs";
import { CalendarOutlined, RightCircleOutlined } from "@ant-design/icons";
import { Text } from "@/components/text";
import { CalendarUpcomingEvent } from "./events";

type CalendarUpcomingEventsProps = {
  limit?: number;
  cardProps?: CardProps;
  showGotoListButton?: boolean;
};

const NoEvnents: React.FC = () => {
  return (
    <span
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        height: "220px",
      }}
    >
      No Upcoming Event
    </span>
  );
};

const Skeleton: React.FC = () => {
  return (
    <div className={styles.item}>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
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
            width: "90%",
            height: "16px",
            marginTop: "8px",
          }}
        />
      </div>
    </div>
  );
};
export const CalendarUpcomingEvents: React.FC<CalendarUpcomingEventsProps> = ({
  limit = 5,
  cardProps,
  showGotoListButton,
}) => {
  const { list } = useNavigation();

  const { data, isLoading } = useList<GetFieldsFromList<UpcomingEventsQuery>>({
    resource: "events",
    pagination: {
      pageSize: limit,
    },
    sorters: [
      {
        field: "startDate",
        order: "asc",
      },
    ],
    filters: [
      {
        field: "startDate",
        operator: "gte",
        value: dayjs().format("YYYY-MM-DD"),
      },
    ],
    meta: {
      gqlQuery: CALENDAR_UPCOMING_EVENTS_QUERY,
    },
  });

  return (
    <Card
      styles={{
        header: {
          padding: "8px 16px",
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
          <CalendarOutlined />
          <Text
            size="sm"
            style={{
              marginLeft: ".7rem",
            }}
          >
            Upcoming Events
          </Text>
        </div>
      }
      extra={
        showGotoListButton && (
          <Button onClick={() => list("events")} icon={<RightCircleOutlined />}>
            See calendar
          </Button>
        )
      }
      {...cardProps}
    >
      {isLoading &&
        Array.from({ length: limit }).map((_, index) => (
          <Skeleton key={index} />
        ))}
      {!isLoading &&
        data?.data?.map((item) => (
          <CalendarUpcomingEvent key={item.id} item={item} />
        ))}
      {!isLoading && data?.data?.length === 0 && <NoEvnents />}
    </Card>
  );
};
