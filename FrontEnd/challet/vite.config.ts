import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import { VitePWA } from 'vite-plugin-pwa';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    VitePWA({
      registerType: 'autoUpdate', // 자동 업데이트 설정
      includeAssets: [
        'favicon.svg',
        'robots.txt',
        'apple-touch-icon.png',
        'assets/icons/*',
      ], // 캐싱할 자산 목록
      manifest: {
        name: 'Challet',
        short_name: 'Challet',
        description: 'Your Challet Application',
        theme_color: '#ffffff',
        icons: [
          {
            src: 'icons/apple-touch-icon-144x144.png',
            sizes: '144x144',
            type: 'image/png',
            purpose: 'any',
          },
          {
            src: 'icons/favicon-96x96.png',
            sizes: '96x96',
            type: 'image/png',
            purpose: 'any',
          },
        ],
      },
    }),
  ],
});
