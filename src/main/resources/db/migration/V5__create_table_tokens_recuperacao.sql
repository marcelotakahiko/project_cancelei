CREATE TABLE tokens_recuperacao (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    expiracao TIMESTAMP NOT NULL,
    usado BOOLEAN DEFAULT FALSE,
    usuario_id UUID,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
