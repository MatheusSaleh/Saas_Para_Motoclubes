CREATE TABLE pagamento (
                           id BIGSERIAL PRIMARY KEY,

                           membro_id BIGINT NOT NULL,

                           data_pagamento DATE,
                           mes_referencia VARCHAR(20) NOT NULL,

                           valor DECIMAL(10,2) NOT NULL CHECK (valor >= 0),

                           forma_pagamento VARCHAR(20) NOT NULL,
                           situacao VARCHAR(50) NOT NULL,

                           data_vencimento DATE NOT NULL,

                           CONSTRAINT fk_pagamento_membro
                               FOREIGN KEY (membro_id)
                                   REFERENCES membro(id)
                                   ON DELETE CASCADE
);