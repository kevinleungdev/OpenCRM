import { Authenticated, Refine } from "@refinedev/core";

import { ErrorComponent } from "@refinedev/antd";
import "@refinedev/antd/dist/reset.css";

import { Layout } from "@/components";
import { resources, themeConfig } from "@/config";
import { authProvider } from "@/providers/auth";
import { dataProvider } from "@/providers/data";
import { DashboardPage } from "@/routes/dashboard";
import routerProvider, {
  CatchAllNavigate,
  NavigateToResource,
} from "@refinedev/react-router-v6";
import { App as AntdApp, ConfigProvider } from "antd";
import { BrowserRouter, Outlet, Route, Routes } from "react-router-dom";
import { ForgotPasswordPage } from "./routes/forgot-password";
import { LoginPage } from "./routes/login";
import { RegisterPage } from "./routes/register";
import UpdatePasswordPage from "./routes/update-password";
import { FullscreenLoading } from "./components/fullscreen-loading";

import "@/utilities/init-dayjs";
import "@refinedev/antd/dist/reset.css";
import "./styles/antd.css";
import "./styles/fc.css";
import "./styles/index.css";
import {
  CompanyCreatePage,
  CompanyEditPage,
  CompanyListPage,
} from "./routes/companies";

function App() {
  // This hook is used to automatically login the user.
  // We use this hook to skip the login page and demonstrate the application more quickly.
  // const { loading } = useAutoLoginForDemo();
  // if (loading) {
  //   return <FullscreenLoading />;
  // }

  return (
    <BrowserRouter>
      <ConfigProvider theme={themeConfig}>
        <AntdApp>
          <Refine
            authProvider={authProvider}
            dataProvider={dataProvider}
            routerProvider={routerProvider}
            resources={resources}
            options={{
              syncWithLocation: true,
              warnWhenUnsavedChanges: true,
              title: {
                text: import.meta.env.VITE_APP_TITLE,
              },
            }}
          >
            <Routes>
              <Route
                element={
                  <Authenticated
                    key="authenticated-layout"
                    fallback={<CatchAllNavigate to="/login" />}
                  >
                    <Layout>
                      <Outlet />
                    </Layout>
                  </Authenticated>
                }
              >
                <Route index element={<DashboardPage />} />
                <Route
                  path="/companies"
                  element={
                    <CompanyListPage>
                      <Outlet />
                    </CompanyListPage>
                  }
                >
                  <Route path="create" element={<CompanyCreatePage />} />
                </Route>
                <Route
                  path="/companies/edit/:id"
                  element={<CompanyEditPage />}
                />
                <Route path="*" element={<ErrorComponent />} />
              </Route>
              <Route
                element={
                  // We've use the fallback property to render the <Outlet /> component inside the wrapper Route of the login page.
                  // This allow us to render the login page when the user is not authenticated and redirect the user to /dashboard page when the user is authenticated
                  <Authenticated key="authenticated-auth" fallback={<Outlet />}>
                    <NavigateToResource resource="dashboard" />
                  </Authenticated>
                }
              >
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route
                  path="/forgot-password"
                  element={<ForgotPasswordPage />}
                />
                <Route
                  path="/update-password"
                  element={<UpdatePasswordPage />}
                />
              </Route>
            </Routes>
          </Refine>
        </AntdApp>
      </ConfigProvider>
    </BrowserRouter>
  );
}

export default App;
