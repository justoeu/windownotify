package br.com.justoeu.wn.servlet;

import java.io.IOException;
import java.util.Random;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.justoeu.wn.domain.Notify;

@WebServlet(urlPatterns = "/generatefile")
public class GenerateFile extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Inject @Any
	private Event<Notify> notifyEvent;

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,	IOException {

		Notify notify = new Notify();
		notify.setNameFile("arquivo_" + new Random().nextInt() + ".txt");
		notify.setSessionClientID(req.getSession(false).getId());
		
		try {
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		notifyEvent.fire(notify);
	}
	
}