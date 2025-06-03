
ALTER TABLE assinaturas ADD COLUMN usuario_id UUID;


UPDATE assinaturas SET usuario_id = 'be57e1d5-9311-4c4f-94a9-df0bb30a861c' WHERE id IN (1, 2);


ALTER TABLE assinaturas
ADD CONSTRAINT fk_usuario_assinatura
FOREIGN KEY (usuario_id)
REFERENCES usuario(id);
