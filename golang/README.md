# Concurrency in Go

- Add the word `go` before the function to make it run concurrently.
- Don't always just lock functions, instead we can make use of go channels to pass data around between the GoRoutines.

## GoRoutines

- Running things in the background, or concurrently.
- Can have multiple GoRoutines at the same time.
- GoRoutines are very lightweight threads and they run quickly. A group of GoRoutines is managed by the Go Scheduler which decides what go routines runs and how much execution time it get, etc.. in the background.
- The `main` function itself is a GoRoutine

```go
package main

func main() {
    fmt.Println("Hello World!")
}
```

## `main` goroutine execution

- In the example below, the `main` goroutine is not waiting for the other goroutine to finish before it exits.
- When the `main` function finishes, the program exists, and any remaining goroutines are terminated. This is likely happening before the goroutine scheduled with `go printSomething("This is the first thing to be printed!")` gets a chance to execute.
- Can use `sync.WaitGroup` to solve the issue.

```go
func printSomething(s string) {
	fmt.Println(s)
}

func main() {
	go printSomething("This is the first thing to be printed!")
	printSomething("This is the second thing to be printed!")
}

// Output: 
This is the second thing to be printed!
```

## `WaitGroup`

- `WaitGroup` is a synchronization primitive in Go used to wait for a collection of goroutines to finish their execution before proceeding further.
- If the `Done` method is called more times than the `Add` method, resulting in a negative counter, it will lead to a runtime panic.