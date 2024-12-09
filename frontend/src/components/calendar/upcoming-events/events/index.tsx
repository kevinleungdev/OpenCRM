import { UpcomingEventsQuery } from "@/graphql/types";
import { useNavigation } from "@refinedev/core";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import dayjs from "dayjs";
import React from "react";
import styles from "../index.module.css";
import { Badge } from "antd";
import { Text } from "@/components/text";

type CalendarUpcomingEventProps = {
  item: GetFieldsFromList<UpcomingEventsQuery>;
};

export const CalendarUpcomingEvent: React.FC<CalendarUpcomingEventProps> = ({
  item,
}) => {
  const { show } = useNavigation();
  const { id, title, startDate, endDate, color } = item;

  const isToday = dayjs.utc(startDate).isSame(dayjs.utc(), "day");
  const isTomorrow = dayjs
    .utc(startDate)
    .isSame(dayjs.utc().add(1, "day"), "day");

  const isAllDayEvent =
    dayjs.utc(startDate).isSame(dayjs.utc(startDate), "day") &&
    dayjs.utc(endDate).isSame(dayjs.utc(endDate), "day");

  const renderDate = () => {
    if (isToday) return "Today";

    if (isTomorrow) return "Tomorrow";

    return dayjs.utc(startDate).format("MMM DD");
  };

  const renderTime = () => {
    if (isAllDayEvent) {
      return "All Day";
    }

    return `${dayjs.utc(startDate).format("hh:mm")} - ${dayjs
      .utc(endDate)
      .format("hh:mm")}`;
  };

  return (
    <div
      key={id}
      onClick={() => show("events", item.id)}
      className={styles.item}
    >
      <div className={styles.date}>
        <Badge color={color} className={styles.badge} />
        <Text size="xs">{`${renderDate()}, ${renderTime()}`}</Text>
      </div>
      <Text ellipsis={{ tooltip: true }} strong className={styles.title}>
        {title}
      </Text>
    </div>
  );
};
