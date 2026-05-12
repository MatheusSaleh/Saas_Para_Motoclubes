CREATE TABLE moto (
    id BIGSERIAL PRIMARY KEY,

    membro_id BIGINT NOT NULL,

    marca VARCHAR(80) NOT NULL,
    modelo VARCHAR(120) NOT NULL,

    ano_fabricacao INTEGER,
    placa VARCHAR(10),
    cor VARCHAR(40),
    cilindradas INTEGER,
    observacao TEXT,

    CONSTRAINT fk_moto_membro
        FOREIGN KEY (membro_id)
            REFERENCES membro(id)
            ON DELETE CASCADE
);

CREATE INDEX idx_moto_membro ON moto (membro_id);
