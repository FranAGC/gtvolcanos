CREATE TABLE self_guided_tours (
    id                    INT             GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    volcano_id            INT             NOT NULL REFERENCES volcanoes (id) ON DELETE CASCADE,
    title                 VARCHAR(150)    NOT NULL,
    difficulty            VARCHAR(20)     NOT NULL CHECK (difficulty IN ('easy', 'moderate', 'hard', 'expert')),
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

CREATE INDEX idx_self_guided_volcano    ON self_guided_tours (volcano_id);
CREATE INDEX idx_self_guided_difficulty ON self_guided_tours (difficulty);

-- Auto-update trigger
CREATE TRIGGER trg_self_guided_updated_at
BEFORE UPDATE ON self_guided_tours
FOR EACH ROW EXECUTE FUNCTION update_timestamp();

CREATE TABLE src_self_guided_tours (
    id                    INT             GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    self_guided_tour_id   INT             NOT NULL REFERENCES self_guided_tours (id) ON DELETE CASCADE,
    type                  VARCHAR(20)     NOT NULL CHECK (type IN ('video', 'route', 'guide', 'other')),
    description           VARCHAR(200)    NOT NULL,
    src_url               TEXT            NOT NULL,
    app_page              VARCHAR(100),
    created_at            TIMESTAMPTZ     NOT NULL DEFAULT NOW()  -- útil para ordenar recursos
);

CREATE INDEX idx_src_self_guided_tour_id ON src_self_guided_tours (self_guided_tour_id);
