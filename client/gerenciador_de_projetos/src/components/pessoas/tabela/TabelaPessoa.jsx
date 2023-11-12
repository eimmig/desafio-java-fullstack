import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Table, InputGroup, FormControl, Button } from 'react-bootstrap';
import '../Pessoas.css';
import BodyObserver from './../../../observer/BodyObserver';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const PessoasTable = React.forwardRef(({ keyProp, editarPessoa }, ref) => {
  const [isDarkMode, setIsDarkMode] = useState(false);
  const [pessoa, setPessoa] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [filteredPessoas, setFilteredPessoas] = useState([]);

  const handleBodyClassChange = (darkMode) => {
    setIsDarkMode(darkMode);
  };

  const carregarPessoas = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/pessoa/getAll');
      setPessoas(response.data);
      setFilteredPessoas(response.data);
    } catch (error) {
      console.error('Erro ao buscar pessoas:', error);
    }
  };

  const excluirPessoa = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/pessoa/${id}`);
      toast.success('Pessoa Excluída com Sucesso!');
      recarregarTabela();
    } catch (error) {
      console.error('Erro ao excluir o pessoa:', error);
      toast.error(error.response.data);
    }
  };

  const handleInputChange = (event) => {
    const termoBusca = event.target.value;
    setSearchTerm(termoBusca);

    const pessoasFiltrados = pessoas.filter((pessoa) =>
      pessoa.nome.toLowerCase().includes(termoBusca.toLowerCase())
    );

    setFilteredPessoas(pessoasFiltrados);
  };

  const recarregarTabela = () => {
    carregarPessoas();
  };

  const [pessoas, setPessoas] = useState([]);
  useEffect(() => {
    carregarPessoas();
  }, [keyProp, ref]);

  React.useImperativeHandle(ref, () => ({
    getPessoa: () => pessoa,
  }));

  const editarPessoaFunc = async (pessoa) => {
     pessoa = await atualizarPessoa(pessoa.id);
     pessoa.cargo = pessoa.funcionario ? 'funcionario' : 'gerente';
    setPessoa(pessoa);
    setTimeout(() => {
      editarPessoa();
    }, 0);
  };

  const atualizarPessoa = async (id) => { 
    try {
      const response = await axios.get(`http://localhost:8080/api/pessoa/${id}`);
      return response.data;
    } catch (error) {
      console.error('Erro ao buscar o Pessoa:', error);
    }
  }

  return (
    <div>
      <InputGroup className="mb-1 input-pessoa">
        <FormControl 
          placeholder="Buscar pessoa pelo nome..." 
          value={searchTerm}
          onChange={handleInputChange}
        />
      </InputGroup>
      <BodyObserver onBodyClassChange={handleBodyClassChange} />
      <Table striped bordered hover variant={isDarkMode ? 'dark' : 'light'}>
        <thead>
          <tr>
            <th className="text-center col-md-2 small-font">Nome</th>
            <th className="text-center col-md-2 small-font">Data de Nascimento</th>
            <th className="text-center col-md-3 small-font">CPF</th>
            <th className="text-center col-md-3 small-font">Funcionario</th>
            <th className="text-center col-md-1 small-font">Gerente</th>
            <th className="text-center col-md-1 small-font">Ação</th>
          </tr>
        </thead>
        <tbody>
          {filteredPessoas.map((pessoa) => (
            <tr key={pessoa.id}>
              <td className="text-center small-font">{pessoa.nome}</td>
              <td className="text-center small-font">{new Date(pessoa.dataNascimento).toLocaleDateString()}</td>
              <td className="text-center small-font">{pessoa.cpf}</td>
              <td className="text-center">{pessoa.funcionario ? <i className="fa-regular fa-circle-check"></i> : <i className="fa-regular fa-circle-xmark"></i> }</td>
              <td className="text-center">{pessoa.gerente ? <i className="fa-regular fa-circle-check"></i> : <i className="fa-regular fa-circle-xmark"></i> }</td>
              <td className="text-center small-font">
                <Button
                  className="small-button btn-pessoa mr-2"
                  variant="primary"
                  onClick={() => editarPessoaFunc(pessoa)}
                >
                  Editar
                </Button>
                <Button
                  className="small-button"
                  variant="danger"
                  onClick={() => excluirPessoa(pessoa.id)}
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

export default PessoasTable;
