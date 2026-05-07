CREATE TABLE users (
    id           INT         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email        VARCHAR(150) NOT NULL UNIQUE,
    password     TEXT         NOT NULL,
    refresh_token TEXT,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    role         VARCHAR(20)  NOT NULL DEFAULT 'editor'
                              CHECK (role IN ('admin', 'editor', 'viewer')),
    active       BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMPTZ  DEFAULT NOW(),
    updated_at   TIMESTAMPTZ  DEFAULT NOW()
);

CREATE INDEX idx_users_email         ON users (email);
CREATE INDEX idx_users_role          ON users (role);
CREATE INDEX idx_users_active        ON users (active);
CREATE INDEX idx_users_refresh_token ON users (refresh_token);

CREATE TRIGGER trg_users_updated_at
BEFORE UPDATE ON users
FOR EACH ROW EXECUTE FUNCTION update_timestamp();
