package com.chcodes.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.chcodes.demo.domain.AppUser;
import com.chcodes.demo.domain.Category;
import com.chcodes.demo.domain.Command;
import com.chcodes.demo.domain.Product;
import com.chcodes.demo.domain.Role;
import com.chcodes.demo.domain.UserInformations;
import com.chcodes.demo.repo.RoleRepo;
import com.chcodes.demo.service.CategoryService;
import com.chcodes.demo.service.CommandService;
import com.chcodes.demo.service.ProductService;
import com.chcodes.demo.service.UserInformationsService;
import com.chcodes.demo.service.UserService;

@Controller
public class MainControlleur {
	@Autowired
	UserService userService;
	@Autowired
	CommandService commandService;
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserInformationsService userInformationsService;
	
	@Autowired
	private RoleRepo roleRepo;
	
	/*
	 * @GetMapping("/login") public String login() { return "/login"; }
	 */
	@GetMapping(value = { "/", "/index" })
	public String index(Model model) {

		model.addAttribute("message", message);

		return "index";
	}

	// Injected (inject) via application.properties.
	@Value("${welcome.message}")
	private String message;

	@Value("${error.message}")
	private String errorMessage;

	@GetMapping("productsList")
	public String getProducts(Model m) {
		m.addAttribute("listeP", productService.getProducts());
		return "affiche";
	}

	@GetMapping("commandsList")
	public String getCommands(Model m) {
		m.addAttribute("listeCommands", commandService.getCommands());
		return "commandsList";
	}
	@GetMapping(value = { "/userList" })
	public String getUsers(Model model) {
		model.addAttribute("users", userService.getUsers());
		model.addAttribute("listRoles", roleRepo.findAll());
		model.addAttribute("user", new AppUser());
		model.addAttribute("userInformations", new UserInformations());
		return "userList";
	}

	@GetMapping(value = { "/categList" })
	public String getCategs(Model model) {

		model.addAttribute("categs", categoryService.getAllCategories());
		return "categList";
	}

	@GetMapping(value = { "/addCateg" })
	public String showAddCategPage(Model model) {

		Category cForm = new Category();
		model.addAttribute("cForm", cForm);

		return "addCateg";
	}

	@PostMapping(value = { "/categ-add" })
	public String saveCategPage(Category c) {
		categoryService.saveCategory(c);

		return "redirect:/categList";
	}

	

	@GetMapping("/produit/add")
	public String add(Model m) {
		m.addAttribute("categories", categoryService.getAllCategories());
		return "ajout";
	}

	@PostMapping("/addProduct")
	public String ajout(Product product) {
		productService.saveProduct(product);
		return "redirect:/productsList";
	}


	@GetMapping("/produit/update/{id}")
	public String update(Model m, @PathVariable int id) {
		m.addAttribute("categories", categoryService.getAllCategories());
		m.addAttribute("produit", productService.getProductById(id));
		return "modif";
	}

	@PostMapping("/produit/update")
	public String modif(Product p) {
		productService.saveProduct(p);
		return "redirect:/productsList";
	}

	@DeleteMapping("/produit/delete/{id}")
	public String delete(@PathVariable int id) {
		productService.deleteProduct(id);
		return "redirect:/productsList";
	}

	// categ update

	@GetMapping("/categ/update/{id}")
	public String updateCateg(Model m, @PathVariable int id) {

		m.addAttribute("c", categoryService.getCategory(id));
		return "modifCateg";
	}

	@PostMapping("/categ/update")
	public String modifCateg(Category c) {
		categoryService.saveCategory(c);
		return "redirect:/categList";
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Long id, Model model) {
		AppUser user = userService.get(id);
		List<Role> listRoles =  roleRepo.findAll();
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		return "redirect:/usersList";
	}
	
	@PostMapping("/user/save")
	public String saveUser(AppUser user) {
		userInformationsService.saveUserInfos(user.getUserInformations());
		userService.saveUser(user);
		return "redirect:/index";
	}
	   @PostMapping(path = "/{id}")
	    public RedirectView updateUser(RedirectAttributes redirectAttributes, @PathVariable("id") Long id, @ModelAttribute AppUser user) {
	        userService.updateUser(id, user);
	        RedirectView redirectView = new RedirectView("/", true);
	        return redirectView;
	    }
	   
	   @GetMapping("/command/update/{id}")
		public String updateCommand(Model m, @PathVariable Long id) {
			m.addAttribute("users", userService.getUsers());
			m.addAttribute("command", commandService.getCommandById(id));
			return "modifCommand";
		}

		@PostMapping("/command/update")
		public String modifCommand(Command c) {
			commandService.saveCommand(c);
			return "redirect:/commandsList";
		}
		
		@GetMapping("/command/add")
		public String addCommand(Model m) {
			m.addAttribute("users", userService.getUsers());
			return "ajoutCommand";
		}
		@PostMapping("/addCommand")
		public String ajoutCommand(Command command) {
			commandService.saveCommand(command);
			return "redirect:/commandsList";
		}

}
