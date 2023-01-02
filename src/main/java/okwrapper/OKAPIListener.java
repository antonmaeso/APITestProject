package okwrapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;

public class OKAPIListener {

    public void createNewListener() throws Exception {

        // Create the Jetty server
        Server server = new Server(8080);

        // Create a servlet context handler
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);

        // Add a servlet to the context
        context.addServlet(new ServletHolder(new TemporaryServlet()), "/*");

        // Start the server
        server.start();

        // Wait for the server to stop
        server.join();

    }

    private static class TemporaryServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            System.out.println("received callback");
        }

        @Override
        public void destroy() {
            // Destroy the servlet here
        }
    }

}




