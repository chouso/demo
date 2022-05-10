package com.chcodes.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.chcodes.demo.domain.Category;
import com.chcodes.demo.domain.Product;
import com.chcodes.demo.repo.CategoryRepo;
import com.chcodes.demo.repo.UserRepo;
import com.chcodes.demo.service.CategoryService;
import com.chcodes.demo.service.ProductService;
import com.chcodes.demo.service.UserService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE",
						"OPTIONS");
			}
		};

	}

	@Bean
	CommandLineRunner run(UserService userService, UserRepo userRepo, CategoryService categService,
			ProductService productService, CategoryRepo categRepo) {
		return args -> {
			/*categService.ajoutCategory(new Category("categ1"));
			categService.ajoutCategory(new Category("categ2"));
			categService.ajoutCategory(new Category("categ3"));
			
			productService.saveProduct(new Product(1, "produi1", 5, 1500, categRepo.findByNom("categ1")));
			productService.saveProduct(new Product(2, "produi2", 15, 1300, categRepo.findByNom("categ1")));
			productService.saveProduct(new Product(3, "produi3", 2, 200, categRepo.findByNom("categ1")));*/
			

			/*
			 * userService.saveCommand(new Command(LocalDate.now(),
			 * userRepo.findByUserName("arnold"))); userService.saveCommand(new
			 * Command(LocalDate.now().plusDays(1), userRepo.findByUserName("arnold")));
			 * userService.saveCommand(new Command(LocalDate.now().plusDays(2),
			 * userRepo.findByUserName("arnold")));
			 * 
			 * userService.saveCommand(new Command(LocalDate.now(),
			 * userRepo.findByUserName("john"))); userService.saveCommand(new
			 * Command(LocalDate.now().plusDays(1), userRepo.findByUserName("john")));
			 * userService.saveCommand(new Command(LocalDate.now().plusDays(2),
			 * userRepo.findByUserName("john")));
			 */
			/*
			 * userService.saveRole(new Role(null, "ROLE_USER")); userService.saveRole(new
			 * Role(null, "ROLE_MANAGER")); userService.saveRole(new Role(null,
			 * "ROLE_ADMIN")); userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
			 * 
			 * userService.saveUser(new AppUser(null, "John Travolta", "john", "1234", new
			 * ArrayList<>())); userService.saveUser(new AppUser(null, "Will SMith", "will",
			 * "1234", new ArrayList<>())); userService.saveUser(new AppUser(null,
			 * "Jim carry", "jim", "1234", new ArrayList<>())); userService.saveUser(new
			 * AppUser(null, "Arnold Schwarzenegger", "arnold", "1234", new ArrayList<>()));
			 * 
			 * userService.addRoleToUser("john", "ROLE_USER");
			 * userService.addRoleToUser("john", "ROLE_MANAGER");
			 * userService.addRoleToUser("will", "ROLE_MANAGER");
			 * userService.addRoleToUser("jim", "ROLE_ADMIN");
			 * userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
			 * userService.addRoleToUser("arnold", "ROLE_ADMIN");
			 * userService.addRoleToUser("arnold", "ROLE_USER");
			 */
		};
	}

}
