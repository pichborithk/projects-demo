import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react-swc';

export default ({ mode }: { mode: string }) => {
  const env = loadEnv(mode, process.cwd(), '');

  return defineConfig({
    plugins: [react()],

    server: {
      host: true,
      strictPort: true,
      port: Number(env.VITE_PORT),
    },
  });
};
