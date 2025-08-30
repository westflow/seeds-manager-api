-- Tabela de Sementes
CREATE TABLE seeds (
    id BIGSERIAL PRIMARY KEY,
    species VARCHAR(100) NOT NULL,
    cultivar VARCHAR(100) NOT NULL,
    is_protected BOOLEAN NOT NULL DEFAULT FALSE
);

-- Tabela de Clientes
CREATE TABLE clients (
     id BIGSERIAL PRIMARY KEY,
     number VARCHAR(20) NOT NULL,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(100),
     phone VARCHAR(20),
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP
);

-- Tabela de Usuários
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100),
    access_level VARCHAR(20) NOT NULL, -- ADMIN, STANDARD, READ_ONLY
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    last_login TIMESTAMP
);

-- Tabela de Notas Fiscais de entrada
CREATE TABLE invoices (
    id BIGSERIAL PRIMARY KEY,
    invoice_number VARCHAR(50) NOT NULL UNIQUE,
    producer_name VARCHAR(100) NOT NULL,
    seed_id BIGINT NOT NULL REFERENCES seeds(id),
    total_kg DECIMAL(10,2) NOT NULL,
    operation_type VARCHAR(50) NOT NULL, -- REPACKAGING, TRANSFER
    auth_number VARCHAR(50),
    category VARCHAR(20) NOT NULL,
    purity DECIMAL(5,2) NOT NULL,
    harvest VARCHAR(20) NOT NULL,
    production_state VARCHAR(2) NOT NULL,
    planted_area DECIMAL(10,2),
    approved_area DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Tabela auxiliar para lot_number
CREATE TABLE lot_sequences (
    id BIGSERIAL PRIMARY KEY,
    year INTEGER NOT NULL,
    last_number INTEGER NOT NULL,
    reset_done BOOLEAN DEFAULT FALSE,
    reset_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela Sacaria
CREATE TABLE bag_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

--Tabela peso da sacaria
CREATE TABLE bag_weights (
    id BIGSERIAL PRIMARY KEY,
    weight DECIMAL(10,2) NOT NULL UNIQUE -- Ex: 1, 5, 10, 15, 20, 25, 600, 800, 1000
);

-- Tabela de laboratórios
CREATE TABLE labs (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    state VARCHAR(2) NOT NULL,
    renasem_code VARCHAR(30) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Tabela de Lotes
CREATE TABLE lots (
    id BIGSERIAL PRIMARY KEY,
    lot_number VARCHAR(50) NOT NULL UNIQUE,
    lot_type VARCHAR(20) NOT NULL, -- INTERNAL_SALE, EXPORT
    seed_id BIGINT NOT NULL REFERENCES seeds(id),
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Tabela Lotes com notas fiscais
CREATE TABLE lot_invoices (
    id BIGSERIAL PRIMARY KEY,
    lot_id BIGINT NOT NULL REFERENCES lots(id) ON DELETE CASCADE,
    invoice_id BIGINT NOT NULL REFERENCES invoices(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Baixas de Lotes
CREATE TABLE lot_withdrawals (
    id BIGSERIAL PRIMARY KEY,
    lot_id BIGINT NOT NULL REFERENCES lots(id),
    invoice_number VARCHAR(50),
    quantity DECIMAL(10,2) NOT NULL,
    seed_score INTEGER NOT NULL,
    withdrawal_date DATE NOT NULL,
    state VARCHAR(2) NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id),
    client_id BIGINT NOT NULL  REFERENCES clients(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Tabela de Reservas de Lotes
CREATE TABLE lot_reservations (
    id BIGSERIAL PRIMARY KEY,
    lot_id BIGINT NOT NULL REFERENCES lots(id),
    quantity DECIMAL(10,2) NOT NULL,
    identification VARCHAR(50),
    reservation_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL, -- CONFIRMED, WITHDRAWAL, CANCELLED
    user_id BIGINT NOT NULL REFERENCES users(id),
    client_id BIGINT REFERENCES clients(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Índices para otimização
-- Índices para seeds
CREATE INDEX idx_seeds_species ON seeds(species);
CREATE INDEX idx_seeds_cultivar ON seeds(cultivar);

-- Índices para clients
CREATE INDEX idx_clients_name ON clients(name);
CREATE INDEX idx_clients_number ON clients(number);

-- Índices para users
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_access_level ON users(access_level);

-- Índices para invoices
CREATE INDEX idx_invoices_invoice_number ON invoices(invoice_number);
CREATE INDEX idx_invoices_seed_id ON invoices(seed_id);
CREATE INDEX idx_invoices_producer_name ON invoices(producer_name);

-- Índices para lots
CREATE INDEX idx_lots_lot_number ON lots(lot_number);
CREATE INDEX idx_lots_seed_id ON lots(seed_id);
CREATE INDEX idx_lots_lab_id ON lots(lab_id);
CREATE INDEX idx_lots_user_id ON lots(user_id);
CREATE INDEX idx_lots_bag_type_id ON lots(bag_type_id);
CREATE INDEX idx_lots_bag_weight_id ON lots(bag_weight_id);
CREATE INDEX idx_lots_category ON lots(category);
CREATE INDEX idx_lots_validity_date ON lots(validity_date);

-- Índices para lot_sequences
CREATE INDEX idx_lot_sequences_year ON lot_sequences(year);
CREATE INDEX idx_lot_sequences_reset_done ON lot_sequences(reset_done);

-- Índices para bag_types
CREATE INDEX idx_bag_types_name ON bag_types(name);

-- Índices para bag_weights
CREATE INDEX idx_bag_weights_weight ON bag_weights(weight);

-- Índices para labs
CREATE INDEX idx_labs_name ON labs(name);
CREATE INDEX idx_labs_renasem_code ON labs(renasem_code);

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