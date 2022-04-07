public class Main {
    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {
        Main main = new Main();
        char[] abc = new char[]{'A', 'B', 'C'};
        Thread thread1 = new Thread(() -> main.print(new char[]{abc[0], abc[1]}));
        Thread thread2 = new Thread(() -> main.print(new char[]{abc[1], abc[2]}));
        Thread thread3 = new Thread(() -> main.print(new char[]{abc[2], abc[0]}));
        thread1.start();
        thread2.start();
        thread3.start();
    }

    public void print(char[] currentAndNextSymbol) {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != currentAndNextSymbol[0]) {
                        mon.wait();
                    }
                    System.out.print(currentAndNextSymbol[0]);
                    currentLetter = currentAndNextSymbol[1];
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
