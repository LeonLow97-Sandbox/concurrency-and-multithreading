# Receive Only Channels and Send Only Channels

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
