INSERT INTO clients (client_type, document, legal_name, email, phone, password_hash, created_at) VALUES
('PF', '12345678901', 'João Silva', 'joao@email.com', '11911111111', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW()),
('PJ', '11222333000181', 'Empresa ABC Ltda', 'contato@empresaabc.com', '11333333333', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW()),
('PF', '98765432100', 'Maria Souza', 'maria@email.com', '21922222222', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', NOW());

INSERT INTO accounts (client_id, account_number, branch, account_type, balance, overdraft_limit, status) VALUES
(1, '10001', '0001', 'CHECKING', 5000.00, 1000.00, 'active'),
(1, '10002', '0001', 'SAVINGS', 15000.00, 0.00, 'active'),
(2, '20001', '0002', 'CHECKING', 50000.00, 5000.00, 'active'),
(3, '30001', '0001', 'CHECKING', 2500.00, 500.00, 'active');

INSERT INTO cards (account_id, number_hash, printed_name, expiration_date, cvv_hash, card_type, status) VALUES
(1, 'hash_1234', 'JOAO SILVA', '2028-12-01', 'cvv_hash_123', 'PHYSICAL', 'active'),
(1, 'hash_5678', 'JOAO SILVA', '2027-06-01', 'cvv_hash_456', 'DIGITAL', 'active'),
(3, 'hash_9012', 'EMPRESA ABC', '2029-03-01', 'cvv_hash_789', 'PHYSICAL', 'active');

INSERT INTO pix_keys (account_id, key_type, key_value) VALUES
(1, 'CPF', '12345678901'),
(1, 'EMAIL', 'joao@email.com'),
(2, 'RANDOM', '7f3a8c2b-1e5d-4a0f-9c6b-3d2e1f8a7b4c'),
(3, 'CNPJ', '11222333000181'),
(4, 'CPF', '98765432100'),
(4, 'PHONE', '21922222222');

INSERT INTO transactions (source_account_id, destination_account_id, amount, transaction_type, timestamp, status) VALUES
(1, 4, 200.00, 'PIX', '2026-06-28 10:30:00', 'completed'),
(4, 2, 50.00, 'PIX', '2026-06-28 14:15:00', 'completed'),
(1, NULL, 150.00, 'PAYMENT', '2026-06-29 09:00:00', 'completed'),
(3, 1, 5000.00, 'TRANSFER', '2026-06-29 16:45:00', 'completed'),
(1, 4, 100.00, 'PIX', '2026-06-30 08:00:00', 'completed');

INSERT INTO investment_products (product_name, asset_type, issuer, rate_index, maturity_date, status) VALUES
('Tesouro Selic 2028', 'TREASURY', 'Tesouro Nacional', 'SELIC', '2028-03-01', 'active'),
('CDB Banco X 120%', 'CDB', 'Banco X', '120% CDI', '2027-12-15', 'active'),
('Fundo Imobiliário ABC', 'FII', 'ABC Gestão', NULL, NULL, 'active'),
('LCI 90% CDB', 'LCI', 'Banco Y', '90% CDI', '2027-06-30', 'active');

INSERT INTO client_wallets (client_id, product_id, quantity, invested_amount) VALUES
(1, 1, 10.00, 10000.00),
(1, 2, 5.00, 5000.00),
(2, 1, 50.00, 50000.00),
(2, 3, 20.00, 20000.00);

INSERT INTO investment_orders (account_id, product_id, order_type, quantity, total_amount, request_date, status) VALUES
(1, 1, 'BUY', 10.00, 10000.00, '2026-06-25 10:00:00', 'completed'),
(1, 2, 'BUY', 5.00, 5000.00, '2026-06-26 14:30:00', 'completed'),
(2, 1, 'BUY', 50.00, 50000.00, '2026-06-27 09:15:00', 'completed');
