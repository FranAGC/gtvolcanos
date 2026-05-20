CREATE TABLE tourism_info (
    id                    INT             GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    mountain_id           INT             REFERENCES mountains (id) ON DELETE CASCADE,
    difficulty            VARCHAR(20)     CHECK (difficulty IN ('fácil', 'moderado', 'difícil', 'experto')),
    hiking_trail          BOOLEAN         DEFAULT FALSE,
    guided_tour_required  BOOLEAN         DEFAULT FALSE,
    entrance_fee_usd      DECIMAL(6, 2),
    best_season           VARCHAR(50),
    nearest_city          VARCHAR(100),
    distance_to_city_km   DECIMAL(6, 2),
    visit_duration_hrs    DECIMAL(4, 1),
    parking               BOOLEAN         DEFAULT FALSE,
    restrooms             BOOLEAN         DEFAULT FALSE,
    visitor_center        BOOLEAN         DEFAULT FALSE,
    camping_allowed       BOOLEAN         DEFAULT FALSE,
    food_nearby           BOOLEAN         DEFAULT FALSE,
    current_alert_level   VARCHAR(20)     CHECK (current_alert_level IN ('verde', 'amarillo', 'naranja', 'rojo')),
    emergency_contact     VARCHAR(100),
	details		          TEXT,
    created_at            TIMESTAMPTZ     DEFAULT NOW(),
    updated_at            TIMESTAMPTZ     DEFAULT NOW()
);

CREATE INDEX idx_tourism_info_mountain_id     ON tourism_info (mountain_id);
CREATE INDEX idx_tourism_info_difficulty      ON tourism_info (difficulty);
CREATE INDEX idx_tourism_info_alert_level     ON tourism_info (current_alert_level);

CREATE TRIGGER trg_tourism_info_updated_at
BEFORE UPDATE ON tourism_info
FOR EACH ROW EXECUTE FUNCTION update_timestamp();
