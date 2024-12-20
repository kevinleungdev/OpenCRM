import { CompaniesTableQuery } from "@/graphql/types";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import { TableProps } from "antd";
import React from "react";

type Company = GetFieldsFromList<CompaniesTableQuery>;

type Props = {
  tableProps: TableProps<Company>;
  setCurrent: (current: number) => void;
  setPageSize: (pageSize: number) => void;
};

export const CompaniesCardView: React.FC<Props> = ({
  tableProps: { dataSource, pagination, loading },
  setCurrent,
  setPageSize,
}) => {
  return <div>CompaniesCardView</div>;
};
