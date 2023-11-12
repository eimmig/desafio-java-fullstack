import React, { useState, useEffect } from 'react';
import { Form, FloatingLabel } from 'react-bootstrap';


const FormEdicaoCadastro = React.forwardRef(({ pessoa }, ref) => {
  const [formValues, setFormValues] = useState({
    nome: '',
    dataNascimento : '',
    cpf : '',
    funcionario : '',
    gerente : '',
    cargo : ''
  });

  React.useImperativeHandle(ref, () => ({ 
    getFormValues: () => formValues,
  }));

  useEffect(() => {
    if (pessoa) {
        pessoa.dataNascimento = formatarData(new Date(pessoa.dataNascimento));
      setFormValues({
        ...pessoa,
      });
    } else {
      setFormValues({
        nome: '',
        dataNascimento: '',
        cpf: '',
        funcionario: false,
        gerente: false,
        cargo: ''
      });
    }
  }, [pessoa]);

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    if (type === 'radio' && checked) {
      setFormValues({
        ...formValues,
        funcionario: name === 'cargo' && value === 'funcionario',
        gerente: name === 'cargo' && value === 'gerente',
        cargo: value,
      });
    } else {
      setFormValues({
        ...formValues,
        [name]: value,
      });
    }
  };

  const formatarData = (timestamp) => {
    const data = new Date(timestamp);
    const ano = data.getFullYear();
    const mes = (data.getMonth() + 1).toString().padStart(2, '0');
    const dia = data.getDate().toString().padStart(2, '0');
    return `${ano}-${mes}-${dia}`;
  };

  return (
    <Form className="my-4">
    <FloatingLabel className="mb-2" controlId="nome" label="Nome">
      <Form.Control
        type="text"
        placeholder="Nome"
        name="nome"
        value={formValues.nome}
        onChange={handleInputChange}
      />
    </FloatingLabel>

    <FloatingLabel className="mb-2" controlId="dataNascimento" label="Data de Nascimento">
      <Form.Control
        type="date"
        name="dataNascimento"
        value={formValues.dataNascimento}
        onChange={handleInputChange}
      />
    </FloatingLabel>

    <FloatingLabel className="mb-2" controlId="cpf" label="CPF">
      <Form.Control
        type="text"
        placeholder="CPF"
        name="cpf"
        value={formValues.cpf}
        onChange={handleInputChange}
      />
    </FloatingLabel>

    <Form.Check
      value = "funcionario"
      type="radio"
      label="Funcionário"
      name="cargo"
      id="funcionarioRadio"
      checked={formValues.cargo === 'funcionario'}
      onChange={handleInputChange}
    />

    <Form.Check
      value = "gerente"
      type="radio"
      label="Gerente"
      name="cargo"
      id="gerenteRadio"
      checked={formValues.cargo === 'gerente'}
      onChange={handleInputChange}
    />
  </Form>
  );
});

export default FormEdicaoCadastro;
