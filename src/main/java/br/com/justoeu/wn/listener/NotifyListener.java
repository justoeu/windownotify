package br.com.justoeu.wn.listener;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

public class NotifyListener implements AsyncListener {
	
	private Queue<AsyncContext> contexts = new ConcurrentLinkedQueue<AsyncContext>();
	private AsyncContext ac = null;
	public NotifyListener(final Queue<AsyncContext> contexts, final AsyncContext ac){
		this.contexts = contexts;
		this.ac = ac;
	}
	
    @Override
    public void onComplete(final AsyncEvent event) throws IOException {
        contexts.remove(ac);
    }

    @Override
    public void onTimeout(final AsyncEvent event) throws IOException {
       contexts.remove(ac);
    }

    @Override
    public void onError(final AsyncEvent event) throws IOException {
        contexts.remove(ac);
    }

    @Override
    public void onStartAsync(final AsyncEvent event) throws IOException { }
}
