CREATE TABLE IF NOT EXISTS mountains (
    id                INT             GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name              VARCHAR(100)    NOT NULL,
    country_id        INT             NOT NULL REFERENCES countries (id) ON DELETE CASCADE,
    region            VARCHAR(100)    NOT NULL,
    latitude          DECIMAL(9, 6)   NOT NULL,
    longitude         DECIMAL(9, 6)   NOT NULL,
    is_volcano        BOOLEAN         DEFAULT FALSE,
    elevation_m       INT			  NOT NULL,
    type              VARCHAR(50),
    status            VARCHAR(20)     CHECK (status IN ('activo', 'dormido', 'extinto', 'inactivo')),
    last_eruption     VARCHAR(50),
    vei               SMALLINT        CHECK (vei BETWEEN 0 AND 8),
    casualties        INT             DEFAULT 0,
    monitored         BOOLEAN         DEFAULT FALSE,
    reto37            BOOLEAN         DEFAULT FALSE,
    former37          BOOLEAN         DEFAULT FALSE,
    image_url         TEXT,
    description       TEXT,
    created_at        TIMESTAMPTZ     DEFAULT NOW(),
    updated_at        TIMESTAMPTZ     DEFAULT NOW()
);

CREATE INDEX idx_mountains_country_id  ON mountains (country_id);
CREATE INDEX idx_mountains_is_volcano  ON mountains (is_volcano);
CREATE INDEX idx_mountains_status      ON mountains (status);

-- Auto-update trigger
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_mountains_updated_at
BEFORE UPDATE ON mountains
FOR EACH ROW EXECUTE FUNCTION update_timestamp();