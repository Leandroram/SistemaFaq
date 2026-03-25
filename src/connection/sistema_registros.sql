-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 19-Mar-2026 às 05:02
-- Versão do servidor: 10.4.32-MariaDB
-- versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sistema_registros`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tblempresa`
--

CREATE TABLE `tblempresa` (
  `id` int(11) NOT NULL,
  `empresa` varchar(200) NOT NULL,
  `notas` text NOT NULL,
  `data` date NOT NULL,
  `hora` time NOT NULL,
  `status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tblempresa`
--

INSERT INTO `tblempresa` (`id`, `empresa`, `notas`, `data`, `hora`, `status`) VALUES
(1, 'Alpha Sistemas', 'Cadastro inicial', '2026-01-05', '08:15:00', 'Ativo'),
(2, 'Beta Tecnologia', 'Reunião agendada', '2026-01-06', '09:30:00', 'Pendente'),
(3, 'Gamma Solutions', 'Cliente antigo', '2026-01-07', '10:45:00', 'Ativo'),
(4, 'Delta Corp', 'Aguardando retorno', '2026-01-08', '11:00:00', 'Inativo'),
(5, 'Epsilon Ltda', 'Primeiro contato', '2026-01-09', '14:20:00', 'Ativo'),
(6, 'Zeta Tech', 'Negociação em andamento', '2026-01-10', '15:10:00', 'Pendente'),
(7, 'Eta Sistemas', 'Contrato fechado', '2026-01-11', '16:00:00', 'Ativo'),
(8, 'Theta Serviços', 'Sem retorno', '2026-01-12', '13:25:00', 'Inativo'),
(9, 'Iota Consultoria', 'Proposta enviada', '2026-01-13', '17:45:00', 'Pendente'),
(10, 'Kappa Ltda', 'Cliente recorrente', '2026-01-14', '12:10:00', 'Ativo'),
(11, 'Lambda Tech', 'Visita técnica', '2026-01-15', '09:00:00', 'Ativo'),
(12, 'Mu Sistemas', 'Revisão pendente', '2026-01-16', '10:30:00', 'Pendente'),
(13, 'Nu Corp', 'Cancelado', '2026-01-17', '11:50:00', 'Inativo'),
(14, 'Xi Soluções', 'Teste piloto', '2026-01-18', '14:00:00', 'Ativo'),
(15, 'Omicron Ltda', 'Cliente novo', '2026-01-19', '15:30:00', 'Ativo'),
(16, 'Pi Tecnologia', 'Aguardando aprovação', '2026-01-20', '16:45:00', 'Pendente'),
(17, 'Rho Serviços', 'Sem interesse', '2026-01-21', '13:10:00', 'Inativo'),
(18, 'Sigma Consultoria', 'Follow-up', '2026-01-22', '17:00:00', 'Ativo'),
(19, 'Tau Sistemas', 'Implementação', '2026-01-23', '08:50:00', 'Ativo'),
(20, 'Upsilon Corp', 'Em análise', '2026-01-24', '09:40:00', 'Pendente'),
(21, 'Phi Tech', 'Retorno positivo', '2026-01-25', '10:15:00', 'Ativo'),
(22, 'Chi Soluções', 'Sem resposta', '2026-01-26', '11:25:00', 'Inativo'),
(23, 'Psi Ltda', 'Negociação', '2026-01-27', '14:55:00', 'Pendente'),
(24, 'Omega Sistemas', 'Contrato enviado', '2026-01-28', '15:35:00', 'Ativo'),
(25, 'Apex Tecnologia', 'Proposta aceita', '2026-01-29', '16:20:00', 'Ativo'),
(26, 'Vertex Corp', 'Cancelado', '2026-01-30', '13:15:00', 'Inativo'),
(27, 'Nexus Ltda', 'Reunião marcada', '2026-02-01', '17:30:00', 'Pendente'),
(28, 'Orbit Tech', 'Cliente fiel', '2026-02-02', '08:40:00', 'Ativo'),
(29, 'Fusion Sistemas', 'Teste aprovado', '2026-02-03', '09:55:00', 'Ativo'),
(30, 'Prime Consultoria', 'Aguardando docs', '2026-02-04', '10:35:00', 'Pendente'),
(31, 'Core Solutions', 'Implementado', '2026-02-05', '11:45:00', 'Ativo'),
(32, 'Logic Tecnologia', 'Sem retorno', '2026-02-06', '14:10:00', 'Inativo'),
(33, 'Data Corp', 'Ajustes finais', '2026-02-07', '15:25:00', 'Pendente'),
(34, 'Info Sistemas', 'Cliente ativo', '2026-02-08', '16:50:00', 'Ativo'),
(35, 'Smart Tech', 'Cancelado', '2026-02-09', '13:35:00', 'Inativo'),
(36, 'Digital Ltda', 'Proposta enviada', '2026-02-10', '17:20:00', 'Pendente'),
(37, 'Global Sistemas', 'Contrato ativo', '2026-02-11', '08:25:00', 'Ativo'),
(38, 'Next Corp', 'Sem interesse', '2026-02-12', '09:10:00', 'Inativo'),
(39, 'Future Tech', 'Aguardando retorno', '2026-02-13', '10:05:00', 'Pendente'),
(40, 'Infinity Ltda', 'Cliente VIP', '2026-02-14', '11:55:00', 'Ativo'),
(41, 'Tech Solutions', 'Manutenção', '2026-02-15', '14:45:00', 'Ativo'),
(42, 'Business Corp', 'Cancelado', '2026-02-16', '15:15:00', 'Inativo'),
(43, 'Enterprise Ltda', 'Negociação', '2026-02-17', '16:30:00', 'Pendente'),
(44, 'Network Sistemas', 'Ativo', '2026-02-18', '13:05:00', 'Ativo'),
(45, 'Cloud Tech', 'Sem resposta', '2026-02-19', '17:40:00', 'Inativo'),
(46, 'Vision Corp', 'Proposta enviada', '2026-02-20', '08:35:00', 'Pendente'),
(47, 'Bridge Ltda', 'Cliente novo', '2026-02-21', '09:20:00', 'Ativo'),
(48, 'Link Sistemas', 'Contrato fechado', '2026-02-22', '10:50:00', 'Ativo'),
(49, 'Matrix Tech', 'Aguardando aprovação', '2026-02-23', '11:15:00', 'Pendente'),
(50, 'Nova Empresa', 'Cadastro recente', '2026-02-24', '14:30:00', 'Ativo');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbluser`
--

CREATE TABLE `tbluser` (
  `id` int(11) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `telefone` varchar(30) NOT NULL,
  `email` varchar(150) NOT NULL,
  `status` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tbluser`
--

INSERT INTO `tbluser` (`id`, `nome`, `telefone`, `email`, `status`) VALUES
(1, 'João Silva Santos', '(11) 98765-4321', 'joao.silva@email.com', 'Suspenso'),
(2, 'Maria Oliveira Costa', '(21) 91234-5678', 'maria.oliveira@email.com', 'Ativo'),
(3, 'Pedro Souza Lima', '(31) 99876-5432', 'pedro.souza@email.com', 'Desativado'),
(4, 'Ana Paula Ferreira', '(41) 97654-3210', 'ana.ferreira@email.com', 'Ativo'),
(5, 'Carlos Eduardo Alves', '(51) 96543-2109', 'carlos.alves@email.com', 'Ativo'),
(6, 'Juliana Santos Rocha', '(61) 95432-1098', 'juliana.rocha@email.com', 'Desativado'),
(7, 'Fernando Costa Silva', '(71) 94321-0987', 'fernando.silva@email.com', 'Ativo'),
(8, 'Patrícia Lima Souza', '(81) 93210-9876', 'patricia.souza@email.com', 'Ativo'),
(9, 'Roberto Almeida Reis', '(85) 92109-8765', 'roberto.reis@email.com', 'Ativo'),
(10, 'Camila Rodrigues Dias', '(91) 91098-7654', 'camila.dias@email.com', 'Desativado'),
(11, 'tesdsfd safds', '43243243432432', 'terewrew@fdsafdsa.com', 'Ativo');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `tblempresa`
--
ALTER TABLE `tblempresa`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `tbluser`
--
ALTER TABLE `tbluser`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `tblempresa`
--
ALTER TABLE `tblempresa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de tabela `tbluser`
--
ALTER TABLE `tbluser`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
