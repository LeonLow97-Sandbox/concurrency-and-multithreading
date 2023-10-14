# Final Project

- A more "real-world" use of concurrency
- A fictitious service that allows people to buy a subscription.
- This section sets up the web application.

## Installations

- Using redis to store sessions.
- Using Docker for a dummy mail server

```
go get github.com/jackc/pgconn
go get github.com/jackc/pgx/v4
go get github.com/alexedwards/scs/v2
go get github.com/alexedwards/scs/redisstore
go get github.com/go-chi/chi/v5
```

- To access psotgresql inside the container.

```
docker exec -it 004-project-subscription_postgres_1 psql -U postgres -d concurrency
```