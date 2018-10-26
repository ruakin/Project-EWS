package de.ews.server.communication;

public abstract class AbstractStatement implements IStatement {

    public void asynchronousPerform() throws NullPointerException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Util.SEMAPHORE.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                perform();
                Util.SEMAPHORE.release();
            }
        }).start();
    }
}
