import type * as Types from "./schema.types";

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

export type LoginMutationVariables = Types.Exact<{
  email: Types.Scalars["String"]["input"];
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
