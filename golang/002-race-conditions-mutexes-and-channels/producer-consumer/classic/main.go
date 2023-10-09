package main

import (
	"fmt"
	"time"
)

const bufferSize = 3

var done = make(chan bool)
var msgs = make(chan int, bufferSize)

func produce() {
	for i := 0; i < 10; i++ {
		select {
		case msgs <- i:
			fmt.Println("Producer sending:", i)
		default:
			fmt.Println("Buffer is full. Skipping value:", i)
		}
		time.Sleep(1 * time.Second)
	}

	fmt.Println("Before closing channel")
	close(msgs)
	fmt.Println("Before passing true to done")
	done <- true
}

func consume() {
	// for msg := range msgs {
	// 	fmt.Println("Consumer: ", msg)
	// 	time.Sleep(100 * time.Millisecond)
	// }
	for {
		msg, ok := <-msgs
		if !ok {
			// if the channel is closed, break out of the loop
			break
		}
		fmt.Println("Consumer:", msg)
		time.Sleep(3 * time.Second)
	}
	done <- true
}

func main() {
	go produce()
	go consume()

	// wait for both producer and consumer to finish
	<-done
	<-done

	fmt.Println("After calling DONE")
}
