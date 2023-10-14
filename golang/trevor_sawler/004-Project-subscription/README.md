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
go get github.com/vanng822/go-premailer/premailer
go get github.com/xhit/go-simple-mail/v2
go get github.com/phpdave11/gofpdf
go get github.com/phpdave11/gofpdf/contrib/gofpdi
```

- To access psotgresql inside the container.

```
docker exec -it 004-project-subscription_postgres_1 psql -U postgres -d concurrency
```

- Application account email and password:
  - email address: admin@example.com
  - password: verysecret
- Go to `localhost:8025` to access MailHog.

## Running cleanup tasks in Golang with Concurrency

- `syscall.SIGINT` (Interrupt)
  - This signal is typically sent by pressing CTRL+C in the terminal where a program is running.
  - It is a graceful way to interrupt the execution of a program, allowing it to perform cleanup operations before terminating.
- `syscall.SIGTERM` (Termination)
  - This signal is a generic way to request the termination of a process.
  - It can be used to stop a running program, and like `SIGINT`, it allows the perform to perform cleanup operations before exiting.

```go
func main() {
    go listenForShutdown()
}

func (app *Config) listenForShutdown() {
	quit := make(chan os.Signal, 1)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
	app.shutdown()
	os.Exit(0)
}

func (app *Config) shutdown() {
	// perform any cleanup tasks
	app.InfoLog.Println("would run cleanup tasks...")

	// block until waitgroup is empty (ensure all background tasks are completed)
	app.Wait.Wait()

	app.InfoLog.Println("closing channels and shutting down application...")
}

```

## Sending Email Concurrently

- Sending email can slow things down
- Send it in the background, using 2 channels.
- Adding cleanup tasks on app shutdown

## Users & Plans

- Adding & validating user accounts
- Signed URLs in email
- Displaying the list of available subscriptions

## Adding Concurrency

- Generate an invoice
- Generate a user manual as a PDF

## Testing Concurrency

- Update our models to be more testable
- Test routes
- Test the renderer
- Test handlers