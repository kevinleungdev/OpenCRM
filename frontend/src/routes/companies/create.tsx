import { SelectOptionWithAvatar } from "@/components";
import {
  CreateCompanyMutation,
  CreateCompanyMutationVariables,
} from "@/graphql/types";
import { useUsersSelect } from "@/hooks/useUsersSelect";
import {
  DeleteOutlined,
  LeftOutlined,
  MailOutlined,
  PlusCircleOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { useModalForm } from "@refinedev/antd";
import {
  CreateResponse,
  HttpError,
  useCreateMany,
  useGetToPath,
  useGo,
} from "@refinedev/core";
import { GetFields, GetVariables } from "@refinedev/nestjs-query";
import {
  Button,
  Col,
  Form,
  Input,
  Modal,
  Row,
  Select,
  Space,
  Typography,
} from "antd";
import React from "react";
import { useLocation, useSearchParams } from "react-router-dom";
import { COMPANY_CREATE_MUTATION } from "./queries";

type Company = GetFields<CreateCompanyMutation>;

type Props = {
  isOverModal?: boolean;
};

type FormValues = GetVariables<CreateCompanyMutationVariables> & {
  contacts?: {
    name?: string;
    email?: string;
  }[];
};

export const CompanyCreatePage: React.FC<Props> = ({ isOverModal }) => {
  const getToPath = useGetToPath();

  const [searchParams] = useSearchParams();
  const { pathname } = useLocation();

  const go = useGo();

  const { formProps, modalProps, close, onFinish } = useModalForm<
    Company,
    HttpError,
    FormValues
  >({
    resource: "companies",
    action: "create",
    defaultVisible: true,
    redirect: false,
    warnWhenUnsavedChanges: !isOverModal,
    mutationMode: "pessimistic",
    meta: {
      gqlMutation: COMPANY_CREATE_MUTATION,
    },
  });

  const { selectProps, query } = useUsersSelect();
  const { mutateAsync: createManyMutateAsync } = useCreateMany();

  return (
    <Modal
      {...modalProps}
      mask={!isOverModal}
      onCancel={() => {
        close();
        go({
          to: searchParams.get("to") ?? getToPath({ action: "list" }) ?? "",
          query: {
            to: undefined,
          },
          options: {
            keepQuery: true,
          },
          type: "replace",
        });
      }}
      title="Add new company"
      width={512}
      // @ts-ignore
      closeIcon={<LeftOutlined />}
    >
      <Form
        {...formProps}
        layout="vertical"
        onFinish={async (values) => {
          try {
            const data = await onFinish({
              name: values.name,
              salesOwnerId: values.salesOwnerId,
            });

            const createdCompany = (data as CreateResponse<Company>)?.data;

            if ((values.contacts ?? [])?.length > 0) {
              await createManyMutateAsync({
                resource: "contacts",
                values:
                  values.contacts?.map((contact) => ({
                    ...contact,
                    companyId: createdCompany?.id,
                    salesOwnerId: createdCompany?.salesOwner?.id,
                  })) ?? [],
                successNotification: false,
              });

              go({
                to: searchParams.get("to") ?? pathname,
                query: {
                  companyId: createdCompany?.id,
                  to: undefined,
                },
                options: {
                  keepQuery: true,
                },
                type: "replace",
              });
            }
          } catch (error) {
            Promise.reject(error);
          }
        }}
      >
        <Form.Item
          label="Company name"
          name="name"
          rules={[{ required: true }]}
        >
          <Input placeholder="Please enter the company name" />
        </Form.Item>
        <Form.Item
          label="Sales owner"
          name="salesOwnerId"
          rules={[{ required: true }]}
        >
          <Select
            {...selectProps}
            options={
              query?.data?.data?.map((user) => ({
                label: (
                  <SelectOptionWithAvatar
                    name={user.name}
                    avatarUrl={user.avatarUrl ?? undefined}
                  />
                ),
                value: user.id,
              })) ?? []
            }
            placeholder="Select sales owner user"
          />
        </Form.Item>
        <Form.List name="contacts">
          {(fields, { add, remove }) => (
            <Space direction="vertical">
              {fields.map(({ key, name, ...restFields }) => (
                <Row key={key} gutter={12} align="middle">
                  <Col span={11}>
                    <Form.Item noStyle {...restFields} name={[name, "name"]}>
                      <Input
                        //@ts-ignore
                        addonBefore={<UserOutlined />}
                        placeholder="Contact name"
                      />
                    </Form.Item>
                  </Col>
                  <Col span={11}>
                    <Form.Item noStyle {...restFields} name={[name, "email"]}>
                      <Input
                        // @ts-ignore
                        addonBefore={<MailOutlined />}
                        placeholder="Contact name"
                      />
                    </Form.Item>
                  </Col>
                  <Col span={2}>
                    <Button
                      // @ts-ignore
                      icon={<DeleteOutlined />}
                      onClick={() => remove(name)}
                    ></Button>
                  </Col>
                </Row>
              ))}
              <Typography.Link onClick={() => add()}>
                <PlusCircleOutlined /> Add new contacts
              </Typography.Link>
            </Space>
          )}
        </Form.List>
      </Form>
    </Modal>
  );
};
