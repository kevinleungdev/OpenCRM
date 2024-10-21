import { authProvider, emails } from "@/providers";
import { useCallback, useEffect, useState } from "react";

export const useAutoLoginForDemo = () => {
  const [isLoading, setIsLoading] = useState(true);

  const login = useCallback(async () => {
    const email = localStorage.getItem("auto_login") || emails[0];
    try {
      await authProvider.login({
        email,
      });
    } catch (error: any) {
    } finally {
      setIsLoading(false);
    }
  }, [isLoading]);

  useEffect(() => {
    const shouldLogin = localStorage.getItem("auto_login") !== "false";
    if (shouldLogin) {
      setIsLoading(false);
      return;
    }

    login();
  }, []);

  return { loading: isLoading };
};

/**
 * Enable auto login feature.
 * This is used to skip the login page and show the application more quickly.
 *
 * @param email
 */
export const enableAutoLogin = (email: string) => {
  localStorage.setItem("auto_login", email);
};

/**
 * Disable auto login feature
 */
export const disableAutoLogin = () => {
  localStorage.setItem("auto_login", "false");
};
