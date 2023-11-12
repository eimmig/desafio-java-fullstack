import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Table, InputGroup, FormControl, Button } from 'react-bootstrap';
import '../Projetos.css';
import BodyObserver from './../../../observer/BodyObserver';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import StatusDots from './status/StatusDots';
import Legend from './status/legenda';

const ProjetosTable = React.forwardRef(({ keyProp, editarProjeto }, ref) => {
  const [isDarkMode, setIsDarkMode] = useState(false);
  const [projeto, setProjeto] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredProjetos, setFilteredProjetos] = useState([]);

  const handleBodyClassChange = (darkMode) => {
    setIsDarkMode(darkMode);
  };

  const carregarProjetos = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/projeto/getAll');
      setProjetos(response.data);
      setFilteredProjetos(response.data);
    } catch (error) {
      console.error('Erro ao buscar projetos:', error);
    }
  };

  const excluirProjeto = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/projeto/${id}`);
      toast.success('Projeto Excluído com Sucesso!');
      recarregarTabela();
    } catch (error) {
      console.error('Erro ao excluir o projeto:', error);
      toast.error(error.response.data);
    }
  };

  const handleInputChange = (event) => {
    const termoBusca = event.target.value;
    setSearchTerm(termoBusca);

    const projetosFiltrados = projetos.filter((projeto) =>
      projeto.nome.toLowerCase().includes(termoBusca.toLowerCase())
    );

    setFilteredProjetos(projetosFiltrados);
  };

  const recarregarTabela = () => {
    carregarProjetos();
  };

  const [projetos, setProjetos] = useState([]);
  useEffect(() => {
    carregarProjetos();
  }, [keyProp, ref]);

  React.useImperativeHandle(ref, () => ({
    getProjeto: () => projeto,
  }));

  const editarProjetoFunc = async (projeto) => {
    projeto = await atualizarProjeto(projeto.id);
    setProjeto(projeto);
    setTimeout(() => {
      editarProjeto();
    }, 0);
  };

  const atualizarProjeto = async (id) => { 
    try {
      const response = await axios.get(`http://localhost:8080/api/projeto/gerenteFuncionarios/${id}`);
      return response.data;
    } catch (error) {
      console.error('Erro ao buscar o projeto:', error);
    }
  }

  return (
    <div>
      <Legend />
      <InputGroup className="mb-1 input-projeto">
        <FormControl 
          placeholder="Buscar projeto pelo nome..." 
          value={searchTerm}
          onChange={handleInputChange}
        />
      </InputGroup>
      <BodyObserver onBodyClassChange={handleBodyClassChange} />
      <Table striped bordered hover variant={isDarkMode ? 'dark' : 'light'}>
        <thead>
          <tr>
            <th className="text-center col-md-2 small-font">Nome</th>
            <th className="text-center col-md-2 small-font">Data de Início</th>
            <th className="text-center col-md-3 small-font">Previsão de Término</th>
            <th className="text-center col-md-3 small-font">Responsável</th>
            <th className="text-center col-md-1 small-font">Status</th>
            <th className="text-center col-md-1 small-font">Ação</th>
          </tr>
        </thead>
        <tbody>
          {filteredProjetos.map((projeto) => (
            <tr key={projeto.id}>
              <td className="text-center small-font">{projeto.nome}</td>
              <td className="text-center small-font">{new Date(projeto.dataInicio).toLocaleDateString()}</td>
              <td className="text-center small-font">{new Date(projeto.dataPrevisaoFim).toLocaleDateString()}</td>
              <td className="text-center small-font">{projeto.idGerente.nome}</td>
              <td className="text-center"> <StatusDots status={projeto.status} /></td>
              <td className="text-center small-font">
                <Button
                  className="small-button btn-projetos mr-2"
                  variant="primary"
                  onClick={() => editarProjetoFunc(projeto)}
                >
                  Editar
                </Button>
                <Button
                  className="small-button"
                  variant="danger"
                  onClick={() => excluirProjeto(projeto.id)}
                >
                  Excluir
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
});

export default ProjetosTable;
