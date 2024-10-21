import { Text } from "@/components";
import { DashboardTotalRevenueQuery } from "@/graphql/types";
import { currencyNumber } from "@/utilities";
import { DollarOutlined } from "@ant-design/icons";
import { GaugeConfig } from "@ant-design/plots/es/interface";
import { useList } from "@refinedev/core";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import { Card, Skeleton, Space } from "antd";
import React, { Suspense } from "react";
import { DASHBOARD_TOTAL_REVENUE_QUERY } from "./queries";

const Gauge = React.lazy(() => import("@ant-design/plots/es/components/gauge"));

type DealStage = GetFieldsFromList<DashboardTotalRevenueQuery>;

export const DashboardTotalRevenueChart: React.FC = () => {
  const {
    data: expectedRevenueData,
    error: expectedRevenueError,
    isError: expectedRevenueIsError,
    isLoading: expectedRevenueIsLoading,
  } = useList<DealStage>({
    resource: "dealStages",
    filters: [
      {
        field: "title",
        operator: "nin",
        value: ["LOST", "WON"],
      },
    ],
    meta: {
      gqlQuery: DASHBOARD_TOTAL_REVENUE_QUERY,
    },
  });

  const {
    data: realizedRevenueData,
    error: realizedRevenueError,
    isError: realizedRevenueIsError,
    isLoading: realizedRevenueIsLoading,
  } = useList<DealStage>({
    resource: "dealStages",
    filters: [
      {
        field: "title",
        operator: "eq",
        value: "WON",
      },
    ],
    meta: {
      gqlQuery: DASHBOARD_TOTAL_REVENUE_QUERY,
    },
  });

  if (expectedRevenueIsError || realizedRevenueIsError) {
    console.error(
      "Error fetching dashboard data",
      expectedRevenueError,
      realizedRevenueError,
    );
    return null;
  }

  const totalRealizationRevenue = (realizedRevenueData?.data || []).map(
    (item) => item.dealsAggregate?.[0]?.sum?.value,
  )[0];

  const totalExpectedRevenue = (expectedRevenueData?.data || []).reduce(
    (prev, curr) => prev + (curr?.dealsAggregate?.[0]?.sum?.value || 0),
    0,
  );

  const totalRealizationPercentageOfExpected =
    totalRealizationRevenue && totalExpectedRevenue
      ? (totalRealizationRevenue / totalExpectedRevenue) * 100
      : 0;

  const config: GaugeConfig = {
    animation:
      expectedRevenueIsLoading || realizedRevenueIsLoading ? false : true,
    supportCSSTransform: true,
    // antd expects a percentage value between 0 and 1
    percent: totalRealizationPercentageOfExpected / 100,
    range: {
      color: "l(0) 0:#D9F7BE 1:#52C41A",
    },
    axis: {
      tickLine: {
        style: {
          stroke: "#BFBFBF",
        },
      },
      label: {
        formatter(v) {
          return Number(v) * 100;
        },
      },
      subTickLine: {
        count: 3,
      },
    },
    indicator: {
      pointer: {
        style: {
          fontSize: 4,
          stroke: "#BFBFBF",
          lineWidth: 2,
        },
      },
      pin: {
        style: {
          r: 8,
          lineWidth: 2,
          stroke: "#BFBFBF",
        },
      },
    },
    statistic: {
      content: {
        formatter: (datum) => {
          return `${(datum?.percent * 100).toFixed(2)}%`;
        },
        style: {
          color: "rgba(0,0,0,0.85)",
          fontWeight: 500,
          fontSize: "24px",
        },
      },
    },
  };

  return (
    <Card
      style={{
        height: "100%",
      }}
      styles={{
        header: {
          padding: "16px",
        },
        body: {
          padding: "0 32px 32px 32px",
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignContent: "center",
        },
      }}
      title={
        <div
          style={{
            display: "flex",
            gap: "8px",
          }}
        >
          <DollarOutlined />
          <Text size="sm">Total revenue (yearly)</Text>
        </div>
      }
    >
      <Suspense>
        <Gauge {...config} padding={0} height={280} width={280} />
      </Suspense>

      <div
        style={{
          display: "flex",
          gap: "32px",
          justifyContent: "center",
        }}
      >
        <Space direction="vertical" size={0}>
          <Text size="xs" className="secondary">
            Expected
          </Text>
          {!expectedRevenueIsLoading || !realizedRevenueIsLoading ? (
            <Text
              size="md"
              className="primary"
              style={{
                minWidth: "100px",
              }}
            >
              {currencyNumber(totalExpectedRevenue || 0)}
            </Text>
          ) : (
            <Skeleton.Button
              style={{
                width: "100px",
                height: "16px",
                marginTop: "6px",
              }}
              active={true}
            />
          )}
        </Space>

        <Space direction="vertical" size={0}>
          <Text size="xs" className="secondary">
            Realized
          </Text>
          {!expectedRevenueIsLoading || !realizedRevenueIsLoading ? (
            <Text
              size="md"
              className="primary"
              style={{
                minWidth: "100px",
              }}
            >
              {currencyNumber(totalRealizationRevenue || 0)}
            </Text>
          ) : (
            <Skeleton.Button
              style={{
                width: "100px",
                height: "16px",
                marginTop: "6px",
              }}
              active={true}
            />
          )}
        </Space>
      </div>
    </Card>
  );
};
