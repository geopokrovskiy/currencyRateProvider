INSERT into currencies (modified_at, code, iso_code, description, scale, symbol)
values (now(), 'RUB', 643, 'Russian Rouble', 1, '₽')
ON CONFLICT DO NOTHING;

INSERT into currencies (modified_at, code, iso_code, description, scale, symbol)
values (now(), 'USD', 840, 'United States Dollar', 1, '$')
ON CONFLICT DO NOTHING;

INSERT into currencies (modified_at, code, iso_code, description, scale, symbol)
values (now(), 'EUR', 978, 'Euro', 1, '€')
ON CONFLICT DO NOTHING;