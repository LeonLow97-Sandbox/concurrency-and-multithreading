# Channels

- A means of alowing communication to and from a GoRoutine.
- Channels can be buffered, or unbuffered.
    - buffered means giving the channel a size. E.g., size of 10.
- Once you are done with a channel, you must close it to prevent a resource leak.
- Channels typically only accept a given type or interface.
    - E.g., strings, boolean, int, ...

## Receive Only Channels and Send Only Channels

- `<-chan` receive only channel
- `chan<-` send only channel

```go
func shout(ping <-chan string, pong chan<- string) {
	for {
		s := <-ping

		pong <- s
	}
}
```

## Error: `all goroutines are asleep - deadlock!`

- No goroutine is listening on that channel.

```
fatal error: all goroutines are asleep - deadlock!
```

## Use `ok` to tell if channel is closed or open

```go
s, ok := <-ping
if !ok {
	// tells if channel is closed or open
}
```

## `select` statement

- If there are multiple cases with the same condition, it chooses to execute one at random.
  - For example, cases 1 and 2 have the same condition of `<-channel1`, the program chooses one of the cases at random to execute.
- Use `default` to avoid deadlocks.

```go
    select {
    case s1 := <-channel1:
        fmt.Println("Case 1:", s1)
    case s2 := <-channel1:
        fmt.Println("Case 2:", s2)
    case s3 := <-channel2:
        fmt.Println("Case 3:", s3)
    case s4 := <-channel2:
        fmt.Println("Case 4:", s4)
    // default:
        // avoiding deadlock
    }
```

## Buffered Channels

- `make(chan int, <size>)` creating a buffered channel with a size. E.g., `make(chan int 10)` buffered channel with capacity of 10 so the channel can hold up to 10 elements without the need for a corresponding receiver to be ready.
- Buffered channels are useful if:
    - Knowing the number of GoRoutines:
        - If you launch 10 goroutines to perform some tasks concurrently, a buffered channel with a capacity of 10 can be used to avoid deadlocks. Each goroutine can send a message to the channel, and the main program can wait for all 10 messages to be received.
    - Limit the Number of Goroutines:
        - Once the buffer is full, any additional attempts to send data will block until there is space in the channel.
    - Limit the Amount of Work Queued Up:
        - If the channel is full, additional tasks may need to wait until there is space in the buffer.