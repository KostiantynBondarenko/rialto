package ua.estate.rialto.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
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
//
//    @GetMapping("/meals")
//    public String meals(Model model) {
//        model.addAttribute("meals",
//                MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
//        return "meals";
//    }
}
