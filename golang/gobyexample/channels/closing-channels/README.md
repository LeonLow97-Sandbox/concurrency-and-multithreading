# Closing Channels

- _Closing_ a channel indicates that **no more values will be sent on it**. This can be useful to communicate completion to the channel's receivers.
- [GoByExample Closing Channels](https://gobyexample.com/closing-channels)

## Example Code

1. We use a `jobs` channel to communicate work to be done from the `main()` goroutine to a worker goroutine. When we have no more jobs for the worker, we will `close` the jobs channel
2. _Worker goroutine_. It repeatedly received from `jobs` channel with `j, more := <-jobs`. In this special 2-value form of receive, the `more` value will be `false` if `jobs` has been `closed` and all values in the channel have already been received. We use this to notify on `done` when we have worked all our jobs.
3. This sends 3 jobs to the worker over the `jobs` channel, then closes it.
4. Using `<-done`, we await the worker using the synchronization approach.

```go
func main() {
	jobs := make(chan int, 5)
	done := make(chan bool)

    // worker goroutine
	go func() {
		for {
			j, more := <-jobs
			if more {
				fmt.Println("received job", j)
			} else {
				fmt.Println("received all jobs")
				done <- true
				return
			}
		}
	}()

	for j := 1; j <= 3; j++ {
		jobs <- j
		fmt.Println("sent job", j)
	}
	close(jobs)
	fmt.Println("sent all jobs")

	<-done
}
```
