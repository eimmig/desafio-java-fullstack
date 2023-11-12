import React from 'react';
import './Legenda.css';

const Legend = () => {
  const getStatus = (index) => {
    switch (index) {
      case 0:
        return "Em Análise";
      case 1:
        return "Análise Realizada";
      case 2:
        return "Análise Aprovada";
      case 3:
        return "Iniciado";
      case 4:
        return "Planejado";
      case 5:
        return "Em Andamento";
      case 6:
        return "Encerrado";
      case 7:
        return "Cancelado";
      default:
        return "Desconhecido";
    }
  };

  const cores = ['#FF5733', '#33FF57', '#5733FF', '#33FFE0', '#FF33E0', '#00791a', '#FFBB33', '#ff3333'];

  return (
    <div className="legend-container">
      {cores.map((cor, index) => (
        <div key={index} className="legend-item">
          <div className="status-dot" style={{ backgroundColor: cor }}>
            <span className="legend-text">{getStatus(index)}</span>
          </div>
        </div>
      ))}
    </div>
  );
};

export default Legend;
