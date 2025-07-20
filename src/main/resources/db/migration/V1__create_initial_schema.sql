-- Tabela de Sementes
CREATE TABLE seeds (
    id SERIAL PRIMARY KEY,
    species VARCHAR(100) NOT NULL,
    cultivar VARCHAR(100) NOT NULL
);

-- Tabela de Clientes
CREATE TABLE clients (
     id SERIAL PRIMARY KEY,
     number VARCHAR(20) NOT NULL,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(100),
     phone VARCHAR(20),
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Usuários
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(100),
    access_level VARCHAR(20) NOT NULL, -- ADMIN, STANDARD, READ_ONLY
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);

-- Tabela de Notas Fiscais
CREATE TABLE invoices (
    id SERIAL PRIMARY KEY,
    invoice_number VARCHAR(50) NOT NULL,
    producer_name VARCHAR(100) NOT NULL,
    cultivar_id INTEGER REFERENCES seeds(id),
    total_kg DECIMAL(10,2) NOT NULL,
    operation_type VARCHAR(50) NOT NULL, -- REPACKAGING, TRANSFER
    auth_number VARCHAR(50),
    category VARCHAR(20) NOT NULL,
    purity DECIMAL(5,2) NOT NULL,
    harvest VARCHAR(20) NOT NULL,
    production_state VARCHAR(2) NOT NULL,
    planted_area DECIMAL(10,2) NOT NULL,
    approved_area DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Lotes
CREATE TABLE lots (
    id SERIAL PRIMARY KEY,
    lot_number VARCHAR(50) NOT NULL UNIQUE,
    lot_type VARCHAR(20) NOT NULL, -- INTERNAL_SALE, EXPORT
    cultivar_id INTEGER REFERENCES seeds(id),
    seed_type VARCHAR(30) NOT NULL, -- COATED, GRAPHITE, COATED_GRAPHITE, CONVENTIONAL
    category VARCHAR(10) NOT NULL, -- S1, S2
    bag_weight DECIMAL(10,2) NOT NULL, -- 1, 5, 10, 15, 20, 25, 600, 800, 1000
    balance DECIMAL(10,2) NOT NULL,
    analysis_bulletin VARCHAR(50),
    bulletin_date DATE,
    invoice_id INTEGER REFERENCES invoices(id),
    bag_type VARCHAR(30),
    validity_date DATE,
    seed_score INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Baixas de Lotes
CREATE TABLE lot_withdrawals (
    id SERIAL PRIMARY KEY,
    lot_id INTEGER REFERENCES lots(id),
    invoice_number VARCHAR(50),
    quantity DECIMAL(10,2) NOT NULL,
    withdrawal_date DATE NOT NULL,
    state VARCHAR(2) NOT NULL,
    client_id INTEGER REFERENCES clients(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Reservas de Lotes
CREATE TABLE lot_reservations (
    id SERIAL PRIMARY KEY,
    lot_id INTEGER REFERENCES lots(id),
    quantity DECIMAL(10,2) NOT NULL,
    reservation_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL, -- PENDING, CONFIRMED, CANCELLED
    client_id INTEGER REFERENCES clients(id),
    user_id INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para otimização
CREATE INDEX idx_seeds_cultivar ON seeds(cultivar);
CREATE INDEX idx_clients_name ON clients(name);
CREATE INDEX idx_lots_lot_number ON lots(lot_number);
CREATE INDEX idx_lots_cultivar_id ON lots(cultivar_id);
CREATE INDEX idx_lot_withdrawals_lot_id ON lot_withdrawals(lot_id);
CREATE INDEX idx_lot_reservations_lot_id ON lot_reservations(lot_id);
CREATE INDEX idx_lot_reservations_client_id ON lot_reservations(client_id);
