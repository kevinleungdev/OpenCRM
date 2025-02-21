import type * as Types from "./schema.types";

export type UpcomingEventsQueryVariables = Types.Exact<{
  filter: Types.EventFilter;
  sorting?: Types.InputMaybe<Array<Types.EventSort> | Types.EventSort>;
  paging: Types.OffsetPaging;
}>;

export type UpcomingEventsQuery = {
  events: Pick<Types.EventConnection, "totalCount"> & {
    nodes: Array<
      Pick<Types.Event, "id" | "title" | "color" | "startDate" | "endDate">
    >;
  };
};

export type AccountSettingsGetUserQueryVariables = Types.Exact<{
  id: Types.Scalars["ID"]["input"];
}>;

export type AccountSettingsGetUserQuery = {
  user: Pick<
    Types.User,
    "id" | "name" | "email" | "avatarUrl" | "jobTitle" | "phone" | "timezone"
  >;
};

export type AccountSettingsUpdateUserMutationVariables = Types.Exact<{
  input: Types.UpdateOneUserInput;
}>;

export type AccountSettingsUpdateUserMutation = {
  updateOneUser: Pick<
    Types.User,
    "id" | "name" | "email" | "avatarUrl" | "jobTitle" | "phone" | "timezone"
  >;
};

export type CompaniesSelectQueryVariables = Types.Exact<{
  filter: Types.CompanyFilter;
  sorting?: Types.InputMaybe<Array<Types.CompanySort> | Types.CompanySort>;
  paging: Types.OffsetPaging;
}>;

export type CompaniesSelectQuery = {
  companies: { nodes: Array<Pick<Types.Company, "id" | "name" | "avatarUrl">> };
};

export type ContactsSelectQueryVariables = Types.Exact<{
  filter: Types.ContactFilter;
  sorting?: Types.InputMaybe<Array<Types.ContactSort> | Types.ContactSort>;
  paging: Types.OffsetPaging;
}>;

export type ContactsSelectQuery = {
  contacts: { nodes: Array<Pick<Types.Contact, "id" | "name" | "avatarUrl">> };
};

export type DealStagesSelectQueryVariables = Types.Exact<{
  filter: Types.DealStageFilter;
  sorting?: Types.InputMaybe<Array<Types.DealStageSort> | Types.DealStageSort>;
  paging: Types.OffsetPaging;
}>;

export type DealStagesSelectQuery = {
  dealStages: { nodes: Array<Pick<Types.DealStage, "id" | "title">> };
};

export type UsersSelectQueryVariables = Types.Exact<{
  filter: Types.UserFilter;
  sorting?: Types.InputMaybe<Array<Types.UserSort> | Types.UserSort>;
  paging: Types.OffsetPaging;
}>;

export type UsersSelectQuery = {
  users: { nodes: Array<Pick<Types.User, "id" | "name" | "avatarUrl">> };
};

export type LoginMutationVariables = Types.Exact<{
  email: Types.Scalars["String"]["input"];
  password?: Types.InputMaybe<Types.Scalars["String"]["input"]>;
}>;

export type LoginMutation = {
  login: Pick<Types.AuthResponse, "accessToken" | "refreshToken">;
};

export type RegisterMutationVariables = Types.Exact<{
  email: Types.Scalars["String"]["input"];
  password: Types.Scalars["String"]["input"];
}>;

export type RegisterMutation = { register: Pick<Types.User, "id" | "email"> };

export type MeQueryVariables = Types.Exact<{ [key: string]: never }>;

export type MeQuery = {
  me: Pick<
    Types.User,
    "id" | "name" | "email" | "phone" | "jobTitle" | "timezone" | "avatarUrl"
  >;
};

export type RefreshTokenMutationVariables = Types.Exact<{
  refreshToken: Types.Scalars["String"]["input"];
}>;

export type RefreshTokenMutation = {
  refreshToken: Pick<Types.AuthResponse, "accessToken" | "refreshToken">;
};

export type CompanyContactsTableQueryVariables = Types.Exact<{
  filter: Types.ContactFilter;
  sorting?: Types.InputMaybe<Array<Types.ContactSort> | Types.ContactSort>;
  paging?: Types.InputMaybe<Types.OffsetPaging>;
}>;

export type CompanyContactsTableQuery = {
  contacts: Pick<Types.ContactConnection, "totalCount"> & {
    nodes: Array<
      Pick<
        Types.Contact,
        "id" | "name" | "avatarUrl" | "jobTitle" | "email" | "phone" | "status"
      >
    >;
  };
};

export type CompanyContactsGetCompanyQueryVariables = Types.Exact<{
  id: Types.Scalars["ID"]["input"];
}>;

export type CompanyContactsGetCompanyQuery = {
  company: Pick<Types.Company, "id" | "name"> & {
    salesOwner: Pick<Types.User, "id">;
  };
};

export type CompanyDealsTableQueryVariables = Types.Exact<{
  filter: Types.DealFilter;
  sorting?: Types.InputMaybe<Array<Types.DealSort> | Types.DealSort>;
  paging?: Types.InputMaybe<Types.OffsetPaging>;
}>;

export type CompanyDealsTableQuery = {
  deals: Pick<Types.DealConnection, "totalCount"> & {
    nodes: Array<
      Pick<Types.Deal, "id" | "title" | "value"> & {
        stage?: Types.Maybe<Pick<Types.DealStage, "id" | "title">>;
        dealOwner: Pick<Types.User, "id" | "name" | "avatarUrl">;
        dealContact: Pick<Types.Contact, "id" | "name" | "avatarUrl">;
      }
    >;
  };
};

export type CompanyTotalDealsAmountQueryVariables = Types.Exact<{
  id: Types.Scalars["ID"]["input"];
}>;

export type CompanyTotalDealsAmountQuery = {
  company: {
    dealsAggregate: Array<{
      sum?: Types.Maybe<Pick<Types.CompanyDealsSumAggregate, "value">>;
    }>;
  };
};

export type CompanyInfoQueryVariables = Types.Exact<{
  id: Types.Scalars["ID"]["input"];
}>;

export type CompanyInfoQuery = {
  company: Pick<
    Types.Company,
    | "id"
    | "totalRevenue"
    | "industry"
    | "companySize"
    | "businessType"
    | "country"
    | "website"
  >;
};

export type CompanyCreateCompanyNoteMutationVariables = Types.Exact<{
  input: Types.CreateOneCompanyNoteInput;
}>;

export type CompanyCreateCompanyNoteMutation = {
  createOneCompanyNote: Pick<Types.CompanyNote, "id" | "note">;
};

export type CompanyCompanyNotesQueryVariables = Types.Exact<{
  filter: Types.CompanyNoteFilter;
  sorting?: Types.InputMaybe<
    Array<Types.CompanyNoteSort> | Types.CompanyNoteSort
  >;
  paging: Types.OffsetPaging;
}>;

export type CompanyCompanyNotesQuery = {
  companyNotes: Pick<Types.CompanyNoteConnection, "totalCount"> & {
    nodes: Array<
      Pick<Types.CompanyNote, "id" | "note" | "createdAt"> & {
        createdBy: Pick<Types.User, "id" | "name" | "updatedAt" | "avatarUrl">;
      }
    >;
  };
};

export type CompanyUpdateCompanyNoteMutationVariables = Types.Exact<{
  input: Types.UpdateOneCompanyNoteInput;
}>;

export type CompanyUpdateCompanyNoteMutation = {
  updateOneCompanyNote: Pick<Types.CompanyNote, "id" | "note">;
};

export type CompanyQuotesTableQueryVariables = Types.Exact<{
  filter?: Types.InputMaybe<Types.QuoteFilter>;
  sorting?: Types.InputMaybe<Array<Types.QuoteSort> | Types.QuoteSort>;
  paging: Types.OffsetPaging;
}>;

export type CompanyQuotesTableQuery = {
  quotes: Pick<Types.QuoteConnection, "totalCount"> & {
    nodes: Array<
      Pick<Types.Quote, "id" | "title" | "status" | "total"> & {
        company: Pick<Types.Company, "id" | "name">;
        contact: Pick<Types.Contact, "id" | "name" | "avatarUrl">;
        salesOwner: Pick<Types.User, "id" | "name" | "avatarUrl">;
      }
    >;
  };
};

export type CompanyTitleFormMutationVariables = Types.Exact<{
  input: Types.UpdateOneCompanyInput;
}>;

export type CompanyTitleFormMutation = {
  updateOneCompany: Pick<Types.Company, "id" | "name" | "avatarUrl"> & {
    salesOwner: Pick<Types.User, "id" | "name" | "avatarUrl">;
  };
};

export type CompanyTitleQueryVariables = Types.Exact<{
  id: Types.Scalars["ID"]["input"];
}>;

export type CompanyTitleQuery = {
  company: Pick<Types.Company, "id" | "name" | "createdAt" | "avatarUrl"> & {
    salesOwner: Pick<Types.User, "id" | "name" | "avatarUrl">;
  };
};

export type CreateCompanyMutationVariables = Types.Exact<{
  input: Types.CreateOneCompanyInput;
}>;

export type CreateCompanyMutation = {
  createOneCompany: Pick<Types.Company, "id" | "name"> & {
    salesOwner: Pick<Types.User, "id" | "name" | "avatarUrl">;
  };
};

export type CompaniesTableQueryVariables = Types.Exact<{
  filter: Types.CompanyFilter;
  sorting: Array<Types.CompanySort> | Types.CompanySort;
  paging: Types.OffsetPaging;
}>;

export type CompaniesTableQuery = {
  companies: Pick<Types.CompanyConnection, "totalCount"> & {
    nodes: Array<
      Pick<Types.Company, "id" | "name" | "avatarUrl"> & {
        dealsAggregate: Array<{
          sum?: Types.Maybe<Pick<Types.CompanyDealsSumAggregate, "value">>;
        }>;
        salesOwner: Pick<Types.User, "id" | "name" | "avatarUrl">;
        contacts: {
          nodes: Array<Pick<Types.Contact, "id" | "name" | "avatarUrl">>;
        };
      }
    >;
  };
};

export type LatestActivitiesDealsQueryVariables = Types.Exact<{
  filter: Types.DealFilter;
  sorting: Array<Types.DealSort> | Types.DealSort;
  paging: Types.OffsetPaging;
}>;

export type LatestActivitiesDealsQuery = {
  deals: {
    nodes: Array<
      Pick<Types.Deal, "id" | "title"> & {
        stage?: Types.Maybe<Pick<Types.DealStage, "id" | "title">>;
        company: Pick<Types.Company, "id" | "name" | "avatarUrl">;
      }
    >;
  };
};

export type LatestActivitiesAuditsQueryVariables = Types.Exact<{
  filter: Types.AuditFilter;
  sorting: Array<Types.AuditSort> | Types.AuditSort;
  paging: Types.OffsetPaging;
}>;

export type LatestActivitiesAuditsQuery = {
  audits: {
    nodes: Array<
      Pick<
        Types.Audit,
        "id" | "action" | "targetEntity" | "targetId" | "createdAt"
      > & {
        changes: Array<Pick<Types.AuditChange, "field" | "from" | "to">>;
        user?: Types.Maybe<Pick<Types.User, "id" | "name" | "avatarUrl">>;
      }
    >;
  };
};

export type DashboardDealsChartQueryVariables = Types.Exact<{
  filter: Types.DealStageFilter;
  sorting?: Types.InputMaybe<Array<Types.DealStageSort> | Types.DealStageSort>;
  paging: Types.OffsetPaging;
}>;

export type DashboardDealsChartQuery = {
  dealStages: {
    nodes: Array<
      Pick<Types.DealStage, "title"> & {
        dealsAggregate: Array<{
          groupBy?: Types.Maybe<
            Pick<
              Types.DealStageDealsAggregateGroupBy,
              "closeDateMonth" | "closeDateYear"
            >
          >;
          sum?: Types.Maybe<Pick<Types.DealStageDealsSumAggregate, "value">>;
        }>;
      }
    >;
  };
};

export type DashboardTasksChartQueryVariables = Types.Exact<{
  filter: Types.TaskStageFilter;
  sorting?: Types.InputMaybe<Array<Types.TaskStageSort> | Types.TaskStageSort>;
  paging: Types.OffsetPaging;
}>;

export type DashboardTasksChartQuery = {
  taskStages: {
    nodes: Array<
      Pick<Types.TaskStage, "title"> & {
        tasksAggregate: Array<{
          count?: Types.Maybe<Pick<Types.TaskStageTasksCountAggregate, "id">>;
        }>;
      }
    >;
  };
};

export type DashboardTotalRevenueQueryVariables = Types.Exact<{
  filter: Types.DealStageFilter;
  sorting?: Types.InputMaybe<Array<Types.DealStageSort> | Types.DealStageSort>;
  paging: Types.OffsetPaging;
}>;

export type DashboardTotalRevenueQuery = {
  dealStages: {
    nodes: Array<
      Pick<Types.DealStage, "title"> & {
        dealsAggregate: Array<{
          sum?: Types.Maybe<Pick<Types.DealStageDealsSumAggregate, "value">>;
        }>;
      }
    >;
  };
};

export type DashboardTotalCountsQueryVariables = Types.Exact<{
  [key: string]: never;
}>;

export type DashboardTotalCountsQuery = {
  companies: Pick<Types.CompanyConnection, "totalCount">;
  contacts: Pick<Types.ContactConnection, "totalCount">;
  deals: Pick<Types.DealConnection, "totalCount">;
};
