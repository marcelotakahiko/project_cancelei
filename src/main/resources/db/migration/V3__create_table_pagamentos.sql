CREATE TABLE pagamentos (
    id SERIAL PRIMARY KEY,
    valor NUMERIC(10,2) NOT NULL,
    metodo VARCHAR(255),
    status VARCHAR(100),
    data_pagamento DATE,
    assinatura_id BIGINT,
    CONSTRAINT fk_pagamento_assinatura FOREIGN KEY (assinatura_id)
        REFERENCES assinaturas(id)
);
