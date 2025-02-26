import { CompaniesTableQuery } from "@/graphql/types";
import {
  AppstoreOutlined,
  SearchOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import { List, useTable } from "@refinedev/antd";
import { HttpError } from "@refinedev/core";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import { Form, Grid, Input, Radio, Space, Spin } from "antd";
import { debounce } from "lodash";
import React, { FC, PropsWithChildren, useState } from "react";
import { COMPANY_TABLE_QUERY } from "./queries";
import { ListTitleButton } from "@/components";
import { CompaniesCardView, CompaniesTableView } from "./components";

type View = "card" | "table";

export const CompanyListPage: FC<PropsWithChildren> = ({ children }) => {
  const [view, setView] = useState<View>("card");
  const screens = Grid.useBreakpoint();

  const {
    tableProps,
    tableQuery,
    searchFormProps,
    filters,
    sorters,
    setCurrent,
    setPageSize,
    setFilters,
  } = useTable<
    GetFieldsFromList<CompaniesTableQuery>,
    HttpError,
    { name: string }
  >({
    resource: "companies",
    onSearch: (values) => {
      return [
        {
          field: "name",
          operator: "contains",
          value: values.name,
        },
      ];
    },
    sorters: {
      initial: [
        {
          field: "createdAt",
          order: "desc",
        },
      ],
    },
    filters: {
      initial: [
        {
          field: "name",
          operator: "contains",
          value: undefined,
        },
        {
          field: "contacts.id",
          operator: "in",
          value: undefined,
        },
      ],
    },
    pagination: {
      pageSize: 12,
    },
    meta: {
      gqlQuery: COMPANY_TABLE_QUERY,
    },
  });

  const onViewChange = (value: View) => {
    setView(value);
    setFilters([], "replace");

    searchFormProps.form?.resetFields();
  };

  const onSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    searchFormProps.onFinish?.({
      name: e.target.value ?? "",
    });
  };
  const debouncedOnChange = debounce(onSearch, 500);

  return (
    <div className="page-container">
      <List
        breadcrumb={false}
        headerButtons={() => {
          return (
            <Space style={{ marginTop: screens.xs ? "1.6rem" : undefined }}>
              <Form {...searchFormProps} layout="inline">
                <Form.Item name="name" noStyle>
                  <Input
                    size="large"
                    // @ts-ignore
                    prefix={<SearchOutlined className="anticon tertiary" />}
                    suffix={
                      <Spin size="small" spinning={tableQuery.isFetching} />
                    }
                    placeholder="Search by name"
                    onChange={debouncedOnChange}
                  />
                </Form.Item>
              </Form>
              {!screens.xs ? (
                <Radio.Group
                  size="large"
                  value={view}
                  onChange={(e) => onViewChange(e.target.value)}
                >
                  <Radio.Button value="table">
                    <UnorderedListOutlined />
                  </Radio.Button>
                  <Radio.Button value="card">
                    <AppstoreOutlined />
                  </Radio.Button>
                </Radio.Group>
              ) : null}
            </Space>
          );
        }}
        contentProps={{
          style: {
            marginTop: "28px",
          },
        }}
        title={
          <ListTitleButton toPath="companies" buttonText="Add new company" />
        }
      >
        {view === "table" ? (
          <CompaniesTableView
            tableProps={tableProps}
            filters={filters}
            sorters={sorters}
          />
        ) : (
          <CompaniesCardView
            tableProps={tableProps}
            setCurrent={setCurrent}
            setPageSize={setPageSize}
          />
        )}
      </List>
      {children}
    </div>
  );
};
