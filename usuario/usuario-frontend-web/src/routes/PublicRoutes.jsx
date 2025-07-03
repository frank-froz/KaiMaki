import { Outlet } from 'react-router-dom';

export function PublicRoutes() {
  return (
    <>
      {/* Puedes meter un Layout público acá si quieres */}
      <Outlet />
    </>
  );
}
