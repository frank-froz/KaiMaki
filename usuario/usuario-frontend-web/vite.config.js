import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

export default defineConfig({
  base: "./",
  plugins: [react()],
  server: {
    proxy: {
      "/api": "http://localhost:8080",
      "/ws": {
        target: "http://localhost:8080",
        ws: true,
        changeOrigin: true,
      },
      "/ws-sockjs": {
        target: "http://localhost:8080",
        ws: true,
        changeOrigin: true,
      },
    },
  },
  define: {
    global: "window",
  },
});
