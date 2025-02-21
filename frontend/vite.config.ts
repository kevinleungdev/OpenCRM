import react from "@vitejs/plugin-react";
import { resolve } from "path";
import { loadEnv, type ConfigEnv, type UserConfigExport } from "vite";
import { viteMockServe } from "vite-plugin-mock";
import tsconfigPaths from "vite-tsconfig-paths";

const root = process.cwd();

function pathResolve(dir: string) {
  return resolve(root, ".", dir);
}

export default ({ command, mode }: ConfigEnv): UserConfigExport => {
  let env = loadEnv(mode, root);
  const isBuild = command === "build";

  return {
    plugins: [
      tsconfigPaths({ root: __dirname }),
      react(),
      env.VITE_USE_MOCK === "true"
        ? viteMockServe({
            ignore: /^_/,
            mockPath: "mock",
            enable: !isBuild,
          })
        : undefined,
    ],
    build: {
      rollupOptions: {
        output: {
          manualChunks: {
            antd: ["antd"],
          },
        },
      },
    },
    resolve: {
      extensions: [
        ".mjs",
        ".js",
        ".ts",
        ".jsx",
        ".tsx",
        ".json",
        ".less",
        ".css",
      ],
      alias: [
        {
          find: /\@\//,
          replacement: `${pathResolve("src")}/`,
        },
      ],
    },
    server: {
      port: 4000,
      proxy: {
        "/graphql": {
          target: "http://localhost:8080",
          changeOrigin: true,
        },
        "/graphqlws": {
          target: "ws://localhost:8080",
          ws: true,
        },
      },
      host: "0.0.0.0",
    },
  };
};
