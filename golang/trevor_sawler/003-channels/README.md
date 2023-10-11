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
