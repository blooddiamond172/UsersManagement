package com.dungnv.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dungnv.dao.UserDAO;
import com.dungnv.model.User;

@WebServlet(urlPatterns = { "/" })
public class UserServlet extends HttpServlet{
	private UserDAO userDAO;
	
	@Override
	public void init() throws ServletException {
		userDAO = new UserDAO();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		
		switch (action) {
		case "/new":
			showNewForm(req,resp);
			break;
		case "/insert":
			addUser(req,resp);
			break;
		case "/edit":
			showEditForm(req,resp);
			break;
		case "/update":
			updateList(req,resp);
			break;
		case "/delete":
			deleteUserInList(req,resp);
			break;
		default:
			usersList(req,resp);
			break;
		}
	}

	

	private void deleteUserInList(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		userDAO.deleteUser(id);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("list");
		try {
			requestDispatcher.forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void updateList(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String country = req.getParameter("country");
		User user = new User(id,name,email,country);
		userDAO.updateUser(user);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("list");
		try {
			requestDispatcher.forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		User user = userDAO.selectUser(id);
		req.setAttribute("user", user);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("UserAdd.jsp");
		try {
			requestDispatcher.forward(req, resp);
			return;
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void addUser(HttpServletRequest req, HttpServletResponse resp) {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String country = req.getParameter("country");
		User user = new User(name,email,country);
		userDAO.insertUser(user);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("list");
		try {
			requestDispatcher.forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		
	}

	private void showNewForm(HttpServletRequest req, HttpServletResponse resp) {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("UserAdd.jsp");
		try {
			requestDispatcher.forward(req, resp);
			return;
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	private void usersList(HttpServletRequest req, HttpServletResponse resp) {
		List<User> users = userDAO.selectAllUsers();
		req.setAttribute("users", users);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("UserList.jsp");
		try {
			requestDispatcher.forward(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		
	}
}
