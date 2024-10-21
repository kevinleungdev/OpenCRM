import type { IResourceItem } from "@refinedev/core";
import {
  AuditOutlined,
  CalculatorOutlined,
  ContainerOutlined,
  CrownOutlined,
  DashboardOutlined,
  FundViewOutlined,
  ProjectOutlined,
  SettingOutlined,
  ShopOutlined,
  StockOutlined,
  TeamOutlined,
} from "@ant-design/icons";

export const resources: IResourceItem[] = [
  {
    name: "dashboard",
    list: "/",
    meta: {
      label: "Dashboard",
      // @ts-ignore
      icon: <DashboardOutlined />,
    },
  },
  {
    name: "events",
    list: "/calendar",
    create: "/calendar/create",
    edit: "/calendar/edit/:id",
    show: "/calendar/show/:id",
    meta: {
      label: "Calendar",
      // @ts-ignore
      icon: <CalculatorOutlined />,
    },
  },
  {
    name: "scrumboard",
    meta: {
      label: "Scrumboard",
      // @ts-ignore
      icon: <ProjectOutlined />,
    },
  },
  {
    name: "tasks",
    list: "/scrumboard/kanban",
    create: "/scrumboard/kanban/create",
    edit: "/scrumboard/kanban/edit/:id",
    show: "/scrumboard/kanban/show/:id",
    meta: {
      label: "Project Kanban",
      // @ts-ignore
      icon: <FundViewOutlined />,
      parent: "scrumboard",
    },
  },
  {
    name: "taskStages",
    list: "scrumboard/kanban/stages",
    create: "/scrumboard/kanban/stages/create",
    edit: "/scrumboard/kanban/stages/edit/:id",
    meta: {
      hide: true,
    },
  },
  {
    name: "deals",
    list: "/scrumboard/sales",
    create: "/scrumboard/sales/create",
    edit: "/scrumboard/sales/edit/:id",
    meta: {
      label: "Sales Pipeline",
      // @ts-ignore
      icon: <StockOutlined />,
      parent: "scrumboard",
    },
  },
  {
    name: "dealStages",
    create: "/scrumboard/sales/stages/create",
    edit: "/scrumboard/sales/stages/edit/:id",
    list: "/scrumboard/sales",
    meta: {
      hide: true,
    },
  },
  {
    name: "companies",
    create: "/companies/create",
    edit: "/companies/edit/:id",
    list: "/companies",
    show: "/companies/show/:id",
    meta: {
      label: "Companies",
      // @ts-ignore
      icon: <ShopOutlined />,
    },
  },
  {
    name: "companies",
    identifier: "sales-companies",
    create: "/scrumboard/sales/create/company/create",
    meta: {
      hide: true,
    },
  },
  {
    name: "contacts",
    create: "/contacts/create",
    edit: "/contacts/edit/:id",
    list: "/contacts",
    show: "/contacts/show/:id",
    meta: {
      label: "Contacts",
      // @ts-ignore
      icon: <TeamOutlined />,
    },
  },
  {
    name: "quotes",
    list: "/quotes",
    create: "/quotes/create",
    edit: "/quotes/edit/:id",
    show: "/quotes/show/:id",
    meta: {
      label: "Quotes",
      // @ts-ignore
      icon: <ContainerOutlined />,
    },
  },
  {
    name: "administration",
    meta: {
      label: "Administration",
      // @ts-ignore
      icon: <CrownOutlined />,
    },
  },
  {
    name: "settings",
    list: "/administration/settings",
    meta: {
      label: "Settings",
      // @ts-ignore
      icon: <SettingOutlined />,
      parent: "administration",
    },
  },
  {
    name: "audits",
    list: "/administration/audit-log",
    meta: {
      label: "Audit Log",
      // @ts-ignore
      icon: <AuditOutlined />,
      parent: "administration",
    },
  },
];
