CREATE TABLE IF NOT EXISTS contact_agents (
    id BIGSERIAL PRIMARY KEY,
    agent_key UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50)
);


CREATE TABLE IF NOT EXISTS contacts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50),
    gender VARCHAR(50),
    age_group VARCHAR(50),
    agent_id bigint,
    CONSTRAINT fk_agent_id FOREIGN KEY(agent_id) REFERENCES contacts(id)
);
