create table usuario (
                         usuario_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) unique NOT NULL,
                         senha_hash VARCHAR(255) NOT NULL,
                         perfil ENUM('CLIENTE', 'ATENDENTE', 'COZINHA', 'GERENTE') NOT NULL,
                         consentimento_lgpd boolean DEFAULT FALSE NOT NULL,
                         data_consentimento datetime
);

create table unidade (
                         unidade_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome varchar(100) NOT NULL,
                         cidade varchar(30),
                         ativa boolean NOT NULL
);

create table produto (
                         produto_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nome varchar (100) NOT NULL,
                         descricao varchar (255),
                         preco_base DECIMAL(10,2) NOT NULL,
                         ativo boolean NOT NULL
);

create table estoque (
                         estoque_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         saldo_fisico int NOT NULL DEFAULT 0,
                         reservado int DEFAULT 0 NOT NULL,
                         ofertado_na_unidade boolean DEFAULT TRUE NOT NULL,
                         unidade_id BIGINT NOT NULL,
                         produto_id BIGINT NOT NULL,
                         FOREIGN KEY (unidade_id) REFERENCES unidade(unidade_id),
                         FOREIGN KEY (produto_id) REFERENCES produto(produto_id),
                         CONSTRAINT uq_estoque_unidade_produto UNIQUE (unidade_id, produto_id),
                         CONSTRAINT ck_estoque_reservado CHECK (reservado >= 0 AND reservado <= saldo_fisico),
                         CONSTRAINT ck_estoque_saldo CHECK (saldo_fisico >= 0)
);

create table pedido (
                        pedido_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        unidade_id BIGINT NOT NULL,
                        usuario_id BIGINT NOT NULL,
                        canal_pedido ENUM('APP', 'TOTEM', 'BALCAO', 'PICKUP', 'WEB') NOT NULL,
                        status ENUM('AGUARDANDO_PAGAMENTO','CONFIRMADO','EM_PREPARO', 'PRONTO', 'CANCELADO', 'ENTREGUE') NOT NULL,
                        expira_em datetime,
                        criado_em datetime default current_timestamp NOT NULL,
                        total DECIMAL(10,2) NOT NULL,
                        foreign key (unidade_id) references unidade (unidade_id),
                        FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
);

create table item_pedido (
                             item_pedido_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             pedido_id BIGINT NOT NULL,
                             produto_id BIGINT NOT NULL,
                             quantidade int NOT NULL,
                             preco_unitario DECIMAL(10,2) NOT NULL,
                             FOREIGN KEY (pedido_id) REFERENCES pedido(pedido_id),
                             foreign key (produto_id) references produto (produto_id),
                             CONSTRAINT ck_item_quantidade CHECK (quantidade > 0)
);

create table pagamento (
                           pagamento_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           pedido_id BIGINT NOT NULL,
                           status ENUM('APROVADO','RECUSADO','TIMEOUT') NOT NULL,
                           valor DECIMAL(10,2) NOT NULL,
                           chave_idempotencia VARCHAR(100) unique NOT NULL,
                           resposta_gateway text,
                           criado_em datetime DEFAULT CURRENT_TIMESTAMP,
                           foreign key (pedido_id) references pedido (pedido_id),
                           pedido_aprovado BIGINT GENERATED ALWAYS AS
                               (IF(status = 'APROVADO', pedido_id, NULL)) STORED,
                           CONSTRAINT uq_um_aprovado_por_pedido UNIQUE (pedido_aprovado)
);

create table auditoria_log (
                               auditoria_log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               usuario_id BIGINT NOT NULL,
                               acao varchar(50) NOT NULL,
                               entidade VARCHAR(50),
                               entidade_id bigint NOT NULL,
                               valor_antes text,
                               valor_depois text,
                               criado_em datetime DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
);
