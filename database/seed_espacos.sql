INSERT INTO Cidade (UF, nome) VALUES
('ES', 'Alegre');

INSERT INTO TipoEspaco (nome, descricao) VALUES
('Quadra esportiva', 'Espaço destinado a atividades esportivas e recreativas.'),
('Parque', 'Espaço público ao ar livre para convivência e lazer.'),
('Praça', 'Área pública urbana de convivência e eventos.'),
('Salão de festas', 'Ambiente coberto para eventos comunitários e sociais.'),
('Auditório', 'Espaço para apresentações, reuniões e atividades culturais.'),
('Centro cultural', 'Espaço destinado a atividades artísticas e culturais.');

INSERT INTO Endereco (idCidade, logradouro, bairro, cep, num, comp, referencia) VALUES
(1, 'Avenida Jerônimo Monteiro', 'Centro', '29500000', 100, NULL, 'Próximo à prefeitura'),
(1, 'Rua Boa Vista', 'Boa Vista', '29500001', 45, NULL, 'Ao lado da escola municipal'),
(1, 'Avenida Muniz Freire', 'Centro', '29500000', 250, NULL, 'Próximo ao parque central'),
(1, 'Praça São José', 'São José', '29500002', NULL, NULL, 'Em frente à igreja'),
(1, 'Rodovia ES-482', 'Distrito de Celina', '29500003', NULL, NULL, 'Área central do distrito'),
(1, 'Rua Sete de Setembro', 'Centro', '29500000', 520, NULL, 'Próximo à biblioteca');

INSERT INTO Espaco (idEndereco, idTipoEspaco, capacidade, nome, descricao, urlCapa, status) VALUES
(1, 1, 24, 'Quadra Poliesportiva Central', 'Quadra para futsal, vôlei e atividades comunitárias.', NULL, 'ATIVO'),
(2, 1, 18, 'Quadra do Bairro Boa Vista', 'Quadra de uso comunitário do bairro Boa Vista.', NULL, 'INTERDITADO'),
(3, 2, 200, 'Parque Municipal', 'Parque público com área verde e espaço de lazer.', NULL, 'ATIVO'),
(3, 3, 120, 'Praça da Matriz', 'Praça central para encontro e eventos da cidade.', NULL, 'ATIVO'),
(4, 4, 150, 'Salão Comunitário São José', 'Salão para festas e reuniões da comunidade.', NULL, 'INATIVO'),
(5, 4, 180, 'Salão de Festas do Distrito', 'Salão comunitário do distrito de Celina.', NULL, 'ATIVO'),
(6, 5, 220, 'Auditório Municipal', 'Auditório principal para eventos institucionais.', NULL, 'ATIVO'),
(6, 6, 160, 'Centro Cultural de Alegre', 'Espaço voltado para oficinas e apresentações culturais.', NULL, 'INATIVO');
