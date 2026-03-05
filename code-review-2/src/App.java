public class App {
    public static void main(String[] args) throws Exception {
        SmartCounter counter = new SmartCounter();

        // many threads doing increment()
        Runnable producer = () -> { for(int i=0; i<100; i++) counter.increment(); };

        // many threads doing decrement() and sometimes waitUntilZero()
        Runnable consumer = () -> {
            try {
                counter.decrement();
                counter.waitUntilZero();
                System.out.println("Reached zero!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }
}
