CREATE TABLE clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_type VARCHAR(20) NOT NULL,
    document VARCHAR(20) NOT NULL UNIQUE,
    legal_name VARCHAR(150) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(20),
    password_hash VARCHAR(255) NOT NULL,
    created_at DATETIME
);

CREATE TABLE accounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT NOT NULL,
    account_number VARCHAR(20) UNIQUE,
    branch VARCHAR(10),
    account_type VARCHAR(20),
    balance DECIMAL(15,2) DEFAULT 0,
    overdraft_limit DECIMAL(15,2) DEFAULT 0,
    status VARCHAR(20) DEFAULT 'active',
    CONSTRAINT fk_accounts_client FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE cards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    number_hash VARCHAR(255),
    printed_name VARCHAR(100),
    expiration_date DATE,
    cvv_hash VARCHAR(255),
    card_type VARCHAR(20),
    status VARCHAR(20) DEFAULT 'active',
    CONSTRAINT fk_cards_account FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE pix_keys (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    key_type VARCHAR(20) NOT NULL,
    key_value VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT fk_pix_keys_account FOREIGN KEY (account_id) REFERENCES accounts(id)
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    source_account_id INT,
    destination_account_id INT,
    amount DECIMAL(15,2),
    transaction_type VARCHAR(30),
    timestamp DATETIME,
    status VARCHAR(20) DEFAULT 'pending',
    CONSTRAINT fk_transactions_source FOREIGN KEY (source_account_id) REFERENCES accounts(id),
    CONSTRAINT fk_transactions_destination FOREIGN KEY (destination_account_id) REFERENCES accounts(id)
);

CREATE TABLE investment_products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    asset_type VARCHAR(30) NOT NULL,
    issuer VARCHAR(100),
    rate_index VARCHAR(50),
    maturity_date DATE,
    status VARCHAR(20) DEFAULT 'active'
);

CREATE TABLE client_wallets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT,
    product_id INT,
    quantity DECIMAL(15,2) DEFAULT 0,
    invested_amount DECIMAL(15,2) DEFAULT 0,
    CONSTRAINT fk_wallets_client FOREIGN KEY (client_id) REFERENCES clients(id),
    CONSTRAINT fk_wallets_product FOREIGN KEY (product_id) REFERENCES investment_products(id)
);

CREATE TABLE investment_orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    product_id INT,
    order_type VARCHAR(10),
    quantity DECIMAL(15,2),
    total_amount DECIMAL(15,2),
    request_date DATETIME,
    status VARCHAR(20) DEFAULT 'pending',
    CONSTRAINT fk_orders_account FOREIGN KEY (account_id) REFERENCES accounts(id),
    CONSTRAINT fk_orders_product FOREIGN KEY (product_id) REFERENCES investment_products(id)
);
