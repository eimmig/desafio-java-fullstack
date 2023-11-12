import React from 'react';
import { Modal, Button } from 'react-bootstrap';
import FormEdicaoCadastro from './FormCadastroEdicao';

const ProjetoModal = ({ showModal, handleCloseModal, handleSave, criacaoEdicao, projeto, formRef }) => {
  return (
    <Modal show={showModal} onHide={handleCloseModal}>
      <Modal.Header closeButton>
        <Modal.Title>{criacaoEdicao} de Projetos</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <FormEdicaoCadastro ref={formRef} projeto={projeto} />
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
  );
};

export default ProjetoModal;
