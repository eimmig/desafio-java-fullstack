import React, { useRef, useState } from 'react';
import { Button, Modal } from 'react-bootstrap';
import PessoasTable from './tabela/TabelaPessoa';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from 'axios';
import FormEdicaoCadastro from './modal-cadastro-edicao/FormCadastroEdicao';
import './Pessoas.css';
import BodyObserver from '../../observer/BodyObserver';

const GridPessoas = () => {
  const [showModal, setShowModal] = useState(false);
  const [criacaoEdicao, setCriacaoEdicao] = useState("Cadastro");
  const [tableKey, setTableKey] = useState(0);
  const formRef = useRef();
  const pessoaRef = useRef();
  const [pessoaModal, setPessoaModal] = useState(null);
  const [isDarkMode, setIsDarkMode] = useState(false);

  const handleBodyClassChange = (darkMode) => {
    setIsDarkMode(darkMode);
  };

  const handleOpenModal = () => {
    setShowModal(true);
    if (pessoaModal !== null) {
      setCriacaoEdicao("Edição");
    } else {
      setCriacaoEdicao("Cadastro");
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const editarPessoa = () => { 
    setPessoaModal(pessoaRef.current.getPessoa());
    handleOpenModal();
  }

  const criarPessoa = () => {
    setPessoaModal(null);
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
      if (pessoaModal !== null) { 
        await axios.put('http://localhost:8080/api/pessoa/' + pessoaModal.id, values, {
          headers: {
            'Content-Type': 'application/json',
          },
        });
      } else {
        await axios.post('http://localhost:8080/api/pessoa/save', values, {
          headers: {
            'Content-Type': 'application/json',
          },
        });
      }
      toast.success('Pessoa salvo com sucesso!');
      setTableKey((prevKey) => prevKey + 1);
      handleCloseModal();
    } catch (error) {
      toast.error(`Erro ao salvar o pessoa: ${error.message}`);
      console.error('Erro ao salvar o pessoa:', error);
    }
  };

  const validarFormulario = (values) => {
    const camposObrigatorios = [
      'nome',
      'dataNascimento',
      'cpf',
      'funcionario',
      'gerente'
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
      Pessoas
      <div className="mt-3">
        {/* <Button className="mb-3 btn-pessoas" variant="success" onClick={criarPessoa}>
          Nova Pessoa
        </Button> */}
        <Modal show={showModal} onHide={handleCloseModal}>
          <Modal.Header closeButton>
            <Modal.Title>{criacaoEdicao} de Pessoas</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <FormEdicaoCadastro ref={formRef} pessoa={pessoaModal} />
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
        <PessoasTable key={tableKey} ref={pessoaRef} editarPessoa={editarPessoa} />
        <ToastContainer theme={isDarkMode ? 'dark' : 'light'} />
      </div>
    </div>
  );
};

export default GridPessoas;
