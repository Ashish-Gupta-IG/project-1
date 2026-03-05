public class ConnectionPoolRunner {
    public static void main(String[] args) throws Exception {
        ConnectionPool pool = new ConnectionPool();
        
        Runnable worker = () -> {
            try {
                for (int i = 0; i < 3; i++) {
                    Connection c = pool.borrowConnection();
                    c.doWork();
                    Thread.sleep(300 + (int)(Math.random()*400));
                    pool.returnConnection(c);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread[] threads = new Thread[12];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(worker, "Worker-" + i);
            threads[i].start();
        }
        
        for (Thread t : threads) t.join();
        
        System.out.println("Final → available: " + pool.getAvailableCount() 
                        + ", in use: " + pool.getInUseCount());
    }
}
