# Problem statement

Implement a thread-safe counter that:

## Requirements
supports `increment`, `decrement`, `getCurrentValue`
whenever the value reaches exactly zero, waiting threads should be notified
multiple producer/consumer threads will use it