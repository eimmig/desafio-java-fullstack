import React, { useState } from 'react';
import './SideBar.css'; 
import GridInicio from '../inicio/inicio';
import GridProjetos from '../projetos/Projetos';
import GridPessoas from './../pessoas/Pessoas';

const Sidebar = () => {
  const [isSidebarClosed, setIsSidebarClosed] = useState(false);
  const [isDarkMode, setIsDarkMode] = useState(false);

  const toggleSidebar = () => {
    setIsSidebarClosed(!isSidebarClosed);
  };

  const toggleDarkMode = () => {
    setIsDarkMode(!isDarkMode);
    document.body.classList.toggle('dark', !isDarkMode);
  };

  const [activeNavItem, setActiveNavItem] = useState(null);

  const handleNavItemClick = (navItem) => {
    setActiveNavItem(navItem);
  };

  const renderGrid = () => {
    switch (activeNavItem) {
      case 'inicio':
        return <GridInicio />;
      case 'pessoa':
        return <GridPessoas />;
      case 'projeto':
        return <GridProjetos />;
      default:
        return <GridInicio />;
    }
  }

  return (
    <>
      <nav className={`sidebar ${isSidebarClosed ? 'close' : ''}`}>
        <header>
          <div className="image-text">
            <span className="image">
              <img src="taskforge-favicon-color.png" alt="Logo" />
            </span>
            <div className="text header-text">
              <span className="name">ForgeTask</span>
            </div>
          </div>
          <i className="fa-solid fa-angle-right toggle" onClick={toggleSidebar}></i>
        </header>

        <div className="menu-bar">
          <div className="menu">
            <ul className="menu-links">
              <li className="nav-link p-0" onClick={() => handleNavItemClick('inicio')}>
                <a href="#">
                  <i className="fa-solid fa-house icon"></i>
                  <span className="text nav-text">Início</span>
                </a>
              </li>
              <li className="nav-link p-0"  onClick={() => handleNavItemClick('projeto')}>
                <a href="#">
                  <i className="fa-solid fa-screwdriver-wrench icon"></i>
                  <span className="text nav-text">Projetos</span>
                </a>
              </li>
              <li className="nav-link p-0"  onClick={() => handleNavItemClick('pessoa')}>
                <a href="#">
                  <i className="fa-solid fa-user icon"></i>
                  <span className="text nav-text">Pessoas</span>
                </a>
              </li>
            </ul>
            <li className="github-box p-0" onClick={() => setIsSidebarClosed(false)}>
                <a href="https://github.com/eimmig/desafio-java-fullstackt" target="_blank">
                  <i className="fa-brands fa-github icon"></i>
                  <span className="text nav-text">GitHub</span>
                </a>
            </li>
          </div>
          <div className="botton-content">
            <li className="mode" onClick={toggleDarkMode}>
              <div className="moon-sun">
                <i className={`fa-solid fa-moon icon ${isDarkMode ? 'moon' : ''}`}></i>
                <i className={`fa-solid fa-sun icon ${isDarkMode ? '' : 'sun'}`}></i>
              </div>
              <span className="mode-text text">{isDarkMode ? 'Modo Claro' : 'Modo Escuro'}</span>
              <div className="toggle-switch">
                <span className="switch"></span>
              </div>
            </li>
          </div>
        </div>
      </nav>
      <section className="home">
        {renderGrid()}
      </section>
    </>
  );
};

export default Sidebar;
