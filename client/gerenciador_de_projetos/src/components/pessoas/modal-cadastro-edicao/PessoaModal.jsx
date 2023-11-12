import React from 'react';
import { Modal, Button } from 'react-bootstrap';
import FormEdicaoCadastro from './FormCadastroEdicao';

const PessoasModal = ({ showModal, handleCloseModal, handleSave, criacaoEdicao, Pessoas, formRef }) => {
  return (
    <Modal show={showModal} onHide={handleCloseModal}>
      <Modal.Header closeButton>
        <Modal.Title>{criacaoEdicao} de Pessoas</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <FormEdicaoCadastro ref={formRef} pessoas={pessoas} />
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

export default PessoasModal;
