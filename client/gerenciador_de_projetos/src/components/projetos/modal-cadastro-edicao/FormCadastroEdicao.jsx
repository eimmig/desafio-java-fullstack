import React, { useState, useEffect } from 'react';
import { Form, FloatingLabel } from 'react-bootstrap';
import axios from 'axios';
import Select from 'react-select';


const FormEdicaoCadastro = React.forwardRef(({ projeto }, ref) => {
  const [formValues, setFormValues] = useState({
    nome: '',
    dataInicio: '',
    dataPrevisaoFim: '',
    dataFim: '',
    descricao: '',
    status: '',
    orcamento: 0,
    risco: '',
    idGerente: '',
    funcionarios: [],
  });

  React.useImperativeHandle(ref, () => ({ 
    getFormValues: () => formValues,
  }));

  const [gerentes, setGerentes] = useState([]);
  const [funcionarios, setfuncionarios] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/pessoa/getGerentes')
      .then(response => setGerentes(response.data))
      .catch(error => console.error('Erro ao buscar gerentes:', error));
    
    axios.get('http://localhost:8080/api/pessoa/getFuncionarios')
      .then(response => setfuncionarios(response.data))
      .catch(error => console.error('Erro ao buscar funcionarios:', error));

      if (projeto) {
        if (typeof(projeto.idGerente) === 'object') {
          projeto.idGerente = projeto.idGerente.id
        }
        projeto.dataInicio = formatarData(new Date(projeto.dataInicio))
        projeto.dataPrevisaoFim = formatarData(new Date(projeto.dataPrevisaoFim))
        projeto.dataFim = formatarData(new Date(projeto.dataFim))
      setFormValues({
        ...projeto
      });
    }
  }, [projeto]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({
      ...formValues,
      [name]: value,
    });
  };

  const handleSelecaoMultiplaChange = (selectedOptions) => {
    setFormValues({
      ...formValues,
      funcionarios: selectedOptions,
    });
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
          <FloatingLabel className="mb-2" controlId="nome" label="Nome do Projeto">
            <Form.Control
              type="text"
              placeholder="Nome"
              name="nome"
              value={formValues.nome}
              onChange={handleInputChange}
            />
          </FloatingLabel>

          <FloatingLabel className="mb-2" controlId="dataInicio" label="Data de Início">
            <Form.Control
              type="date"
              name="dataInicio"
              value={formValues.dataInicio}
              onChange={handleInputChange}
            />
          </FloatingLabel>

          <FloatingLabel className="mb-2" controlId="dataPrevisaoFim" label="Previsão de Término">
            <Form.Control
              type="date"
              name="dataPrevisaoFim"
              value={formValues.dataPrevisaoFim}
              onChange={handleInputChange}
            />
          </FloatingLabel>

          <FloatingLabel className="mb-2" controlId="dataFim" label="Data de Término">
            <Form.Control
              type="date"
              name="dataFim"
              value={formValues.dataFim}
              onChange={handleInputChange}
            />
          </FloatingLabel>

          <FloatingLabel className="mb-2" controlId="descricao" label="Descrição">
            <Form.Control
              as="textarea"
              placeholder="Descrição"
              name="descricao"
              value={formValues.descricao}
              onChange={handleInputChange}
            />
          </FloatingLabel>

          <FloatingLabel className="mb-2" controlId="status" label="Status">
            <Form.Select
              name="status"
              value={formValues.status}
              onChange={handleInputChange}
            >
              <option value="">Selecione o status</option>
              <option value="EM_ANALISE">Em Análise</option>
              <option value="ANALISE_REALIZADA">Análise Realizada</option>
              <option value="ANALISE_APROVADA">Análise Aprovada</option>
              <option value="INICIADO">Iniciado</option>
              <option value="PLANEJADO">Planejado</option>
              <option value="EM_ANDAMENTO">Em Andamento</option>
              <option value="ENCERRADO">Encerrado</option>
              <option value="CANCELADO">Cancelado</option>
            </Form.Select>
          </FloatingLabel>

          <FloatingLabel className="mb-2" controlId="orcamento" label="Orçamento">
            <Form.Control
              type="number"
              placeholder="Orçamento"
              name="orcamento"
              value={formValues.orcamento}
              onChange={handleInputChange}
            />
          </FloatingLabel>

          <FloatingLabel className="mb-2" controlId="risco" label="Risco">
            <Form.Select
              name="risco"
              value={formValues.risco}
              onChange={handleInputChange}
            >
              <option value="">Selecione o risco</option>
              <option value="BAIXO_RISCO">Baixo Risco</option>
              <option value="MEDIO_RISCO">Médio Risco</option>
              <option value="ALTO_RISCO">Alto Risco</option>
            </Form.Select>
          </FloatingLabel>

          <FloatingLabel className="mb-2" controlId="idGerente" label="Gerente">
            <Form.Select
              name="idGerente"
              value={formValues.idGerente}
              onChange={handleInputChange}
            >
              <option value="">Selecione o gerente</option>
              {gerentes.map(gerente => (
                <option key={gerente.id} value={gerente.id}>
                  {gerente.nome}
                </option>
              ))}
            </Form.Select>
          </FloatingLabel>
          <FloatingLabel controlId="selecaoMultipla">
          <Select
            isMulti
            name="selecaoMultipla"
            options={funcionarios.map((funcionario) => ({ value: funcionario.id, label: funcionario.nome }))}
            getOptionLabel={(option) => option.label}
            getOptionValue={(option) => option.value}
            onChange={handleSelecaoMultiplaChange}
            placeholder="Selecione os Funcionários..."
            value={formValues.funcionarios}
          />
          </FloatingLabel>
        </Form>
  );
});

export default FormEdicaoCadastro;
