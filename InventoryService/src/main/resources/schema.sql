CREATE TABLE IF NOT EXISTS inventory (
    id BIGSERIAL PRIMARY KEY,
    sku_code VARCHAR(255) NOT NULL UNIQUE,
    available_quantity NUMERIC(19, 2) NOT NULL,
    reserved_quantity NUMERIC(19, 2) NOT NULL DEFAULT 0,
    unit VARCHAR(20) NOT NULL,
    create_at TIMESTAMP,
    update_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stock_reservations (
    id UUID PRIMARY KEY,
    order_id VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stock_reservation_items (
    id BIGSERIAL PRIMARY KEY,
    reservation_id UUID NOT NULL REFERENCES stock_reservations(id),
    sku_code VARCHAR(255) NOT NULL,
    quantity NUMERIC(19, 2) NOT NULL CHECK (quantity > 0),
    unit VARCHAR(20) NOT NULL
);