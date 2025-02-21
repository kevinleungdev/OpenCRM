import { CompaniesSelectQuery } from "@/graphql/types";
import { useSelect } from "@refinedev/antd";
import { GetFieldsFromList } from "@refinedev/nestjs-query";
import gql from "graphql-tag";

const COMPANIES_SELECT_QUERY = gql`
  query CompaniesSelect(
    $filter: CompanyFilter!
    $sorting: [CompanySort!]
    $paging: OffsetPaging!
  ) {
    companies(filter: $filter, sorting: $sorting, paging: $paging) {
      nodes {
        id
        name
        avatarUrl
      }
    }
  }
`;

export const UseCompaniesSelect = () => {
  return useSelect<GetFieldsFromList<CompaniesSelectQuery>>({
    resource: "companies",
    optionLabel: "name",
    meta: {
      gqlQuery: COMPANIES_SELECT_QUERY,
    },
  });
};
