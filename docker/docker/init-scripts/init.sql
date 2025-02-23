CREATE TABLE IF NOT EXISTS transacao(
                                        id SERIAL PRIMARY KEY,
                                        tipo INT,
                                        data DATE,
                                        valor DECIMAL,
                                        cpf BIGINT,
                                        cartao VARCHAR(250),
    hora TIME,
    dono_da_loja VARCHAR(250),
    nome_da_loja VARCHAR(250)
    );
