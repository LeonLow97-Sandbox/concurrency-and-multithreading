package main

import (
	"fmt"
	"sync"
)

func printSomething(s string, wg *sync.WaitGroup) {
	// decrement the wg by 1 once function finish execution
	defer wg.Done()

	fmt.Println(s)
}

func main() {
	// declare a WaitGroup variable
	var wg sync.WaitGroup

	words := []string{
		"alpha",
		"beta",
		"delta",
		"gamma",
		"pi",
		"zeta",
		"eta",
		"theta",
		"epsilon",
	}

	// sets wg to the number of goroutines to wait for
	wg.Add(len(words))

	for i, w := range words {
		go printSomething(fmt.Sprintf("%d: %s", i, w), &wg)
	}

	// waits until wg becomes 0 (waits for all goroutines to finish)
	// This operation blocks until the internal counter becomes 0
	wg.Wait()

	wg.Add(1)

	printSomething("This is the second thing to be printed!", &wg)
}
