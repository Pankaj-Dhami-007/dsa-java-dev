package dhami.in.servlet;

import com.google.gson.Gson;
import dhami.in.model.User;
import dhami.in.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/api/users")
public class UserCrudServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.setContentType("application/json");

        String idParam = req.getParameter("id");
        if (idParam != null) {
            User user = userService.getById(Long.parseLong(idParam));
            if (user == null) {
                res.setStatus(404);
                res.getWriter().write("{\"error\":\"User not found\"}");
            } else {
                res.getWriter().write(gson.toJson(user));
            }
        } else {
            Collection<User> users = userService.getAll();
            res.getWriter().write(gson.toJson(users));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.setContentType("application/json");

        User input = gson.fromJson(req.getReader(), User.class);
        User created = userService.create(input.getName(), input.getEmail());

        res.setStatus(201);
        res.getWriter().write(gson.toJson(created));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.setContentType("application/json");

        User input = gson.fromJson(req.getReader(), User.class);
        User updated = userService.update(input.getId(), input.getName(), input.getEmail());

        if (updated == null) {
            res.setStatus(404);
            res.getWriter().write("{\"error\":\"User not found\"}");
        } else {
            res.getWriter().write(gson.toJson(updated));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.setContentType("application/json");

        String idParam = req.getParameter("id");
        if (idParam == null) {
            res.setStatus(400);
            res.getWriter().write("{\"error\":\"id parameter required\"}");
            return;
        }

        boolean deleted = userService.delete(Long.parseLong(idParam));
        if (deleted) {
            res.getWriter().write("{\"message\":\"User deleted\"}");
        } else {
            res.setStatus(404);
            res.getWriter().write("{\"error\":\"User not found\"}");
        }
    }
}
