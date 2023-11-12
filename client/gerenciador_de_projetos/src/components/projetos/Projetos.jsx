import React, { useRef, useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import ProjetosTable from './tabela/TabelaProjeto';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from 'axios';
import FormEdicaoCadastro from './modal-cadastro-edicao/FormCadastroEdicao';
import './Projetos.css';
import BodyObserver from '../../observer/BodyObserver';

const GridProjetos = () => {
  const [showModal, setShowModal] = useState(false);
  const [criacaoEdicao, setCriacaoEdicao] = useState("Cadastro");
  const [tableKey, setTableKey] = useState(0);
  const formRef = useRef();
  const projetoRef = useRef();
  const [projetoModal, setProjetoModal] = useState(null);
  const [isDarkMode, setIsDarkMode] = useState(false);

  const handleBodyClassChange = (darkMode) => {
    setIsDarkMode(darkMode);
  };

  const handleOpenModal = () => {
    setShowModal(true);
    if (projetoModal !== null) {
      setCriacaoEdicao("Edição");
    } else {
      setCriacaoEdicao("Cadastro");
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const editarProjeto = () => { 
    setProjetoModal(projetoRef.current.getProjeto());
    handleOpenModal();
  }

  const criarProjeto = () => {
    setProjetoModal(null);
    handleOpenModal();
  };

  const handleSave = async () => {
    const values = formRef.current.getFormValues();
    const formValido = validarFormulario(values);

    if (!formValido) {
      toast.error('Preencha todos os campos obrigatórios!');
      return;
    }

    try {
      if (projetoModal !== null) { 
        await axios.put('http://localhost:8080/api/projeto/' + projetoModal.id, values, {
          headers: {
            'Content-Type': 'application/json',
          },
        });
      } else {
        await axios.post('http://localhost:8080/api/projeto/save', values, {
          headers: {
            'Content-Type': 'application/json',
          },
        });
      }
      toast.success('Projeto salvo com sucesso!');
      setTableKey((prevKey) => prevKey + 1);
      handleCloseModal();
    } catch (error) {
      toast.error(`Erro ao salvar o projeto: ${error.message}`);
      console.error('Erro ao salvar o projeto:', error);
    }
  };

  const validarFormulario = (values) => {
    const camposObrigatorios = [
      'nome',
      'dataInicio',
      'dataPrevisaoFim',
      'descricao',
      'status',
      'orcamento',
      'risco',
      'idGerente',
    ];

    for (const campo of camposObrigatorios) {
      if (values[campo] === undefined || values[campo] === null || values[campo] === '') {
        return false;
      }
    }
    return true;
  };

  return (
    <div className="text">
      <BodyObserver onBodyClassChange={handleBodyClassChange} />
      Projetos
      <div className="mt-3">
        <Button className="mb-3 btn-projetos" variant="success" onClick={criarProjeto}>
          Novo Projeto
        </Button>
        <Modal show={showModal} onHide={handleCloseModal}>
          <Modal.Header closeButton>
            <Modal.Title>{criacaoEdicao} de Projetos</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <FormEdicaoCadastro ref={formRef} projeto={projetoModal} />
          </Modal.Body>
          <Modal.Footer>
            <Button variant="primary" onClick={handleSave}>
              Salvar
            </Button>
            <Button variant="secondary" onClick={handleCloseModal}>
              Fechar
            </Button>
          </Modal.Footer>
        </Modal>
        <ProjetosTable key={tableKey} ref={projetoRef} editarProjeto={editarProjeto} />
        <ToastContainer theme={isDarkMode ? 'dark' : 'light'} />
      </div>
    </div>
  );
};

export default GridProjetos;
