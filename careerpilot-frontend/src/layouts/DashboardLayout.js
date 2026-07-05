import React, { useState, useEffect } from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from './Navbar';
import Sidebar from './Sidebar';
import '../styles/layout.css';

/**
 * DashboardLayout wraps every protected page.
 *
 * Structure:
 *   <DashboardLayout>
 *     ├── <Navbar>        (top bar, full width)
 *     └── .layout__body
 *           ├── <Sidebar> (collapsible left nav)
 *           └── <main>    (page content via <Outlet>)
 *
 * Sidebar is open by default on desktop (≥1024 px) and closed on mobile.
 */
function DashboardLayout() {
  const [sidebarOpen, setSidebarOpen] = useState(window.innerWidth >= 1024);

  // Close sidebar on resize to mobile
  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth < 1024) {
        setSidebarOpen(false);
      } else {
        setSidebarOpen(true);
      }
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  const toggleSidebar  = () => setSidebarOpen((prev) => !prev);
  const closeSidebar   = () => setSidebarOpen(false);

  return (
    <div className="layout">
      <Navbar onMenuToggle={toggleSidebar} sidebarOpen={sidebarOpen} />

      <div className="layout__body">
        <Sidebar isOpen={sidebarOpen} onClose={closeSidebar} />

        <main
          className={`layout__main ${sidebarOpen ? 'layout__main--shifted' : ''}`}
          id="main-content"
          aria-label="Page content"
        >
          <div className="layout__content">
            {/* React Router renders the matched child route here */}
            <Outlet />
          </div>
        </main>
      </div>
    </div>
  );
}

export default DashboardLayout;
