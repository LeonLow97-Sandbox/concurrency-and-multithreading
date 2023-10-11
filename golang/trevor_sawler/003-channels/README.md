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
