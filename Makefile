.PHONY: qa-up qa-build qa-down qa-hard-reset qa-logs qa-ps

qa-up:
	docker compose -f docker-compose.qa.yml up -d

qa-build:
	docker compose -f docker-compose.qa.yml up -d --build

qa-down:
	docker compose -f docker-compose.qa.yml down

qa-hard-reset:
	docker compose -f docker-compose.qa.yml down -v

qa-logs:
	docker compose -f docker-compose.qa.yml logs -f api

qa-ps:
	docker compose -f docker-compose.qa.yml ps