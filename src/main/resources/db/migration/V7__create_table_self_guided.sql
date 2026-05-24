CREATE TABLE self_guided_tours (
    id                    INT             GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    mountain_id            INT             NOT NULL REFERENCES mountains (id) ON DELETE CASCADE,
    title                 VARCHAR(150)    NOT NULL,
    difficulty            VARCHAR(20)     NOT NULL CHECK (difficulty IN ('fácil', 'moderado', 'difícil', 'experto')),
    distance_km           DECIMAL(6, 2),
    estimated_duration_hrs DECIMAL(4, 1),
    starting_point_name   VARCHAR(150),
    starting_point_lat    DECIMAL(9, 6),
    starting_point_lng    DECIMAL(9, 6),
    instructions          TEXT            NOT NULL,
    recommended_gear      TEXT[],
    active                BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at            TIMESTAMPTZ     DEFAULT NOW(),
    updated_at            TIMESTAMPTZ     DEFAULT NOW()
);

CREATE INDEX idx_self_guided_mountain    ON self_guided_tours (mountain_id);
CREATE INDEX idx_self_guided_difficulty ON self_guided_tours (difficulty);

-- Auto-update trigger
CREATE TRIGGER trg_self_guided_updated_at
BEFORE UPDATE ON self_guided_tours
FOR EACH ROW EXECUTE FUNCTION update_timestamp();

CREATE TABLE src_mountains (
    id                    INT             GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    mountain_id           INT             REFERENCES mountains (id) ON DELETE CASCADE,
    self_guided_tour_id   INT             REFERENCES self_guided_tours (id) ON DELETE CASCADE,
    type                  VARCHAR(20)     NOT NULL CHECK (type IN ('video', 'ruta', 'guia', 'post', 'imagen', 'otro')),
    description           VARCHAR(200)    NOT NULL,
    src_url               TEXT            NOT NULL,
    app_page              VARCHAR(100),
    additional_info        VARCHAR(200),
    created_at            TIMESTAMPTZ     NOT NULL DEFAULT NOW()  -- útil para ordenar recursos
);
CREATE INDEX idx_src_mountains_mountain_id ON src_mountains (mountain_id);
