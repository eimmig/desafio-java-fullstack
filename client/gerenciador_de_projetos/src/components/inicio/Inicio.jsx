import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Inicio.css';

const GridInicio = () => {
  const [projetoStats, setProjetoStats] = useState({
    totalProjetos: 0,
    totalOrcamento: 0,
  });

  const [pessoaStats, setPessoaStats] = useState({
    totalPessoas: 0,
    totalGerentes: 0,
    totalFuncionarios: 0,
  });

  useEffect(() => {
    axios.get('http://localhost:8080/api/projeto/stats')
      .then(response => setProjetoStats(response.data))
      .catch(error => console.error('Erro ao buscar estatísticas de projetos:', error));

    axios.get('http://localhost:8080/api/pessoa/stats')
      .then(response => setPessoaStats(response.data))
      .catch(error => console.error('Erro ao buscar estatísticas de pessoas:', error));
  }, []);

  return (
    <div className="text">Início
      <div className="container">
        <div className="row">
          <div className="col-md-6">
            <div className="box-grid">
              <div className="icon-grid"><i className="fa-solid fa-screwdriver-wrench icon"></i></div>
              <div className="text-grid">
                <div>Total de Projetos: {projetoStats.totalProjetos}</div>
                <div>Total de Orçamento: R$ {projetoStats.totalOrcamento}</div>
              </div>
            </div>
          </div>
          <div className="col-md-6">
            <div className="box-grid">
              <div className="icon-grid"><i className="fa-solid fa-user icon"></i></div>
              <div className="text-grid">
                <div>Total de Pessoas: {pessoaStats.totalPessoas}</div>
                <div>Total de Gerentes: {pessoaStats.totalGerentes}</div>
                <div>Total de Funcionários: {pessoaStats.totalFuncionarios}</div>
              </div>
            </div>
          </div>
          <div className="col-md-12">
            <div className="logo-container">
              <img src="src\assets\taskforge-high-resolution-logo-transparent.png" alt="Logo" className="logo" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default GridInicio;
