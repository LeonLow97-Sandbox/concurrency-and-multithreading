package main

import "fmt"

func main() {
	messages := make(chan string)

	go func() {
		messages <- "Hello Jie Wei!"
	}()

	msg := <-messages // until value is received from the channel `messages` then program can proceed
	fmt.Println(msg)
}
