package ua.estate.rialto.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private MealService mealService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

//    @GetMapping("/users")
//    public String users(Model model) {
//        model.addAttribute("users", userService.getAll());
//        return "users";
//    }

//    @PostMapping("/users")
//    public String setUser(HttpServletRequest request) {
//        int userId = Integer.valueOf(request.getParameter("userId"));
//        AuthorizedUser.setId(userId);
//        return "redirect:meals";
//    }
//
//    @GetMapping("/meals")
//    public String meals(Model model) {
//        model.addAttribute("meals",
//                MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
//        return "meals";
//    }
}
