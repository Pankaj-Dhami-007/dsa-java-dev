package dhami.in.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.setContentType("text/html");
        res.getWriter().write("""
            <html>
            <body>
                <h1>Hello from Servlet!</h1>
                <p>Method: GET</p>
                <p>Context Path: %s</p>
            </body>
            </html>
            """.formatted(req.getContextPath()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.setContentType("application/json");
        res.getWriter().write("{\"message\":\"Hello POST received\"}");
    }
}
