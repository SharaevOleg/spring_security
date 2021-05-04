package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.IUserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping("login")
	public String loginPage() {
		return "login";
	}

	@GetMapping(value = "/admin")
	public String printAllUsers(ModelMap model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("allUsers", users);
		return "admin";
	}

	@RequestMapping(path = "admin/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
		return "redirect:/admin";
	}

	@RequestMapping(path = {"admin/edit", "admin/edit/{id}"})
	public String editUser(Model model, @PathVariable("id") Optional<Long> id) {
		if (id.isPresent()) {
			User user = userService.getUserById(id.get());
			model.addAttribute("user", user);
		} else {
			model.addAttribute("user", new User());
		}
		return "edit";
	}

	@PostMapping("/createUser")
	public String createUser(User user) {
		userService.createUser(user);
		return "redirect:/admin";
	}

	@GetMapping("admin/update")
	public String updateUserForm(ModelMap model, User user) {
		model.addAttribute("user", userService.getUserById(user.getId()));
		return "edit";
	}

	@PostMapping("admin/update")
	public String updateUser(User user) {
		userService.updateUser(user);
		return "redirect:/admin";
	}

	@RequestMapping(value = "user")
	public String userGet(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("user", userService.getUserByEmail(auth.getName()));
		return "user";
	}
}