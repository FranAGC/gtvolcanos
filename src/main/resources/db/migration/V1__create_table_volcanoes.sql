CREATE TABLE IF NOT EXISTS volcanoes (
    id                INT             GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name              VARCHAR(100)    NOT NULL,
    country           VARCHAR(100)    NOT NULL,
    region            VARCHAR(100),
    latitude          DECIMAL(9, 6)   NOT NULL,
    longitude         DECIMAL(9, 6)   NOT NULL,
    elevation_m       INT			  NOT NULL,
    type              VARCHAR(50),
    status            VARCHAR(20)     CHECK (status IN ('activo', 'dormido', 'extinto')),
    last_eruption     SMALLINT,
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

-- Auto-update trigger
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trg_volcanoes_updated_at
BEFORE UPDATE ON volcanoes
FOR EACH ROW EXECUTE FUNCTION update_timestamp();