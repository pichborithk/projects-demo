import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.tsx';
import './index.css';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Checkout from './Checkout.tsx';
import Canceled from './Canceled.tsx';

const router = createBrowserRouter([
  { path: '/', element: <App /> },
  { path: 'checkout', element: <Checkout /> },
  { path: 'canceled', element: <Canceled /> },
]);

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
