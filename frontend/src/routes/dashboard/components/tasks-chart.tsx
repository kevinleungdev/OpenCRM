import { Text } from "@/components";
import { DashboardTasksChartQuery } from "@/graphql/types";
import { ProjectOutlined, RightCircleOutlined } from "@ant-design/icons";
import { PieConfig } from "@ant-design/plots/es/interface";
import { useList, useNavigation } from "@refinedev/core";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import { Button, Card } from "antd";
import React, { Suspense, useMemo } from "react";
import { DASHBOARD_TASKS_CHART_QUERY } from "./queries";

const Pie = React.lazy(() => import("@ant-design/plots/es/components/pie"));

export const DashboardTasksChart: React.FC = () => {
  const { list } = useNavigation();

  const { data, isError, error } = useList<
    GetFieldsFromList<DashboardTasksChartQuery>
  >({
    resource: "taskStages",
    pagination: {
      pageSize: 4,
    },
    meta: {
      gqlQuery: DASHBOARD_TASKS_CHART_QUERY,
    },
  });

  if (isError) {
    console.error("Error fetching task chart data", error);
  }

  const taskData = useMemo(() => {
    if (!data?.data.length) {
      return [];
    }

    return data?.data
      .map((stage) => ({
        title: stage.title,
        value: stage.tasksAggregate?.[0]?.count?.id || 0,
      }))
      .filter(
        (stage) =>
          stage.value !== null && stage.value !== undefined && stage.value > 0,
      )
      .sort((a, b) => (b.value ?? 0) - (a.value ?? 0));
  }, [data?.data]);

  const COLORS = [
    "#BAE0FF",
    "#69B1FF",
    "#1677FF",
    "#0958D9",
    "#10239E",
    "#061178",
    "#030852",
    "#03052E",
    "#000B0A",
    "#000000",
  ];

  const config: PieConfig = {
    width: 168,
    height: 168,
    data: taskData,
    angleField: "value",
    colorField: "title",
    color: COLORS,
    legend: false,
    radius: 1,
    innerRadius: 0.6,
    label: false,
    syncViewPadding: true,
    statistic: {
      title: false,
      content: false,
    },
  };

  return (
    <Card
      style={{
        height: "100%",
      }}
      styles={{
        header: {
          padding: "8px 16px",
        },
        body: {
          padding: "32px",
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
          <ProjectOutlined />
          <Text size="sm" style={{ marginLeft: ".5rem" }}>
            Tasks
          </Text>
        </div>
      }
      extra={
        <Button onClick={() => list("tasks")} icon={<RightCircleOutlined />}>
          See kanban board
        </Button>
      }
    >
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          position: "relative",
        }}
      >
        <Suspense>
          <Pie {...config} />
        </Suspense>
        <div
          style={{
            display: "flex",
            flexWrap: "wrap",
            width: "100%",
            marginTop: "48px",
          }}
        >
          {taskData?.map((item, index) => (
            <div
              key={index}
              style={{
                display: "flex",
                width: "50%",
                marginTop: "8px",
                alignItems: "center",
              }}
            >
              <div
                style={{
                  height: 8,
                  width: 8,
                  backgroundColor: COLORS[index],
                  marginRight: ".5rem",
                }}
              />
              <Text
                size="md"
                style={{
                  textTransform: "capitalize",
                  whiteSpace: "nowrap",
                }}
              >
                {item.title.toLowerCase()}
              </Text>
            </div>
          ))}
        </div>
      </div>
    </Card>
  );
};
