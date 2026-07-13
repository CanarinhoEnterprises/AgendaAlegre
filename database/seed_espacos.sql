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
(1, 1, 24, 'Quadra Poliesportiva Central', 'Quadra para futsal, vôlei e atividades comunitárias.', https://www.pmbsf.es.gov.br/uploads/files/quadra-do-vargem-alegre.jpg, 'ATIVO'),
(2, 1, 18, 'Quadra do Bairro Boa Vista', 'Quadra de uso comunitário do bairro Boa Vista.', https://www.vitoria.es.gov.br/recursos/imagens/banco/2023/03/06/110731/normal@2x.jpg, 'INTERDITADO'),
(3, 3, 120, 'Praça da Matriz', 'Praça central para encontro e eventos da cidade.', https://www.alegre.es.gov.br/wp-content/uploads/2021/06/DSC_5189-scaled.jpg, 'ATIVO'),
(4, 4, 150, 'Salão Comunitário São José', 'Salão para festas e reuniões da comunidade.', https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTWo0HUdFQRQNAqxaYpEJSfAyhUzhgia-wQ1Mry0B2J4zCQ7dMGgDyAn3Nt&s=10, 'INATIVO'),
(5, 4, 180, 'Salão de Festas do Distrito', 'Salão comunitário do distrito de Celina.', https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDiTrxl5IAcPGvITz6vKhsxJL0zx0xnB_YT8WqvonwMMqQb5jkFdGn-WMq&s=10, 'ATIVO'),
(6, 5, 220, 'Auditório Municipal', 'Auditório principal para eventos institucionais.', https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmHPwTfbkwAljq-lG-h73E4ikC6nkn08fKQWY1F6I4xoOAsySut2v68A&s=10, 'ATIVO'),
(6, 6, 160, 'Centro Cultural de Alegre', 'Espaço voltado para oficinas e apresentações culturais.', https://descubraoespiritosanto.es.gov.br/Media/DescubraOEspiritoSanto/_Profiles/7996852b/34b24092/Teatro%20Municipal%20de%20Alegre%20(3).jpg?v=639005206838552300, 'INATIVO');

INSERT INTO Reserva (
    idSolicitante, idEspaco, horaInicio, dtUso, horaFim,
    finalidade, dtSolicitacao, dtCancelamento, dtConfirmacao,
    qtdPessoas, status
)
VALUES (
    4,                                  -- idSolicitante
    13,                                  -- idEspaco
    '10:50:00',                        -- horaInicio
    '2025-07-12',  -- dtUso
    '10:56:00',                        -- horaFim
    'Torneio de futsal comunitário',   -- finalidade
    NOW(),                             -- dtSolicitacao
    NULL,                              -- dtCancelamento
    NULL,                              -- dtConfirmacao
    30,                                -- qtdPessoas
    'CONFIRMADA'                       -- status
);

INSERT INTO Avaliacao (
    idReserva, nota, comentario, dtAvaliacao
)
VALUES (
    1,                                                   -- idReserva
    5,                                                   -- nota (1 a 5)
    'Espaço muito bem cuidado e de fácil acesso.',       -- comentario (até 1000 caracteres)
    CURRENT_DATE                                         -- dtAvaliacao
);