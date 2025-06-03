CREATE TABLE notificacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titulo VARCHAR(100) NOT NULL,
    mensagem TEXT NOT NULL,
    data DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    assinatura_id INTEGER NOT NULL,
    CONSTRAINT fk_notificacao_assinatura FOREIGN KEY (assinatura_id)
        REFERENCES assinaturas(id)
);
