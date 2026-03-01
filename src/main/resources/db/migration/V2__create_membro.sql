CREATE TABLE membro (
                        id BIGSERIAL PRIMARY KEY,

                        pessoa_id BIGINT NOT NULL UNIQUE,
                        conjuge_id BIGINT,

                        cpf VARCHAR(11) NOT NULL UNIQUE,
                        rg VARCHAR(20),

                        pais VARCHAR(50),
                        ocupacao VARCHAR(100),

                        ativo BOOLEAN NOT NULL DEFAULT TRUE,
                        em_dia BOOLEAN NOT NULL DEFAULT FALSE,

                        nome_guerra VARCHAR(120),
                        tipo_sanguineo VARCHAR(5),

                        telefone VARCHAR(20),
                        email VARCHAR(150),

                        autorizou_uso_midias BOOLEAN NOT NULL DEFAULT FALSE,

                        CONSTRAINT fk_membro_pessoa
                            FOREIGN KEY (pessoa_id)
                                REFERENCES pessoa(id)
                                ON DELETE CASCADE,

                        CONSTRAINT fk_membro_conjuge
                            FOREIGN KEY (conjuge_id)
                                REFERENCES pessoa(id)
                                ON DELETE SET NULL
);