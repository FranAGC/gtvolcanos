CREATE TABLE guides (
    id                    INT              GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    -- Personal info
    first_name            VARCHAR(100)     NOT NULL,
    last_name             VARCHAR(100)     NOT NULL,
    phone                 VARCHAR(20)      NOT NULL,
    email                 VARCHAR(150)     UNIQUE,
    nationality           VARCHAR(100),
    spoken_languages      TEXT[],          -- e.g. {Spanish, English, French}
    profile_photo_url     TEXT,
    bio                   TEXT,
    -- Social media
    facebook              VARCHAR(150),
    instagram             VARCHAR(150),
    whatsapp              VARCHAR(20),
    -- Professional info
    license_number        VARCHAR(50)      UNIQUE,
    certified             BOOLEAN          NOT NULL DEFAULT FALSE,
    experience_years      SMALLINT         CHECK (experience_years >= 0),
    price_per_day_usd     DECIMAL(8, 2)    CHECK (price_per_day_usd >= 0),
    max_group_size        SMALLINT         CHECK (max_group_size > 0),
    -- Status
    active                BOOLEAN          NOT NULL DEFAULT TRUE,
    created_at            TIMESTAMPTZ      DEFAULT NOW(),
    updated_at            TIMESTAMPTZ      DEFAULT NOW()
);

-- A guide can operate in multiple mountains and a mountain can have multiple guides
CREATE TABLE guide_mountain (
    id                    INT              GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    guide_id              INT              NOT NULL REFERENCES guides (id) ON DELETE CASCADE,
    mountain_id            INT              NOT NULL REFERENCES mountains (id) ON DELETE CASCADE,
    is_primary            BOOLEAN          NOT NULL DEFAULT FALSE,
    UNIQUE (guide_id, mountain_id)
);

-- Indexes
CREATE INDEX idx_guides_active          ON guides (active);
CREATE INDEX idx_guides_certified       ON guides (certified);
CREATE INDEX idx_guide_mountain_guide    ON guide_mountain (guide_id);
CREATE INDEX idx_guide_mountain_mountain  ON guide_mountain (mountain_id);

-- Auto-update trigger
CREATE TRIGGER trg_guides_updated_at
BEFORE UPDATE ON guides
FOR EACH ROW EXECUTE FUNCTION update_timestamp();
