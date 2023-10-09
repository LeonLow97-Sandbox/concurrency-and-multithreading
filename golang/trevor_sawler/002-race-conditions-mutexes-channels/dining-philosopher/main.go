package main

import (
	"fmt"
	"sync"
	"time"
)

// The Dining Philosophers problem is well known in computer science classes.
// Five philosophers, numbered from 0 through 4, live in a house where the table
// is laid for them; each philosopher has their own place at the table.
// Their only difficulty - besides those of philosophy - is that the dish
// served is a very difficult kind of spaghetti which has to be eaten with
// 2 forks. There are 2 forks next to each plate, so that presents no difficulty.
// As a consequence, however, this means that no 2 neighbors may be eating simultaneously
// since there are 5 philosophers and 5 forks.

// This is a simple implementation of Dijkstra's solution to the "Dining
// Philosophers" dilemma.

// Philosopher is a struct which stores information about a philosopher
type Philosopher struct {
	name      string
	rightFork int // will be lock (mutex)
	leftFork  int // will be lock
}

// philosophers - list of all philosophers
var philosophers = []Philosopher{
	{name: "Alice", leftFork: 4, rightFork: 0},
	{name: "Bob", leftFork: 0, rightFork: 1},
	{name: "Charlie", leftFork: 1, rightFork: 2},
	{name: "Denise", leftFork: 2, rightFork: 3},
	{name: "Elle", leftFork: 3, rightFork: 4},
}

// define some variables
var hunger = 3 // how many times does a person eat?
var eatTime = 1 * time.Second
var thinkTime = 3 * time.Second
var sleepTime = 1 * time.Second

var orderMutex sync.Mutex // a mutex for the slice orderFinished
var orderFinished []string // the order in which philosophers finish dining and leave

func main() {
	// print out a welcome message
	fmt.Println("Dining Philosopher's Problem")
	fmt.Println("----------------------------")
	fmt.Println("The table is empty.")

	time.Sleep(sleepTime)

	// start the meal
	dine()

	// print out finished message
	fmt.Println("The table is empty.")
}

func dine() {
	// eatTime = 0 * time.Second
	// thinkTime = 0 * time.Second

	wg := &sync.WaitGroup{}
	wg.Add(len(philosophers))

	// ensure that philosopher is seated
	seated := &sync.WaitGroup{}
	seated.Add(len(philosophers))

	// forks is a map of all 5 forks
	var forks = make(map[int]*sync.Mutex)
	for i := 0; i < len(philosophers); i++ {
		forks[i] = &sync.Mutex{}
	}

	// start the meal
	for i := 0; i < len(philosophers); i++ {
		// fire off a goroutine for the current philosopher
		go diningProblem(philosophers[i], wg, forks, seated)
	}

	wg.Wait()
}

func diningProblem(philosopher Philosopher, wg *sync.WaitGroup, forks map[int]*sync.Mutex, seated *sync.WaitGroup) {
	defer wg.Done()

	// seat the philosopher at the table
	fmt.Printf("%s is seated at the table.\n", philosopher.name)
	seated.Done()

	seated.Wait()

	// eat 3 times
	for i := hunger; i > 0; i-- {
		// get a lock on both forks (always take the lower number fork first)
		// this is to avoid having both philosophers pick up the same fork when the program first runs
		if philosopher.leftFork > philosopher.rightFork {
			forks[philosopher.rightFork].Lock()
			fmt.Printf("\t%s takes the right fork.\n", philosopher.name)
			forks[philosopher.leftFork].Lock()
			fmt.Printf("\t%s takes the left fork.\n", philosopher.name)
		} else {
			forks[philosopher.leftFork].Lock()
			fmt.Printf("\t%s takes the right fork.\n", philosopher.name)
			forks[philosopher.rightFork].Lock()
			fmt.Printf("\t%s takes the left fork.\n", philosopher.name)
		}

		fmt.Printf("\t%s has both forks and is eating.\n", philosopher.name)
		time.Sleep(eatTime)

		fmt.Printf("\t%s is thinking.\n", philosopher.name)
		time.Sleep(thinkTime)

		forks[philosopher.leftFork].Unlock()
		forks[philosopher.rightFork].Unlock()

		fmt.Printf("\t%s put down the forks.\n", philosopher.name)
	}

	fmt.Println(philosopher.name, "is satisfied.")
	fmt.Println(philosopher.name, "left the table.")

	orderMutex.Lock()
	orderFinished = append(orderFinished, philosopher.name)
	orderMutex.Unlock()
}
