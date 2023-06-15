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
- With multithreading, can achieve **better resource utilization and improve performance** in the main.
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
- Creating new threads require _fewer resources_ than creating new processes, does not affect the parent process.
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

1. Implementing the `Runner` interface (**Recommended Method**)

```java
class Runner1 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Runner1: " + i);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner1());
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

- `Thread.sleep()` pauses the execution of the current thread for a specified duration (in _milliseconds_).
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

# Daemon and Worker Threads

- A thread in Java can be **daemon thread** or a standard **worker thread**.
- When a Java program starts, then one thread begins running immediately, i.e., `main` thread. It starts the main method.
- Can create child threads from the `main` thread. The main thread is the last thread to finish execution because it performs various shutdown operations.
- **daemon threads** are intended as helper threads, e.g., for garbage collection.

<img src="./pics/daemon_vs_worker_threads.png" width="40%" />

- Daemon threads are _low priority_ threads that run in the background to perform tasks such as garbage collection.
- Usually we create daemon threads for I/O operations or services (smartphone services such as NFC or Bluetooth communication).
  - Can create a daemon thread for a smartphone application to look for smart-watches to pair with.
- Daemon threads are **terminated by the JVM when all other worker threads are terminated** (finish execution).
  - They do not prevent the JVM from exiting when all non-daemon threads have finished executing.
  - When all worker threads have completed their execution, the JVM terminates any remaining daemon threads without allowing them to finish their work.
  - Recommended to use daemon threads for tasks that are safe to be terminated abruptly and do not require precise completion.
- _Main difference_: worker threads are not terminated while daemon threads are interrupted by the JVM.

```java
// Setting a thread as a Daemon Thread
t1.setDaemon(true);
```

## Thread Priority

<img src="./pics/thread_priority.png" width="60%" />

- Time-slicing algorithm is handled by Thread Scheduler.
- Can assign a priority value (1-10) to every Thread
  - default priority value is 5
  - `MIN_PRIORITY`: 1
  - `MAX_PRIORITY`: 10
- Threads with the _same priority_ value (default priority is 5) are executed in a **FIFS** (first-in-first-served) manner - the thread scheduler store the threads in a **queue**.
- Higher priority threads are executed before lower priority threads but it depends on the underlying OS (thread starvation is avoided).
  - E.g., main thread with default priority of 5 could be executed before threads with priority of 10.

```java
// Thread with priority 10
Thread t = new Thread(new WorkerThread());

t.setPriority(Thread.MAX_PRIORITY);
t.start();
```

# Memory Management of Threads

<img src="./pics/heap_and_stack_memory.png" width="40%" />

- Threads (of the same process) run in a shared memory space, while processes run in separate memory spaces.

| Stack Memory                                              | Heap Memory              |
| --------------------------------------------------------- | ------------------------ |
| Stores local variables, method arguments and method calls | Stores object references |
| Fast                                                      | Slow                     |
| Smaller memory                                            | Larger memory            |

- Every thread has its own stack memory but all threads share the heap memory (shared memory space). Thus, synchronization is needed.
- The main purpose of **synchronization** is the sharing of resources without interference using mutual exclusion.
  - So that threads cannot interfere the sharing of resources in Heap Memory.

## Synchronization

- Located in `~/_004_thread_memory_synchronization`

```java
public void increment() {
    counter++;
}
```

- In the example below,
  - Reading the number from memory
  - Incrementing the value
  - Writing the number to memory
  - Return with the variable
- These operations seems to be atomic in the sense that requires only a single operation but this is not the case.
  - It takes some time to finish with the _increment operation_.
  - During this procedure, another thread may call this method as well with the original counter value.
  - If both threads call this method at the same time, they share the same variable `counter` memory.
    - counter = 0
    - Thread 1: counter = counter + 1 = 1
    - Thread 2: counter = counter + 1 = 1
    - Final value counter = 1 _(incorrect, should be 2)_

```java
public static synchronized void increment() {
    counter++;
}
```

- Using the `synchronized` keyword helps to ensure that this method is only executed by a single thread at a given time, provides mutual exclusion
- Provides **mutual exclusion**.
- Ensures **thread safety** when multiple threads access shared data or critical sections of code concurrently.
- If the methods are accessing different variables that are not shared among multiple threads, there is no risk of data inconsistency or race conditions, and synchronization is not required.
- **Race Condition**: occurs when 2 or more threads access shared data concurrently, leading to unpredictable and incorrect behavior due to the uncontrolled interleaving of their operations.

## Intrinsic Lock (Monitor Lock)

```java
public synchronized void increment() {
    counter++;
}
```

- Every object in Java has an intrinsic lock.
- "A thread that needs exclusive and consistent access to an object's fields has to acquire the object's intrinsic lock before accessing them, and then release the intrinsic lock when it's done with them."
- Due to the monitor lock, **no 2 threads can execute the same `synchronized` method at the same time**.
- Only a single thread can acquire the intrinsic lock of the class.
- When the `synchronized` keyword is used, the thread acquires the intrinsic lock of the application.
- Removing the `synchronized` keyword allows the method to be called without acquiring the intrinsic lock. (faster)
- When a method is declared as `synchronized`, it means that only 1 thread can execute that method at a time.
  - A thread owns the intrinsic lock between the time is has acquired the lock and released the lock.
  - If a thread owns an intrinsic lock, no other thread can acquire the same lock.
- If multiple threads attempt to execute a `synchronized` method simultaneously, the additional threads will be blocked until the lock is released by the executing thread.
- If multiple `synchronized` methods are present in an object, different threads may need to wait for each other to release the lock associated with that object before they can proceed. This can lead to potential performance issues and contention.

### Object level locking (Intrinsic Lock)

- This is called object level locking because we get the monitor lock (intrinsic lock) associated with the object itself.

```java
public synchronized void increment() {
    counter++;
}

// block level lock
public void increment() {
    synchronized(this) {
        counter++;
    }
}
```

### Class level locking (Intrinsic Lock) - `static` keyword

```java
public static synchronized void increment() {
    counter++;
}

// block level lock
public static void increment() {
    synchronized(ClassName.class) {
        counter++;
    }
}
```

- This is called class level locking because we get the monitor lock (intrinsic lock) associated with the class.
- Block level locks are generally recommended as they do not configure other execution in the method to be synchronized. So, only synchronized specific executions in the method.

## Locking with Custom Objects

```java
private static final Object lock1 = new Object();
private static final Object lock2 = new Object();

public static void increment1() {
    synchronized (lock1) {
        counter1++;
    }
}

public static void increment2() {
    synchronized (lock2) {
        counter2++;
    }
}
```

- Both methods are using independent locks.

## Thread Communication (`wait` and `notify`)

- Threads that are locking on the same intrinsic lock (monitor) can release the lock until the other thread calls `notify`.
- `wait()` and `notify()` methods can be used and called from _synchronized_ methods or blocks exclusively.

<img src="./pics/wait_and_notify.png" width="80%" />

- **Deadlock**: If there are 2 threads using the same intrinsic lock, the first thread goes into a waiting state with `wait()` but the second thread doesn't execute `notify()`, then thread 1 will be in the waiting state infinitely.

```java
class Process {

    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running the produce method...");
            wait();
            System.out.println("Again in the producer method...");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000); // to ensure that the produce thread runs first

        synchronized (this) {
            System.out.println("Consume method is executed...");
            notify();
            Thread.sleep(5000);
        }
    }

}
```

- In the example above, the behavior of `wait()` and `notify()` is such that when a thread calls `notify()`, it only signals to another waiting thread that it can wake up and attempt to reacquire the lock.
- However, the actual reacquisition of the lock by the waiting thread is not immediate.
- The waiting thread will only be able to proceed and acquire the lock when the notifying thread releases the lock by exiting the **synchronized** block.
  1. `consume()` method prints "Consume method is executed..."
  2. `consume()` method sleeps for 5 seconds (while still holding the lock as the synchronized block has not yet been exited).
  3. After 5 seconds, the `consume()` method releases the lock by exiting the synchronized block.
  4. `produce()` method acquires the lock and continues execution, printing "Again in the producer method...".

## Difference between `wait()` and `sleep()`

| `wait()`                                                           | `sleep()`                                          |
| ------------------------------------------------------------------ | -------------------------------------------------- |
| Call `wait` on the Object.                                         | Call `sleep` on the Thread itself.                 |
| `wait` can be the interrupted (need `InterruptedException`)        | Sleep cannot be interrupted.                       |
| `wait` must happen in a synchronized block.                        | Sleep does not have to be in a synchronized block. |
| `wait` releases the lock on the object that `wait()` is called on. | `sleep` does not release the locks it hold.        |

## Releasing the Intrinsic Lock

- 2 ways to release the intrinsic lock with `wait()` and `notify()`
  - When a thread calls the `wait()` method, it releases the intrinsic lock (AKA monitor lock) that it holds, allowing other threads to acquire it.
  - When a thread calls the `notify()` method, it does not directly release the lock to the waiting thread, the thread continues to execute until it releases the lock explicitly by **exiting the synchronized block/method**.
- `notify()` is used to wake up one of the waiting threads that are waiting on the same monitor, allowing it to try to acquire the lock.
- The thread that calls `notify()` does not directly release the lock held by the waiting thread, it releases the lock itself at a later point.

<img src="./pics/sequence_of_events_for_wait_and_notify.png" width="60%" />

## Reentrant Locks

- Reentrant locks provide the ability for a thread to acquire the same lock multiple times without deadlocking itself, as long as it releases the lock the same number of times.
- It has the same behavior as the "synchronized approach" with some additional features.

```java
private Lock lock = new ReentrantLock(); // works too as Lock is an interface and ReentrantLock implements Lock
private ReentrantLock lock = new ReentrantLock();
lock.lock();
lock.unlock();
```

- `new ReentrantLock(boolean fairness)`
  - If the `fairness` parameter is set to be TRUE then the longest waiting thread will get the lock. (by default).
  - If the `fairness` parameter is set to FALSE, then there is no access order.
- **IMPORTANT**: a good approach is to use `try-catch-finally` blocks when doing the critical section and call `unlock()` in the finally block.
- A thread cannot acquire a lock owned by another thread but a given thread can acquire a lock that it owns.
- Allowing a thread to acquire the same lock more than once is called _re-entrant synchronization_.
- Example:
  - Consider recursive method calls.
  - If a given thread calls a recursive and synchronized method several times, then it is fine (note that in this case the same thread "enters" the synchronized block several times).
  - There will be no deadlock because of re-entrant synchronization.

## Locks and Synchronized Blocks

| Lock (Reentrant)                                                                                               | Synchronized Blocks                                |
| -------------------------------------------------------------------------------------------------------------- | -------------------------------------------------- |
| Can make a lock fair (**prevent thread starvation**)                                                           | Unfair by default                                  |
| Can check whether the given lock is held or not with `lock.isHeldByCurrentThread()` with `Reentrant` interface | Cannot check the lock status directly              |
| Can get the **list of waiting threads** for the given lock                                                     | Cannot directly access the list of waiting threads |
| Need `try-catch-finally` block                                                                                 | Don't need `try-catch-finally` block               |

## `volatile` Keyword

- `volatile` keyword in Java is used to indicate that a variable may be modified by multiple threads and it provides a lightweight synchronization mechanism for variables that are shared among threads.
- Used in scenarios where you have a variable that is accessed by multiple threads, and you want to ensure that changes made by 1 thread are immediately visible to other threads.
- Ensure that any read or write operation on the variable is directly performed on the main memory, rather than on a local cache specific to each thread.
- Guarantees visibility of changes made by 1 thread to other threads, helping to avoid certain subtle concurrency issues.

<img src="./pics/memory_of_threads.png" width="60%" />

- Every read of a `volatile` variable will be read from the **RAM** so from the main memory (not from cache).
  - usually variables are cached for performance reasons
  - caches are faster. Do not use `volatile` keyword is not necessary (it prevents instruction reordering which is a performance boost technique).
- `volatile` guarantees pushing to RAM, but pushing to RAM can happen even without volatile.

## When to use the `volatile` keyword?

1. Flag variables:
   - `volatile` is commonly used for boolean flags that control the execution of threads.
   - For example, a flag to stop a thread's execution when set to `true`.
   - Using `volatile` ensures that the flag's value is always visible to other threads.
2. Status variables:
   - If a variable represents the status of a shared resource and is updated by 1 thread, but read by multiple threads, marking it as `volatile` ensures that all threads see the most up-to-date status.
3. Performance considerations:
   - In some situations, using `volatile` can offer better performance compared to other synchronization mechanisms, especially when the shared variable is frequently read but rarely written.

## Stopping a Thread

- Need to use `volatile` keyword to ensure that the termination status of a thread is shared in the main memory (RAM).

```java
// Create a volatile variable to terminate the thread
private volatile boolean terminated;

// Create a setter
public void setTerminated(boolean terminated) {
    this.terminated = terminated;
}
```

## Deadlock

- Deadlock occurs when 2 or more threads wait forever for a lock or resource held by another thread.
- Deadlock is a state where 2 or more entities are stuck and unable to proceed because they are waiting for each other to release resources.

---

- Example of Deadlock in User Management Database table
  - Process 1 wants to update User A's profile and needs to acquire a lock on User A's row.
  - Process 2 wants to update User B's profile and needs to acquire a lock on User B's row.
  - If Process 1 already holds a lock on User A's row and also wants to update User B's row, while Process 2 already holds a lock on User B's row and wants to update User A's row, a **circular dependency** is created.
- Both processes are holding a lock on 1 row and are waiting for another row, resulting in a circular wait.
- Leads to a deadlock where both processes are stuck and cannot proceed further.

---

## Livelock

- Livelock occurs in concurrent systems, where 2 or more processes become stuck in a repetitive cycle of actions, unable to progress.
- Similar to deadlock, but in a livelock, the processes are not blocked or waiting for resources; they are continuously active, yet unable to make progress towards their goals.
- Often arise when multiple processes try to respond to a certain condition or event,but their actions end up interfering with each other, causing a perpetual loop.
- Unlike deadlocks, livelocks do not result in a complete system half, but it leads to an _inefficient utilization of resources_ and can severely _impact system performance_.
- Resolving a livelock typically involves careful analysis and modification of the affected processes' logic to break the repetitive cycle and enable progress.

---

- Practical Example to understand Livelock
  - 2 people trying to pass each other in a narrow hallway.
  - If both individuals move in the same direction at the same time to let the other person pass, hey end up blocking each other's path.
  - Then, they both step back to allow the other person to pass, which results in another collision.
  - The cycle repeats indefinitely, and neither person can make forward progress.

---

## How to handle deadlocks and livelocks?

- Ensure that **each thread acquires the locks in the same order** to avoid any _cyclic dependency_ in lock acquisition.
- Ensure that a thread does not block infinitely if it is unable to acquire a lock.
  - use `Lock` interface `tryLock()` method.
- Livelock can be handled with the methods above and some randomness
  - threads retry acquiring the locks at random intervals.

## Atomic Variables

- Provide atomicity guarantees for certain operations.
- Ensure that specific operations on the variable are executed atomically, meaning they are indivisible (cannot be interfered by other threads).
- Ensures thread-safety and avoids race conditions.
- Atomic variables should be used when you need to perform single, thread-safe operations on a single variable, such as incrementing, decrementing, or setting its value.
- Useful in scenarios with high contention, where multiple threads may simultaneously read and write to the same variable.

## When to use atomic variables over the `synchronized` keyword?

1. Simplicity
   - Atomic variables are simpler to use and understand, especially for simple operations on a single variable.
2. Performance
   - Atomic variables can offer better performance in high-contention scenarios compared to synchronized blocks as they have _lower overhead_.
   - `synchronized` keyword introduces higher overhead due to acquiring and releasing locks, which can impact performance, especially in scenarios with low contention.
3. Granularity
   - Atomic variables allow fine-grained control over the synchronization of specific operations, while `synchronized` keyword applies to entire blocks or methods.

## Classes for Atomic Variables

- In Java, the `java.util.concurrent.atomic` package provides several classes for atomic variables.

1. `AtomicInteger`: this class provides atomic operations for `int` values. Supports atomic increments, decrements, additions and comparisons.

```java
import java.util.concurrent.atomic.AtomicInteger;

AtomicInteger atomicInteger = new AtomicInteger(0);
atomicInteger.incrementAndGet(); // Atomically increments the value by 1 and returns the new value
atomicInteger.getAndAdd(5); // Atomically adds 5 to the value and returns the previous value
```

2. `AtomicLong`: Similar to `AtomicInteger`, this class provides atomic operations for `long` values.

```java
import java.util.concurrent.atomic.AtomicLong;

AtomicLong atomicLong = new AtomicLong(0L);
atomicLong.decrementAndGet(); // Atomically decrements the value by 1 and returns the new value
atomicLong.compareAndSet(10L, 15L); // Atomically compares the value with 10L and sets it to 15L if they match
```

3. `AtomicBoolean`: This class provides atomic operations for `boolean` values.

```java
import java.util.concurrent.atomic.AtomicBoolean;

AtomicBoolean atomicBoolean = new AtomicBoolean(true);
atomicBoolean.getAndSet(false); // Atomically sets the value to false and returns the previous value
```

4. `AtomicReference`: This class provides atomic operations for reference types.

```java
import java.util.concurrent.atomic.AtomicReference;

AtomicReference<String> atomicReference = new AtomicReference<>("Hello");
atomicReference.compareAndSet("Hello", "Hi"); // Atomically compares the value with "Hello" and sets it to "Hi" if they match
```

## Semaphores

- **Simple variables** (or abstract data types) that are used for controlling access to a common resource. Important concept in operating systems.
- "It is a record of how many units of a particular resource are available. We have to wait until a unit of the resource becomes available again".
- Maintains a set of permits that threads must acquire before accessing the shared resource.
- Semaphores can be used to limit the number of concurrent threads accessing the resource or to coordinate access in a specific order.
- Uses the `Semaphore` class to provide semaphore functionality.
- Semaphores have an internal counter representing the number of available permits.
- Threads can acquire permits using the `acquire` method and release them using the `release` method.
- **Counting Semaphores**: allows an arbitrary resource count.
- **Binary Semaphores**: semaphores that are restricted to the values 0 and 1.

---

- Practical example of Semaphores
  - Suppose a library has 10 identical study rooms (each room can be used by a single student at a time).
  - Students must request a study room from the front desk.
  - If no rooms are free, students have to wait for rooms to be available again so until someone relinquishes (leaves) a given study room.
  - When a student finished using the room, the student must return to the front desk and indicate that 1 room has become free.
  - the front desk is the Semaphore in this case.

---

- Semaphores track only **how many resources are free** - it does not keep track of which of the resources are free.
- The semaphore count may serve as a useful **trigger** for a number of different actions (web servers).
- **Producer-Consumer** problem can be solved and implemented with the help of semaphores (Dijkstra's approach).

## Mutex (Mutual Exclusive Object)

- Mutex is a synchronization mechanism used to protect shared resources from concurrent access by multiple threads.
- It allows only 1 thread to acquire the mutex and access the shared resource at a time, ensuring exclusive access and preventing data races or conflicts.
- The `ReentrantLock` class implements mutex functionality.
- Mutexes provide more flexibility than Java's intrinsic locks (`synchronized` keyword) by allowing explicit control over lock acquisition and release.
- Mutex is very similar to a binary semaphore: while binary semaphore can be used as mutex, a mutex is a more specific use-case.
- `Lock` is designed to enforce a mutual exclusion concurrency control policy.

## Semaphore vs Mutex

| Semaphore                                                                                                                          | Mutex                                                                                                                            |
| ---------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------- |
| **Signalling Mechanism**                                                                                                           | **Locking Mechanism**                                                                                                            |
| Threads and processes perform `acquire()` and `release()` operations to indicate whether that are acquiring or releasing the resource. | Threads or processes have to acquire the lock on mutex object if it wants to acquire the resource.                               |
| Allows multiple program threads to access the **finite instance of resources** (not just a single resource)                        | Allows multiple program threads to access a **single shared resource** but one at a time.                                        |
| the process of thread **blocks** itself if no resource is free till the count of semaphore become greater than 0                   | if the lock is already acquired by another thread or process then the thread will **wait** until the mutex object gets unlocked. |

# Creating Threads with Executors

## Executors

- Executors provide a higher-level interface for executing tasks in a multithreaded environment.
- They abstract away the low-level details of thread creation, management and scheduling.
- Executors **manage a pool of worker threads** that can be **reused** for executing tasks. Using thread pool makes multithreading **efficient**.
- They improve performance and resource management by reusing threads instead of creating new threads for every task.
  - Creating a thread is expensive because Java needs to allocate stack memory, cache, CPU associated with every single thread.

---

- Why use thread pools and the Executor Framework?
  - Java provides its own multithreading framework: `Executor Framework`.
  - It will handle everything: schedule and execute the submitted tasks.
  - Adding a new thread for each process leads to the creation of a large number of threads.
    - These threads need memory + CPU will spend too much time switching context when the threads are swapped.
  - Thread pools can reuse threads in an efficient manner by keeping the threads alive and reusing them (thread pools are usually **queues**).

---

#### Types of Executors:

1. `SingleThreadExecutor`
   - This executor has a single thread so we can execute processes in a sequential manner. Every process is executed by a new thread.
2. `FixedThreadPool(n)`
   - This is how we can create a thread pool with `n` threads. Usually `n` is the number of cores in the CPU.
   - Maintains a fixed number of threads that are always available for executing tasks.
   - If a thread finished executing a task, the thread pool will assign another task to that thread.
   - If there are more tasks than n, then these tasks are stored with a `LinkedBlockingQueue` data structure.
3. `CachedThreadPool`
   - Dynamically create and reuse threads based on the demand for tasks.
   - The number of threads is not bounded.
   - If all threads are busy executing some tasks and a new task comes, the pool will create and add a new thread to the executor.
   - If a thread remains idle for **60 seconds**, the thread is removed.
   - It is used for short parallel tasks.
4. `ScheduledExecutor`
   - Used for scheduling tasks to run at a specific time or with a fixed delay between executors.

```java
ExecutorService executor = Executors.newSingleThreadExecutor();
ExecutorService executor = Executors.newFixedThreadPool(3);

ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
executor.scheduleAtFixedRate(new StockMarketUpdater(), 1000, 5000, TimeUnit.MILLISECONDS); // StockMarketUpdater implements Runnable interface
```

---

## `Runnable` and `Callable` Interfaces

|              | `Runnable`                             | `Callable`                                                      |
| ------------ | -------------------------------------- | --------------------------------------------------------------- |
| Purpose      | Represents a task that can be executed | Represents a task that can be executed and **returns a result** |
| Method       | `void run()`                           | `V call()`                                                      |
| Return type  | None(void)                             | Can return a value of type V                                    |
| Usage        | Used for fire-and-forget tasks         | Used when a result is needed                                    |

- `Runnable` is used when the execution has started but the calling thread doesn't need to wait for a result.
- `Callable` is used when a result is needed, and it provides a way to retrieve the result of the _computation_.
- `Callable` interface is typically used with the `ExecutorService`, which represents a thread pool that can execute tasks asynchronously.
- `Callable` tasks are useful when you need to perform a computation in a separate thread and retrieve the result asynchronously, allowing the calling thread to continue with other operations or wait for the result to be available.

---

- `Callable` is a function interface with a single method `call()`, which returns a value of type `V` (the result of the computation).
- When a `Callable` task is submitted to an `ExecutorService` using the `submit()` method, it returns a `Future` object that represents the result of the computation.
  - `executorService.submit()` can handle `Runnable` interfaces as well as `Callable` interfaces.
  - `executorService.submit()` can handle a `Future<T>` return value and we can get the `T` value with `get()` on the future object.
- The `Future` object provides methods to check if the computation is complete, retrieve the result using the `get()` method (which blocks until the result is available), and cancel the task if needed.

---

- To submit tasks that implement the `Runnable` interface, `executorService.execute()` is used.

---

# Collection Framework

<img src="./pics/java-collections-framework.png" width="50%" style="background-color:white;padding:10px;border:3px solid black" />

- Might not be thread safe (not synchronized).

## Collections Synchronization

- Java provides the `Collections` class, which includes methods for synchronizing collections.
- Without synchronization, concurrent access to a collection can result in data inconsistencies, race conditions, and other concurrency related issues.
- Synchronizing a collection ensures that only 1 thread can modify the collection at a time, preventing concurrent modifications and maintaining data integrity.
- Synchronized collections use an _internal lock_ mechanism to ensure thread-safe access. This lock allows only 1 thread to perform operations on the collection at a time, while other threads wait for their turn.

```java
// For synchronizing ArrayList
List<Integer> nums = Collections.synchronizedList(new ArrayList<>());
```

## Latch

- Synchronization mechanism that allows 1 or more threads to wait until a set of operations being performed by other threads completes.
- The `CountDownLatch` class is initialized with a count, which represents the number of times the `countDown()` method must be invoked before the waiting threads are released.
- Threads that need to wait for the latch to be released call the `await()` method on the latch. If the count is greater than 0, the calling thread will be blocked until the count reaches 0.
- Once all the operations being performed by other threads are completed, the latch count is decrementing using the `countDown()` method. This allows waiting threads to proceed.
- The `CountDownLatch` is a **one-time mechanism**. Once the count reaches 0, it cannot be reset.
- Latches are commonly used to coordinate the execution of multiple threads, where 1 or more threads need to wait for others to complete before proceeding.
- Latches are often used in scenarios like waiting for a specific number of tasks to complete in a parallel computation, waiting for resources to be initialized, or waiting for a group of threads to finish their work before continuing.

## CyclicBarrier

- Synchronization mechanism that allows a group of threads to wait at a predetermined point until all threads in the group have reached that point.
- The `CyclicBarrier` class is initialized with a count, which represents the number of threads that must reach the barrier before they are released.
- Threads that need to synchronize with the barrier call the `await()` method on the `CyclicBarrier`. Each thread that calls `await()` indicates that it has reached the barrier.
- Once the specified number of threads have called `await()`, the barrier is tripped, and all the waiting threads are released simultaneously.
- `CyclicBarrier` can be reused after all threads have been released. Once the barrier is tripped, the count is reset, and threads can use it again for synchronization.
- `CyclicBarrier` can also be initialized with a barrier action, which is a runnable task that is executed once the barrier is tripped. This can be useful for performing additional actions after all threads have reached the barrier.
- `CyclicBarrier` is often used in scenarios where **multiple threads need to wait for each other** to reach a common point before proceeding, such as dividing a complex computation into smaller tasks that can be executed in parallel and synchronized at certain stages.

## `BlockingQueue`

- `BlockingQueue` is a data structure that provides thread-safe operations for adding and removing elements.
- It supports blocking operations, meaning threads can be blocked when attempting to add or remove elements from the queue.
- If a thread tries to remove an element from an empty queue, it will be blocked until an element becomes available.
- If a thread tries to add an element to a full queue, it will be blocked until space becomes available.
- Commonly used in concurrent programming scenarios (Producer-Consumer) where multiple threads need to coordinate and exchange data efficiently, ensuring that the producer and consumer threads are synchronized.

## `DelayQueue`

- `DelayQueue` is a class that implements the `BlockingQueue` interface and provides a special priority queue for elements that have a delay associated with them.
- Allows the elements to have a specific delay before they can be taken from the queue.
- `DelayQueue` orders elements based on their delay time, so the element with the shortest delay will be the first one to be available for retrieval.
- Elements can only be taken from the queue when their delay has expired. If an element's delay has not expired, the `take()` method will block until the delay is over.
- `Delayed` interface requires implementing 2 methods:
  - `getDelay(TimeUnit unit)`: specifies the remaining time for an element's delay to expire.
  - `compareTo(Delayed o)`: order elements in the queue.
- Useful in scenarios where you need to schedule tasks or events to occur after a specific delay, such as delayed execution of tasks or managing timed events.

## `PriorityQueue`

- `PriorityBlockingQueue` is a class that implements the `BlockingQueue` interface and represents a thread-safe, priority-based queue.
- Used in multithreading where elements are inserted into the queue by different threads and the order of retrieval is based on the priority of the elements.
- Elements in a `PriorityBlockingQueue` are ordered based on their natural ordering or a custom comparator provided at the time of creation.
- When a thread tries to remove an element from the queue, it gets the element with the highest priority (the minimum element according to the natural order or comparator).
- If multiple elements have the **same priority**, their order or retrieval is not guaranteed.
- If the queue is empty, the retrieval operation blocks the calling thread until an element becomes available.

## `HashMap` as a Synchronized Collections

- `Map<String, Integer> map = Collections.synchronizedMap(new HashMap<>());`
  - NOT AN EFFICIENT SOLUTION!
  - Can make a `Map` synchronized with the help of the `Collections` class.
  - Uses the **intrinsic lock** which means that independent operations may have to wait for each other.
    - not efficient because it uses a single thread (intrinsic lock) can manipulate the underlying data structure.
- `ConcurrentHashMap`
  - Can make a map synchronized with defining **segments** of the underlying array.
  - these segments (16 items) can be updated only by a **single thread**.
  - Assign a lock to every segment instead of using a single lock.
  - **Every thread can read** any item from the underlying array without restrictions.

## `Exchanger`

<img src="./pics/exchanger.png" width="50%" />

- `Exchanger` is a synchronization construct that allows **2 threads to exchange objects**.
- Provides a point of rendezvous where 2 threads can swap objects.
- Operates based on the principle of thread synchronization, ensuring that the exchange happens only when both threads have reached the exchange point.

---

- Main features and usage patterns of the `Exchanger` class:
  - `Exchanger` class is a **generic** class that takes a type parameter specifying the type of objects to be exchanged. E.g., `Exchanger<Integer>`
  - `Exchanger` class provides 2 methods: `exchange()` and `exchange(V value)`.
  - The `exchange()` method is a _blocking_ operation that waits for another thread to arrive at the exchange point before proceeding with the exchange. It returns the object received from the other thread.
  - The `exchange(V value)` method allows a thread to exchange its own object with the other thread. It waits for the other thread to arrive at the exchange point, exchanges the objects, and returns the object received from the other thread.
  - If 1 of threads arrives at the exchange point before the other, it will wait until the other thread arrives. This synchronization ensures that both threads perform the exchange together.
  - If only 1 thread calls the `exchange()` method, it will be blocked until another thread arrives.
  - If both threads call the `exchange()` method simultaneously, they will swap their objects and continue execution.

---

## `CopyOnWriteArrayList` (Concurrent Collections)

- `List<T> list = new CopyOnWriteArrayList<>();`: efficient implementation of the synchronized `ArrayList`.
- `CopyOnWriteArrayList` is a class that provides thread-safe access to a list of elements. It implements the `List` interface.
- Main feature of `CopyOnWriteArrayList` is that it **creates a new copy of the underlying array** every time a modification operation is performed, ensuring thread safety without the need for explicit synchronization.
- Designed for scenarios where the number of read operations significantly outweighs the number of write operations.
- Read operations on `CopyOnWriteArrayList` are fast and efficient because they do not require locking or copying of the array.
- Write operations such as adding, modifying or removing elements, are relatively expensive as they involve creating a new copy of the array, O(N) operation.
  - Write operations are **atomic**, threads must wait for each other to update the list.
- `CopyOnWriteArrayList` trades off memory consumption and write operation cost for efficient concurrent read access. It is important to choose the appropriate data structure based on the specific requirements of your application.

# Parallel Computing

- When we use standard **sequential approach**, then the programming language executes the operations one after another.
- **Multithreaded approach** uses multiple threads but these threads are executed with the time-slicing algorithm.
- With **parallel algorithms**, we execute different tasks on different processors (or processor cores) simultaneously.
- Some problems are sequential by default so we are unable to apply parallelization.
- Sometimes parallel algorithms are slower than sequential implementations because of the communication between the threads which slows down the application.

---
- Multithreading is used to execute independent tasks without blocking other tasks.
- Parallelization is used to **speed up a given application**.
---

- It depends on the **operating system** on how the threads will be executed.
- If there is just a single processor core, then the time-slicing algorithm executes the threads (multithreading).
- If there are multiple processor cores, then the operating system may **execute the threads in parallel**.

## Parallel Algorithm vs Sequential Algorithm

|Parallel Algorithm|Sequential Algorithm|
|---|---|
|Can divide the problem into multiple **independent subsets**.|Steps are usually depending on each other.|
|Can execute the operations on these subsets simultaneously.|Unable to execute the operations independently.|
|Examples include checking prime numbers, sorting or searching algorithms|Examples include numerical methods (differential equations or numerical integral)|

## Problems with Parallel Computing

- Communication
  - For sequential algorithms, we measure the running time and memory complexity of the algorithms.
  - For parallel algorithm, have to consider the **communication factor** between the threads (parallel slowdown).
- Load Balance
  - Have to ensure to split the work evenly among the processors (CPU or CPU cores).
  - Have to ensure that processors should not wait for each other to finish - every processor should do the same amount of work.
  - E.g., Finding all prime factors for numbers 0 - 1000. We would assume one processor finds prime factors from 0 - 500 and the other from 501 - 1000. But as the number gets higher, it takes a longer time to find the prime factors. So the processor running for 0 - 500 will be completed earlier.

## Merge Sort Algorithm (Multithreading and Parallel Programming)

- Merge sort has O(n log n) time complexity.
- Requires O(N) space complexity.
- Divide and conquer approach
  1. Divide the array into 2 sub-arrays recursively.
  2. Sort these sub-arrays recursively with merge sort again.
  3. If there is only a single item left in the sub-array, consider it to be sorted by definition (or can use insertion sort on small arrays).
  4. Merge the sub-arrays to get the final sorted array.

# Sum Problem

- Sequential Sum
  - the standard sequential sum function considers all the items in **O(N) linear running** time complexity.
- Parallel Sum
  - the parallel sum function **splits the original array into N chunks** and does linear searches independently.
  - The number of processors defines the value of N.
  - If we have 2 processors (or cores), then split the original array into 2 and do sum operation in a parallel manner.
  - If we have 3 processors (or cores), then split the original array into 3 and do sum operation in a parallel manner.
  - **CRUCIAL**: final operation is sequential so the threads have to wait for each other to finish.