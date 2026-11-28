INSERT INTO unidade (unidade_id, nome, cidade, ativa) VALUES
(1, 'Raízes Boa Viagem', 'Recife', TRUE),
(2, 'Raízes Aldeota', 'Fortaleza', TRUE),
(3, 'Raízes Centro (formato reduzido)', 'Caruaru', TRUE);

INSERT INTO produto (produto_id, nome, descricao, preco_base, ativo) VALUES
(1, 'Tapioca de queijo coalho', 'Tapioca recheada com queijo coalho', 12.50, TRUE),
(2, 'Tapioca de carne de sol', 'Tapioca recheada com carne de sol e manteiga de garrafa', 16.90, TRUE),
(3, 'Cuscuz recheado com frango', 'Cuscuz nordestino recheado com frango desfiado', 14.00, TRUE),
(4, 'Bolo de macaxeira', 'Fatia de bolo de macaxeira', 8.50, TRUE),
(5, 'Suco de cajá', 'Suco natural de cajá, 400 ml', 7.00, TRUE),
(6, 'Café coado', 'Café passado na hora', 4.50, TRUE),
(7, 'Canjica (sazonal junino)', 'Canjica cremosa, disponível no período junino', 9.00, TRUE);

-- Unidade 1: cozinha completa (suco de cajá com estoque zerado, para testar indisponibilidade)
INSERT INTO estoque (unidade_id, produto_id, saldo_fisico, reservado, ofertado_na_unidade) VALUES
(1, 1, 30, 0, TRUE),
(1, 2, 20, 0, TRUE),
(1, 3, 25, 0, TRUE),
(1, 4, 15, 0, TRUE),
(1, 5, 0, 0, TRUE),
(1, 6, 50, 0, TRUE),
(1, 7, 10, 0, TRUE);

-- Unidade 2: cozinha completa, sem o produto sazonal
INSERT INTO estoque (unidade_id, produto_id, saldo_fisico, reservado, ofertado_na_unidade) VALUES
(2, 1, 25, 0, TRUE),
(2, 2, 18, 0, TRUE),
(2, 3, 20, 0, TRUE),
(2, 4, 12, 0, TRUE),
(2, 5, 30, 0, TRUE),
(2, 6, 40, 0, TRUE),
(2, 7, 0, 0, FALSE);

-- Unidade 3: formato reduzido (só alguns produtos), com um item de estoque mínimo (1 unidade)
INSERT INTO estoque (unidade_id, produto_id, saldo_fisico, reservado, ofertado_na_unidade) VALUES
(3, 1, 1, 0, TRUE),
(3, 4, 10, 0, TRUE),
(3, 6, 20, 0, TRUE);