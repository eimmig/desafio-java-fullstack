import React from 'react';
import './StatusDots.css';

const StatusDots = ({ status }) => {
  const getStatusColor = (status) => {
    switch (status) {
      case 'EM_ANALISE':
        return ['status-1', "Em Análise"];
      case 'ANALISE_REALIZADA':
        return ['status-2', "Análise Realizada"];
      case 'ANALISE_APROVADA':
        return ['status-3', "Análise Aprovada"];
      case 'INICIADO':
        return ['status-4', "Iniciado"];
      case 'PLANEJADO':
        return ['status-5', "Planejado"];
      case 'EM_ANDAMENTO':
        return ['status-6', "Em Andamento"];
      case 'ENCERRADO':
        return ['status-7', "Encerrado"];
      case 'CANCELADO':
        return ['status-8', "Cancelado"];
    }
  };

  const [dotClass, legenda] = getStatusColor(status);


  return <div className={`status-dot ${dotClass}`} title={legenda}></div>;
};

export default StatusDots;
