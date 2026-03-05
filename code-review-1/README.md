# Problem statement

Implement a simple Object Pool for reusable Connection objects (Pretend that Connection is some expensive-to-create object).

## Requirements

1. Pool should have maximum size of 5
2. When someone calls `borrowConnection()`, either give already existing idle connection or create a new one (but never more than 5)
3. When someone calls `returnConnection(Connection conn)`, put it back to the pool so it can be reused
4. If pool is full (i.e., 5 are currently borrowed), caller should wait until some connection is returned
5. Simple thread-safety is required