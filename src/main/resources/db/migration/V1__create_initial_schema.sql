-- Ativa a extensão unaccent
CREATE EXTENSION IF NOT EXISTS unaccent;

-- Tabela de Empresas
CREATE TABLE companies (
    id BIGSERIAL PRIMARY KEY,
    legal_name VARCHAR(200) NOT NULL,   -- Razão Social
    trade_name VARCHAR(200),             -- Nome Fantasia
    cnpj VARCHAR(18) UNIQUE,             -- CNPJ
    logo_url TEXT,
    primary_color VARCHAR(20),
    secondary_color VARCHAR(20),
    email VARCHAR(150),
    phone VARCHAR(20),
    address VARCHAR(200),
    city VARCHAR(100),
    state VARCHAR(2),
    zip_code VARCHAR(10),
    tenant_code VARCHAR(50) UNIQUE NOT NULL, -- usado em URLs customizadas
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de Sementes
CREATE TABLE seeds (
    id BIGSERIAL PRIMARY KEY,
    species VARCHAR(100) NOT NULL,
    cultivar VARCHAR(100) NOT NULL,
    normalized_species VARCHAR(100),
    normalized_cultivar VARCHAR(100),
    is_protected BOOLEAN NOT NULL DEFAULT FALSE,
    company_id BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT unique_seed_entry UNIQUE (normalized_species, normalized_cultivar)
);

-- Tabela de Clientes
CREATE TABLE clients (
     id BIGSERIAL PRIMARY KEY,
     number VARCHAR(20) UNIQUE,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(100),
     phone VARCHAR(20),
     company_id BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP,
     active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de Usuários
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100),
    tenant_role VARCHAR(20) NOT NULL, -- OWNER, ADMIN, STANDARD, READ_ONLY
    system_role VARCHAR(20),          -- SUPER_ADMIN
    company_id BIGINT REFERENCES companies(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    last_login TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de Notas Fiscais de entrada
CREATE TABLE invoices (
    id BIGSERIAL PRIMARY KEY,
    invoice_number VARCHAR(50) NOT NULL UNIQUE,
    producer_name VARCHAR(100) NOT NULL,
    seed_id BIGINT NOT NULL REFERENCES seeds(id),
    total_kg DECIMAL(10,2) NOT NULL,
    balance DECIMAL(10,2) NOT NULL,
    operation_type VARCHAR(50) NOT NULL, -- REPACKAGING, TRANSFER
    auth_number VARCHAR(50),
    category VARCHAR(20) NOT NULL,
    purity DECIMAL(5,2) NOT NULL,
    harvest VARCHAR(20) NOT NULL,
    production_state VARCHAR(2) NOT NULL,
    planted_area DECIMAL(10,2),
    approved_area DECIMAL(10,2),
    company_id BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela auxiliar para lot_number
CREATE TABLE lot_sequences (
    id BIGSERIAL PRIMARY KEY,
    year INTEGER NOT NULL,
    last_number INTEGER NOT NULL,
    company_id BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    reset_done BOOLEAN DEFAULT FALSE,
    reset_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela Sacaria
CREATE TABLE bag_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    company_id BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

--Tabela peso da sacaria
CREATE TABLE bag_weights (
    id BIGSERIAL PRIMARY KEY,
    weight DECIMAL(10,2) NOT NULL UNIQUE, -- Ex: 1, 5, 10, 15, 20, 25, 600, 800, 1000
    company_id BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de laboratórios
CREATE TABLE labs (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    state VARCHAR(2) NOT NULL,
    renasem_code VARCHAR(30) NOT NULL UNIQUE,
    company_id BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de Lotes
CREATE TABLE lots (
    id BIGSERIAL PRIMARY KEY,
    lot_number VARCHAR(50) NOT NULL UNIQUE,
    lot_type VARCHAR(20) NOT NULL, -- INTERNAL_SALE, EXPORT
    seed_type VARCHAR(30) NOT NULL, -- COATED, GRAPHITE, COATED_GRAPHITE, CONVENTIONAL
    category VARCHAR(10) NOT NULL, -- S1, S2
    bag_weight_id BIGINT NOT NULL REFERENCES bag_weights(id),
    bag_type_id BIGINT NOT NULL REFERENCES bag_types(id),
    quantity_total DECIMAL(10,2) NOT NULL,
    balance DECIMAL(10,2) NOT NULL,
    production_order VARCHAR(50),
    analysis_bulletin VARCHAR(50),
    bulletin_date DATE,
    hard_seeds INTEGER NOT NULL DEFAULT 0,
    wild_seeds INTEGER NOT NULL DEFAULT 0,
    other_cultivated_species INTEGER NOT NULL DEFAULT 0,
    tolerated INTEGER NOT NULL DEFAULT 0,
    prohibited INTEGER NOT NULL DEFAULT 0,
    validity_date DATE,
    seed_score INTEGER NOT NULL DEFAULT 0,
    purity DECIMAL(5,2) NOT NULL,
    lab_id BIGINT REFERENCES labs(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    company_id BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela Lotes com notas fiscais
CREATE TABLE lot_invoices (
    id BIGSERIAL PRIMARY KEY,
    lot_id BIGINT NOT NULL REFERENCES lots(id) ON DELETE CASCADE,
    invoice_id BIGINT NOT NULL REFERENCES invoices(id) ON DELETE CASCADE,
    allocated_quantity_lot DECIMAL(10,2) NOT NULL,
    allocated_quantity_invoice DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Baixas de Lotes
CREATE TABLE lot_withdrawals (
    id BIGSERIAL PRIMARY KEY,
    lot_id BIGINT NOT NULL REFERENCES lots(id),
    invoice_number VARCHAR(50),
    quantity DECIMAL(10,2) NOT NULL,
    withdrawal_date DATE NOT NULL,
    state VARCHAR(2) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id),
    client_id BIGINT NOT NULL  REFERENCES clients(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de Reservas de Lotes
CREATE TABLE lot_reservations (
    id BIGSERIAL PRIMARY KEY,
    lot_id BIGINT NOT NULL REFERENCES lots(id),
    quantity DECIMAL(10,2) NOT NULL,
    identification VARCHAR(50),
    reservation_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL, -- RESERVED, WITHDRAWAL, CANCELLED
    user_id BIGINT NOT NULL REFERENCES users(id),
    client_id BIGINT REFERENCES clients(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Tabela de Responsáveis Técnicos
CREATE TABLE technical_responsibles (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    CONSTRAINT chk_tr_cpf_format CHECK (cpf ~ '^[0-9]{11}$'),
    renasem_number VARCHAR(50),
    crea_number VARCHAR(50),
    address TEXT,
    city VARCHAR(120),
    state VARCHAR(2),
    zip_code VARCHAR(10),
    phone VARCHAR(30),
    email VARCHAR(255),
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

-- Índices para otimização
-- Índices para seeds
CREATE INDEX idx_seeds_species ON seeds(species);
CREATE INDEX idx_seeds_cultivar ON seeds(cultivar);
CREATE INDEX idx_seeds_active ON seeds(active);
CREATE INDEX idx_seeds_company_id ON seeds(company_id);
CREATE INDEX idx_seeds_company_active ON seeds(company_id, active);

-- Índices para clients
CREATE INDEX idx_clients_name ON clients(name);
CREATE INDEX idx_clients_number ON clients(number);
CREATE INDEX idx_clients_active ON clients(active);
CREATE INDEX idx_clients_company_id ON clients(company_id);
CREATE INDEX idx_clients_company_active ON clients(company_id, active);

-- Índices para users
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_tenant_role ON users(tenant_role);
CREATE INDEX idx_users_system_role ON users(system_role);
CREATE INDEX idx_users_active ON users(active);
CREATE INDEX idx_users_company_id ON users(company_id);
CREATE INDEX idx_users_company_active ON users(company_id, active);

-- Índices para invoices
CREATE INDEX idx_invoices_invoice_number ON invoices(invoice_number);
CREATE INDEX idx_invoices_seed_id ON invoices(seed_id);
CREATE INDEX idx_invoices_producer_name ON invoices(producer_name);
CREATE INDEX idx_invoices_active ON invoices(active);
CREATE INDEX idx_invoices_company_id ON invoices(company_id);
CREATE INDEX idx_invoices_company_active ON invoices(company_id, active);

-- Índices para lots
CREATE INDEX idx_lots_lot_number ON lots(lot_number);
CREATE INDEX idx_lots_lab_id ON lots(lab_id);
CREATE INDEX idx_lots_user_id ON lots(user_id);
CREATE INDEX idx_lots_bag_type_id ON lots(bag_type_id);
CREATE INDEX idx_lots_bag_weight_id ON lots(bag_weight_id);
CREATE INDEX idx_lots_category ON lots(category);
CREATE INDEX idx_lots_validity_date ON lots(validity_date);
CREATE INDEX idx_lots_active ON lots(active);
CREATE INDEX idx_lots_company_id ON lots(company_id);
CREATE INDEX idx_lots_company_active ON lots(company_id, active);

-- Índices para bag_types
CREATE INDEX idx_bag_types_name ON bag_types(name);
CREATE INDEX idx_bag_types_active ON bag_types(active);
CREATE INDEX idx_bag_types_company_id ON bag_types(company_id);
CREATE INDEX idx_bag_types_company_active ON bag_types(company_id, active);

-- Índices para bag_weights
CREATE INDEX idx_bag_weights_weight ON bag_weights(weight);
CREATE INDEX idx_bag_weights_active ON bag_weights(active);
CREATE INDEX idx_bag_weights_company_id ON bag_weights(company_id);
CREATE INDEX idx_bag_weights_company_active ON bag_weights(company_id, active);

-- Índices para labs
CREATE INDEX idx_labs_name ON labs(name);
CREATE INDEX idx_labs_renasem_code ON labs(renasem_code);
CREATE INDEX idx_labs_active ON labs(active);
CREATE INDEX idx_labs_company_id ON labs(company_id);
CREATE INDEX idx_labs_company_active ON labs(company_id, active);

-- Índices para lot_withdrawals
CREATE INDEX idx_lot_withdrawals_lot_id ON lot_withdrawals(lot_id);
CREATE INDEX idx_lot_withdrawals_user_id ON lot_withdrawals(user_id);
CREATE INDEX idx_lot_withdrawals_client_id ON lot_withdrawals(client_id);
CREATE INDEX idx_lot_withdrawals_withdrawal_date ON lot_withdrawals(withdrawal_date);

-- Índices para lot_reservations
CREATE INDEX idx_lot_reservations_lot_id ON lot_reservations(lot_id);
CREATE INDEX idx_lot_reservations_client_id ON lot_reservations(client_id);
CREATE INDEX idx_lot_reservations_status ON lot_reservations(status);
CREATE INDEX idx_lot_reservations_reservation_date ON lot_reservations(reservation_date);

-- Índices para lot_invoices
CREATE INDEX idx_lot_invoices_lot_id ON lot_invoices(lot_id);
CREATE INDEX idx_lot_invoices_invoice_id ON lot_invoices(invoice_id);

-- Índices para companies
CREATE UNIQUE INDEX idx_companies_tenant_code ON companies(tenant_code);
CREATE UNIQUE INDEX idx_companies_cnpj ON companies(cnpj);
CREATE INDEX idx_companies_trade_name ON companies(trade_name);
CREATE INDEX idx_companies_legal_name ON companies(legal_name);
CREATE INDEX idx_companies_email ON companies(email);
CREATE INDEX idx_companies_active ON companies(active);
CREATE INDEX idx_companies_active_trade ON companies(active, trade_name);

-- Índices para technical_responsibles
CREATE INDEX idx_technical_responsibles_name ON technical_responsibles(name);
CREATE INDEX idx_tr_company_cpf ON technical_responsibles(company_id, cpf);
CREATE INDEX idx_technical_responsibles_company_id ON technical_responsibles(company_id);
CREATE INDEX idx_technical_responsibles_active ON technical_responsibles(active);
CREATE INDEX idx_tr_company_active ON technical_responsibles(company_id, active);
CREATE UNIQUE INDEX uk_tr_cpf_company ON technical_responsibles(company_id, cpf);
CREATE UNIQUE INDEX uk_tr_one_primary_per_company ON technical_responsibles(company_id) WHERE is_primary = TRUE;

-- Criação do trigger normalização sementes
CREATE OR REPLACE FUNCTION normalize_seed_fields()
RETURNS TRIGGER AS $$
BEGIN
  NEW.normalized_species := LOWER(unaccent(TRIM(NEW.species)));
  NEW.normalized_cultivar := UPPER(unaccent(TRIM(NEW.cultivar)));
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_normalize_seed
    BEFORE INSERT OR UPDATE ON seeds
                         FOR EACH ROW
                         EXECUTE FUNCTION normalize_seed_fields();