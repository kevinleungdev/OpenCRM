import { EditOutlined } from "@ant-design/icons";
import { useForm, UseFormProps } from "@refinedev/antd";
import { Button, Form, FormItemProps, FormProps, Skeleton } from "antd";
import FormItem from "antd/es/form/FormItem";
import React from "react";
import { Text } from "../text";
import styles from "./index.module.css";

type SingleElementFormProps = {
  itemProps?: FormItemProps;
  icon?: React.ReactNode;
  view?: React.ReactNode;
  extra?: React.ReactNode;
  state?: "view" | "form" | "empty";
  onUpdate?: () => void;
  onCancel?: () => void;
  onClick?: () => void;
  loading?: boolean;
  style?: React.CSSProperties;
  useFormProps?: UseFormProps;
  formProps?: FormProps;
} & React.PropsWithChildren;

export const SingleElementForm: React.FC<SingleElementFormProps> = ({
  state = "view",
  view,
  icon,
  itemProps,
  onUpdate,
  onCancel,
  onClick,
  loading,
  children,
  style,
  extra,
  useFormProps,
  formProps: formPropsFromProp,
}) => {
  const { formProps, saveButtonProps } = useForm({
    action: "edit",
    redirect: false,
    autoSave: {
      enabled: false,
    },
    queryOptions: {
      enabled: false,
    },
    mutationMode: "optimistic",
    onMutationSuccess() {
      onUpdate?.();
    },
    ...useFormProps,
  });

  return (
    <Form layout="vertical" {...formProps} {...formPropsFromProp}>
      <div className={styles.container} style={style}>
        <div className={styles.icon}>{icon}</div>
        <div className={styles.content}>
          <div className={styles.input}>
            <Text type="secondary" size="sm" className={styles.label}>
              {itemProps?.label}
            </Text>
            {loading && (
              <Skeleton.Input className={styles.skeleton} size="small" active />
            )}
            {state === "form" && !loading && (
              <div className={styles.formItem}>
                <FormItem {...itemProps} noStyle>
                  {children}
                </FormItem>
                {extra}
              </div>
            )}
            {state === "empty" && (
              <Button
                onClick={() => onClick?.()}
                type="link"
                size="small"
                style={{ padding: 0 }}
              >
                Add ${itemProps?.label}
              </Button>
            )}
            {state === "view" && view}
          </div>

          {state === "form" && (
            <div className={styles.buttons}>
              <Button onClick={() => onCancel?.()}>Cancel</Button>
              <Button type="primary" {...saveButtonProps}>
                Save
              </Button>
            </div>
          )}
        </div>

        {state === "view" && (
          <div className={styles.actions}>
            <EditOutlined onClick={() => onClick?.()} />
          </div>
        )}
      </div>
    </Form>
  );
};
