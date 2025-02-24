CREATE TABLE IF NOT EXISTS transacao (
                                       id SERIAL PRIMARY KEY,
                                       tipo INTEGER,
                                       data DATE,
                                       valor NUMERIC(10, 2),
                                       cpf BIGINT,
                                       cartao VARCHAR(16),
                                       hora TIME,
                                       dono_da_loja VARCHAR(14),
                                       nome_da_loja VARCHAR(19)
);
