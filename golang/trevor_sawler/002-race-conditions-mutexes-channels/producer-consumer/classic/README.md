# Classic Producer-Consumer problem

- [Stack Overflow Link](https://stackoverflow.com/questions/29634649/producer-consumer-in-golang)
- [Go Playground](https://go.dev/play/p/diYQGN-iwE)

# Solution

## `<-done`

- In the `main` goroutine thread, it encounters the `<-done` operation which is a block operation in Go.
    - The `main` goroutine is waiting until a value is sent on the `done` channel.
    - The purpose of this is to synchronize the termination of the main goroutine with the completion of certain tasks (in this case, the `produce` and `consume` goroutines).
- The `main` goroutine is waiting for 2 values to be received from the `done` channel.
    - One value is sent by the `produce` goroutine and the other is sent by the `consume` goroutine.
    - These values act as signals, indicating that both the producer and consumer have completed their tasks.

---
### `<-done` examples

- I tried putting 3 `<-done` and got the following error.
    - The deadlock occurs when the program is waiting for a value from a channel, but there is **no other goroutine to send a value on that channel**.
    - To resolve this issue, ensure that for every `<-done`, there is a corresponding goroutine sending a value on the `done` channel.

```
fatal error: all goroutines are asleep - deadlock!

goroutine 1 [chan receive]:
main.main()
```

- I tried putting only 1 `<-done` and the program exited quickly before the consumer got to execute to i = 3.
    - The `main` goroutine is not waiting long enough for the `consume` goroutine to finish its work before terminating.
    - Since goroutines execute concurrently, the `main` goroutine may proceed to exit before the `consume` goroutine completes.

```
Producer sending: 3
Consumer: 2
Before closing channel
Before passing true to done
After calling DONE
```

---