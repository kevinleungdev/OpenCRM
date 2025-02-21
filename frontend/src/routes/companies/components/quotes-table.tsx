import { Text } from "@/components";
import { Participants } from "@/components/participants";
import { QuoteStatusTag } from "@/components/tags";
import { CompanyQuotesTableQuery } from "@/graphql/types";
import { useUsersSelect } from "@/hooks/useUsersSelect";
import { currencyNumber } from "@/utilities";
import {
  ContainerOutlined,
  ExportOutlined,
  PlusCircleOutlined,
  SearchOutlined,
} from "@ant-design/icons";
import { FilterDropdown, ShowButton, useTable } from "@refinedev/antd";
import { useNavigation } from "@refinedev/core";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import { Button, Card, Input, Select, Space, Table } from "antd";
import { useMemo } from "react";
import { Link, useParams } from "react-router-dom";
import { COMPANY_QUOTES_TABLE_QUERY } from "./queries";

type Props = {
  style?: React.CSSProperties;
};

type Quote = GetFieldsFromList<CompanyQuotesTableQuery>;

export const CompanyQuotesTable: React.FC<Props> = ({ style }) => {
  const { listUrl } = useNavigation();
  const params = useParams();

  const { tableProps, filters, setFilters } = useTable<Quote>({
    resource: "quotes",
    syncWithLocation: false,
    sorters: {
      initial: [
        {
          field: "updatedAt",
          order: "desc",
        },
      ],
    },
    filters: {
      initial: [
        {
          field: "title",
          operator: "contains",
          value: "",
        },
        {
          field: "status",
          operator: "in",
          value: undefined,
        },
      ],
      permanent: [
        {
          field: "company.id",
          operator: "eq",
          value: params.id,
        },
      ],
    },
    meta: {
      gqlQuery: COMPANY_QUOTES_TABLE_QUERY,
    },
  });

  const { selectProps: selectPropsUsers } = useUsersSelect();

  const showResetFilters = useMemo(() => {
    return filters?.filter((filter) => {
      if ("field" in filter && filter.field === "company.id") {
        return false;
      }

      if (!filter.value) {
        return false;
      }

      return true;
    });
  }, [filters]);

  const hasData = (tableProps?.dataSource?.length ?? 0) > 0;

  return (
    <Card
      style={style}
      styles={{
        header: {
          borderBottom: "1px solid #D9D9D9",
          marginBottom: "1px",
        },
        body: {
          padding: 0,
        },
      }}
      title={
        <Space size="middle">
          <ContainerOutlined />
          <Text>Quotes</Text>

          {showResetFilters?.length > 0 && (
            <Button size="small" onClick={() => setFilters([], "replace")}>
              Reset filters
            </Button>
          )}
        </Space>
      }
    >
      {!hasData && (
        <Space
          direction="vertical"
          size={16}
          style={{
            padding: 16,
          }}
        >
          <Text>No quotes yet</Text>
          <Link to={listUrl("quotes")}>
            <PlusCircleOutlined
              style={{
                marginRight: 4,
              }}
            />{" "}
            Add quotes
          </Link>
        </Space>
      )}
      {hasData && (
        <Table
          {...tableProps}
          rowKey="id"
          pagination={{
            ...tableProps.pagination,
            showSizeChanger: false,
          }}
        >
          <Table.Column
            title="Quote Title"
            dataIndex="title"
            // @ts-ignore
            filterIcon={<SearchOutlined />}
            filterDropdown={(props) => (
              <FilterDropdown {...props}>
                <Input placeholder="Search Title" />
              </FilterDropdown>
            )}
          />
          <Table.Column<Quote>
            title="Total amount"
            dataIndex="total"
            sorter
            render={(_, record) => {
              return <Text>{currencyNumber(record.total || 0)}</Text>;
            }}
          />
          <Table.Column<Quote>
            title="Stage"
            dataIndex="status"
            render={(_, record) => {
              if (!record.status) return null;

              return <QuoteStatusTag status={record.status} />;
            }}
            filterDropdown={(props) => (
              <FilterDropdown {...props}>
                <Select
                  style={{ width: "200px" }}
                  mode="multiple"
                  placeholder="Select Stage"
                  options={statusOptions}
                />
              </FilterDropdown>
            )}
          />
          <Table.Column<Quote>
            dataIndex={["salesOwner", "id"]}
            title="Participants"
            render={(_, record) => {
              return (
                <Participants
                  useOne={record.salesOwner}
                  useTwo={record.contact}
                />
              );
            }}
            filterDropdown={(props) => (
              <FilterDropdown {...props}>
                <Select
                  style={{ width: "200px" }}
                  mode="multiple"
                  placeholder="Select Sales Owner"
                  {...selectPropsUsers}
                />
              </FilterDropdown>
            )}
          />
          <Table.Column<Quote>
            dataIndex="id"
            width={48}
            render={(value) => {
              return (
                <ShowButton
                  hideText
                  recordItemId={value}
                  size="small"
                  resource="contacts"
                  // @ts-ignore
                  icon={<ExportOutlined />}
                />
              );
            }}
          />
        </Table>
      )}{" "}
    </Card>
  );
};

const statusOptions: { label: string; value: string }[] = [
  {
    label: "Draft",
    value: "DRAFT",
  },
  {
    label: "Sent",
    value: "SENT",
  },
  {
    label: "Accepted",
    value: "ACCEPTED",
  },
];
