CREATE TABLE assinaturas (
    id SERIAL PRIMARY KEY,
    nome_servico VARCHAR(100) NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    data_vencimento DATE NOT NULL,
    forma_pagamento VARCHAR(50),
    status VARCHAR(50)
);
