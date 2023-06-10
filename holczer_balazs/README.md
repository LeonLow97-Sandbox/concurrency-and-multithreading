# Process and Threads Introduction

- By default, programming languages are sequential which means they execute the commands 1 by 1.
- In a **single-threaded application**, the operations are executed one after the other.

```java
public static void main(String[] args) {
    initializeArrays();
    downloadData(); // if this is time consuming, it may freeze the application
    buildModel();
    makePredictions();
}
```

- The reason for **multithreading** is to separate multiple (time-consuming) tasks that might be subjected to **interference by the execution of other tasks**.
- With multithreading, can achieve better resource utilization and improve performance in the main.
- For example (stock market application), the **application downloads real-time data from the web** and constructs time-series models (ARMA or ARIMA) based on the data.
  - Solution: Use multithreading and a distinct thread for this time consuming operation and the application will not freeze.
- Multithreading is the ability of the CPU to execute multiple processes or threads concurrently.
- Both threads and processes are **independent sequences** of execution.

## Processes

- A process is an instance of a **program execution**.
- For example, when you open a software (Paint, Excel, etc) or web browser - these are distinct processes.
- The operating system (OS) assigns distinct **registers, stack memory and heap memory** to every single process.
- In Java, we can create processes with the `ProcessBuilder` class.
- Processes rely on the parent process (expensive).
- Process refers to an executing program, which would be running the Java application itself.

## Threads

- A thread is a **light-weight** process.
- It is a unit of execution _within a given process_, so a single process may contain several threads.
- Each **thread in a process shares the memory and resources**.
- Creating new threads require _fewer resources_ that creating new processes, does not affect the parent process.
- Concurrent Programming: ensure that different threads in the same process are using the same memory.
- The optimal number of threads is the number of processors (or processor cores) of the computer because this can make the application run parallel instead of a multithreaded application.

## Time-Slicing Algorithm

- Time-slicing means that processing time is shared among processes and threads.
- A single CPU has to deal with all the `k threads` in the application.
- Processing time for a single processor is shared among multiple processes or threads.
- The following diagram illustrates the time-slicing algorithm when the single processor handles _thread #1_ for a short amount of time and then _thread #2_, and so on...

<img src="./pics/time_slicing_algorithm.png" width="40%" />

- When there are multiple processors (or cores), then all the threads can be executed in a parallel manner. No need for time slicing algorithm.

<img src="./pics/parallel_execution.png" width="40%" />

## Benefits of Multithreading

- Can design more **responsive application**, can perform several operations concurrently.
- Can achieve **better resource utilization** (CPU utilization). By default, every Java application is single threaded. Can utilize **more CPU cores** with multiple threads.
- Can **improve performance** by utilizing CPU cores and run the threads in parallel.

## Downsides of Multithreading

- Threads are manipulating data that are located on the **same memory area** because they belong the same process - synchronization is not that straight-forward.
  - data may become inconsistent if multiple threads are manipulating the same data at the same time.
- Difficult to design and test/debug multithreaded applications.
- **Using multiple threads is expensive** - CPU has to save local data, application pointer, etc. of the current thread and has to load the other thread as well.
  - Switching between threads is a long operation. (expensive)

<img src="./pics/multithreading_disadvantage.png" width="40%" />

- It is **expensive to switch between multiple threads** - this is why an algorithm may become too slow with multiple threads.
- **RULE OF THUMB**: For small problems and applications, it is unnecessary to use multiple threads.

## Thread Lifecycle

1. `New` State
   - Every thread is in the new state until we call the `start()` method.
2. `Active` State
   - When we call the `start()` method on the given thread.
   - There are 2 sub-states:
     - `runnable`: ready to be executed but may not be currently executing because CPU is running another thread due to the time-slicing algorithm.
     - `running`: when the thread's turn comes, it enters the running state and executes the `run()` method.
3. `Blocked` / `Waiting` State
   - In this state, a thread is temporarily inactive and not consuming CPU time.
   - The thread may enter the blocked state or waiting state for various reasons, such as waiting for I/O operations, waiting for synchronization, or explicitly calling methods like `join()` or `sleep()`.
     - `join()`: waiting for another thread to be completed.
   - The _Thread Scheduler_ is responsible for resuming a blocked or waiting thread when the condition it was waiting for is satisfied.
4. `Terminated` State
   - When a thread has finished it's task.

## 3 Methods to Start Threads

1. Implementing the `Runner` interface

```java
class Runner2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner2: " + i);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Thread t2 = new Thread(new Runner2());
        t1.start();
    }
}
```

2. Extending the `Thread` class

```java
class Runner1 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner1: " + i);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Runner1();
        t1.start();
    }
}
```

3. Creating a inline thread

```java
    public static void main(String[] args) {
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Runner3: " + i);
                }
            }
        });
        t3.start();
    }
```

## Use `Runnable` interface or `Thread` classes?

- Usually using the `Runnable` interface approach is preferred.
    - If we extends `Thread` then we can't extend any other class (usually a huge disadvantage) because in Java, a given class can extends one class exclusively.
    - A class may implement more interfaces as well - so implementing the `Runnable` interface can do no harm in the software logic.

## `sleep()`

- `Thread.sleep()` pauses the execution of the current thread for a specified duration (in *milliseconds*).
- Introduces delays or pauses in the program's execution.
- `Thread.sleep()` method can be used for controlling the timing of certain operations or introducing delays between actions.
- Throws an `InterruptedException` if the thread is interrupted by another thread.

## `join()`

- `Thread.join()` allows one thread to wait for the completion of another thread.
- When a thread invokes the `join()` method on another thread, it waits for that thread to finish its execution before continuing its own execution.
- In the example below:
    - By calling `t1.join()`, the main thread waits for `t1` to finish execution before continuing. Same for `t2`.
    - The `join()` calls ensure that the output from the main thread ("Finished with Threads...") is only printed once both `t1` and `t2` have finished their tasks.

```java
t1.start();
t2.start();

try {
    t1.join(); // Main thread waits for thread1 to complete
    t2.join(); // Main thread waits for thread2 to complete
} catch (InterruptedException e) {
    e.printStackTrace();
}

System.out.println("Finished with Threads...");
```