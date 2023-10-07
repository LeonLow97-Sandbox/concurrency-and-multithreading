# Dining Philosophers Problem

1. The problem involves 5 philosophers sitting at a table, with a fork placed between each pair of adjacent philosophers. There are a total of 5 forks.
2. Each philosopher has 2 actions: thinking and eating. They need 2 forks to eat, one for each hand.
3. The challenge is to design a solution that prevents deadlocks and starvation, ensuing that each philosopher gets a fair chance to eat without causing a deadlock situation.
4. The main constraints of the problem are:
    - A philosopher can only pick up a fork if it's available (not being used by another philosopher).
    - A philosopher can only start eating if both the left and right forks are available.
    - How to create a **concurrent algorithm** such that no philosopher will starve?

