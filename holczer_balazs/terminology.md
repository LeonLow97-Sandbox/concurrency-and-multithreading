# Terminologies

## Atomicity

- Atomicity refers to the property of an operation or a sequence of operations being treated as a single indivisible unit of execution.
- An atomic operation appears to be executed instantaneously, without any interference from other threads.
- Atomicity ensures that an operation is either fully completed or not started at all, with no intermediate state visible to other threads.
- Atomicity is important in concurrent programming to maintain data consistency and prevent race conditions.
- In Java, atomicity can be achieved through synchronization mechanism like locks, the `synchronized` keyword or by using atomic classes provided in the `java.util.concurrent.atomic` package.
---
- Examples of atomic operations:
    - Reading and writing variables of primitive types (e.g., `int`, `boolean`) without interference from other threads.
    - Incrementing or decrementing a counter variable by 1.
    - Performing compare-and-swap operations, where a value is updated only if it matches an expected value.
---
- Reading and writing variables of non-primitive types (e.g., objects) that require multiple steps or involve multiple memory locations.
- Incrementing or decrementing a non-atomic counter variable by a value greater than 1.
- Performing read-modify-write operations that are not atomic, such as retrieving s value, modifying it, and storing it back.
- Non-atomic operations can lead to race conditions, inconsistent states, and data corruption in a multithreaded environment.
- To ensure atomicity for non-atomic operations, synchronization mechanisms like locks or atomic classes should be used to protect the critical sections of code.

## Contention

- Contention refers to a situation where multiple threads compete for shared resources, such as variables, objects or sections of code, leading to potential conflicts and synchronization issues.
- High contention arises when multiple threads attempt to simultaneously read from or write to the same resource, potentially leading to race conditions and inconsistent results.
- For example, if multiple threads are performing frequent increments or updates on a shared counter variable, there is a high likelihood of contention.