package br.com.justoeu.wn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/subs", loadOnStartup = 1, asyncSupported = true)
public class NotifyMePullingExectors extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Queue<AsyncContext> clients = new ConcurrentLinkedQueue<AsyncContext>();
	private final BlockingQueue<String> messages = new LinkedBlockingQueue<String>();
	private final AtomicInteger count = new AtomicInteger();
	private final AtomicInteger countClients = new AtomicInteger();

	@Override
	public void init() throws ServletException {
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@Override
			public void run() {
				while (true) {

					try {
						String message = messages.take();
						for (AsyncContext ctx : clients) {
							PrintWriter writer = ctx.getResponse().getWriter();
							writer.println(message);
							writer.flush();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		});
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse arg1) throws ServletException, IOException {
		AsyncContext ctx = req.startAsync();
		ctx.setTimeout(3000000);
		clients.add(ctx);
		System.out.println("novo cliente. id: " + countClients.incrementAndGet());
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse arg1) throws ServletException, IOException {
		System.out.println("enviando mensagem para   " + countClients + " clientes");

		messages.add(String.format("Contador: %d %n", count.incrementAndGet()));
	}
}