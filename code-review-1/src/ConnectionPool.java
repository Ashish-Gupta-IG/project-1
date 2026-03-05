import java.util.ArrayList;
import java.util.List;

class Connection {
    protected final int id;
    
    public Connection(int id) {
        this.id = id;
        System.out.println("Created connection #" + id);
        try {
            Thread.sleep(1000); // pretend it's expensive
        } catch (InterruptedException e) {}
    }
    
    public void doWork() {
        System.out.println("Using connection #" + id);
    }
}

public class ConnectionPool {
    
    private List<Connection> available = new ArrayList<>();
    private List<Connection> inUse = new ArrayList<>();
    private int nextId = 1;
    private final int maxSize = 5;
    
    public Connection borrowConnection() throws InterruptedException {
        synchronized (this) {
            while (available.isEmpty() && inUse.size() >= maxSize) {
                System.out.println("Pool full - waiting...");
                this.wait();
            }
            
            if (!available.isEmpty()) {
                Connection conn = available.remove(0);
                inUse.add(conn);
                return conn;
            }
            
            // create new
            Connection newConn = new Connection(nextId++);
            inUse.add(newConn);
            return newConn;
        }
    }
    
    public void returnConnection(Connection conn) {
        synchronized (this) {
            if (inUse.contains(conn)) {
                inUse.remove(conn);
                available.add(conn);
                this.notifyAll();
                System.out.println("Returned connection #" + conn.id);
            } else {
                System.out.println("Warning: trying to return unknown connection!");
            }
        }
    }
    
    // just for debugging
    public int getAvailableCount() {
        return available.size();
    }
    
    public int getInUseCount() {
        return inUse.size();
    }
}