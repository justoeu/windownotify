package br.com.justoeu.wn.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.enterprise.event.Observes;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.yaml.snakeyaml.Yaml;

import br.com.justoeu.wn.contants.NotifyContans;
import br.com.justoeu.wn.domain.Notify;
import br.com.justoeu.wn.domain.NotifyConfig;
import br.com.justoeu.wn.listener.NotifyListener;

@WebServlet(urlPatterns = "/subscribe", loadOnStartup=1, asyncSupported = true)
public class NotifyMePulling extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Queue<AsyncContext> contexts = new ConcurrentLinkedQueue<AsyncContext>();
	private NotifyConfig notifyConfig = null;

	@Override
	public void init() throws ServletException {
        try{
        	InputStream targetStream = this.getClass().getResourceAsStream("/notifyConfig.yml");
        	notifyConfig = new Yaml().loadAs(targetStream, NotifyConfig.class);
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
	
    public void notifyClientsAboutFinishProcess(@Observes final Notify notify) {
        for (final AsyncContext ac : contexts) {
            try {
				HttpServletRequest request = (HttpServletRequest) ac.getRequest();

				if (request.getSession(false).getId().equals(notify.getSessionClientID())){
				
	                final ServletOutputStream os = ac.getResponse().getOutputStream();
	                os.println(notify.getNameFile());
	                os.flush();
	                ac.complete();
	                
				}
            } catch (IOException ex) {
            	ex.printStackTrace();
//            } finally{
//            	System.out.println( "qtd Contextos " + contexts.size());
            }
        }
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

      if ((request.getParameter(notifyConfig.getNameParameter()) != null) && isAjax(request)){

        	sessionCreate(request);
        	
            response.setContentType(MediaType.TEXT_PLAIN);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.flushBuffer();

	        final AsyncContext ac = request.startAsync(request, response);
	        ac.setTimeout(notifyConfig.getTimeoutRegister());
	        ac.addListener(new NotifyListener(contexts, ac));
	        
	        contexts.add(ac);
        }
    }

	private HttpSession sessionCreate(final HttpServletRequest request) {
		return request.getSession(false) == null ? request.getSession(): request.getSession(false);
	}
    
    private boolean isAjax(final HttpServletRequest request) {
        return NotifyContans.XML_HTTP_REQUEST.equals(request.getHeader(NotifyContans.HEADER_REQUEST_AJAX));
    }
}