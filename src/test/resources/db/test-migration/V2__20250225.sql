INSERT into currencies (modified_at, code, iso_code, description, scale, symbol)
values (now(), 'CS1', 643, 'Currency Source 1', 1, 'C1')
ON CONFLICT DO NOTHING;

INSERT into currencies (modified_at, code, iso_code, description, scale, symbol)
values (now(), 'CD1', 840, 'Currency Destination 1', 1, 'D1')
ON CONFLICT DO NOTHING;
