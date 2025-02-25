INSERT INTO rate_providers (provider_code, created_at, modified_at, provider_name, description, priority, active,
                            default_multiplier)
VALUES ('PR1', now(), now(), 'Test provider', 'Description', 1, TRUE, 1)
ON CONFLICT DO NOTHING;