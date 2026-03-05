public class SmartCounter {

    private int count = 0;
    private Object lock = new Object();

    public void increment() {
        synchronized (lock) {
            count++;
            if (count == 0) {
                lock.notifyAll();
            }
        }
    }

    public void decrement() throws InterruptedException {
        synchronized (lock) {
            while (count <= 0) {
                System.out.println("Waiting because count is " + count);
                lock.wait();
            }
            count--;
            if (count == 0) {
                lock.notifyAll();
            }
        }
    }

    public int get() {
        synchronized (lock) {
            return count;
        }
    }

    public void waitUntilZero() throws InterruptedException {
        synchronized (lock) {
            while (count != 0) {
                lock.wait();
            }
        }
    }
}
