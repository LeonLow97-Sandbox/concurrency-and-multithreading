# Channels

- Channels are the *pipes* that connect concurrent goroutines. Can send values into channels from 1 goroutine and receive those values from another goroutine.
- **Creating a channel**: `make(chan <datatype>)`. Channels are typed by the values they convey.
- *Send* a value into a channel using the `channel <-` syntax.
- *Receive* a value from the channel using the `<-channel` syntax.

## Example Code

```go
func main() {
	messages := make(chan string)

	go func() {
		messages <- "Hello Jie Wei!"
	}()

	msg := <-messages 
	fmt.Println(msg)
}
```

1. Send "ping" to the messages channel from a new goroutine.
2. Receive the "ping" message we sent above and print it out.
3. When we run the program, the "ping" message is successfully passed from 1 goroutine to another via our channel.
4. By default, sends and receives block until both the sender and receiver are ready. This property allowed us to wait at the end of our program for the "ping" message (`<-messages`) without having to use any other synchronization.