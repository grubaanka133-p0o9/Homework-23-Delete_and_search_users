package homework.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ResponseBody
    @GetMapping("/users")
    public String users() {
        List<User> users = userRepository.getAll();

        String result = "";

        for (User user : users) {
            result += user.toString() + "<br/>";
        }
        return result;
    }

    @RequestMapping("/add")
    public String add(@RequestParam(defaultValue = "Anonim", required = false) String imie,
                      @RequestParam String nazwisko,
                      @RequestParam Integer wiek) {

        if (StringUtils.isEmpty(imie)) {
            return "redirect:/err.html";
        } else {
            User user = new User(imie, nazwisko, wiek);
            userRepository.add(user);
            return "redirect:/success.html";

        }
    }

    @ResponseBody
    @PostMapping("/search-first-name")
    public String searchByFirstName(@RequestParam String firstName) {
        return printList(filterByFirstName(firstName));
    }

    @ResponseBody
    @PostMapping("/search-last-name")
    public String searchByLastName(@RequestParam String lastName) {
        return printList(filterByLastName(lastName));
    }

    @ResponseBody
    @PostMapping("/search-age")
    public String searchByAge(@RequestParam String age) {
        try {
            return printList(filterByAge(age));
        } catch (NumberFormatException exception) {
            return "To nie jest liczba";
        }
    }

    @ResponseBody
    @PostMapping("/delete-first-name")
    public String deleteByFirstName(@RequestParam String firstName) {
        return deleteUsersFromList(filterByFirstName(firstName));
    }

    @ResponseBody
    @PostMapping("/delete-last-name")
    public String deleteByLastName(@RequestParam String lastName) {
        return deleteUsersFromList(filterByLastName(lastName));
    }

    @ResponseBody
    @PostMapping("/delete-age")
    public String deleteAge(@RequestParam String age) {
        try {
            return deleteUsersFromList(filterByAge(age));
        } catch (NumberFormatException exception) {
            return "To nie jest liczba";
        }
    }


    private String deleteUsersFromList(List<User> users) {
        if (users.isEmpty()) {
            return "Lista jest pusta";
        }
        for (int i = 0; i < users.size(); i++) {
            userRepository.deleteFromList(users.get(i));
        }
        return "Usunięto";
    }


    private String printList(List<User> users) {
        String result = "";
        if (users.isEmpty()) {
            return "Lista jest pusta";
        }
        for (int i = 0; i < users.size(); i++) {
            result += users.get(i).toString() + "<br/>";
        }
        return result;
    }

    private List<User> filterByFirstName(String firstname) {
        List<User> filteredList = new ArrayList<>();
        for (int i = 0; i < userRepository.getAll().size(); i++) {
            if (userRepository.getAll().get(i).getFirstName().equals(firstname)) {
                filteredList.add(userRepository.getAll().get(i));
            }
        }
        return filteredList;
    }

    private List<User> filterByLastName(String lastname) {
        List<User> filteredList = new ArrayList<>();
        for (int i = 0; i < userRepository.getAll().size(); i++) {
            if (userRepository.getAll().get(i).getLastName().equals(lastname)) {
                filteredList.add(userRepository.getAll().get(i));
            }
        }
        return filteredList;
    }

    private List<User> filterByAge(String age) throws NumberFormatException {
        List<User> filteredList = new ArrayList<>();
        int ageValue = Integer.parseInt(age);
        for (int i = 0; i < userRepository.getAll().size(); i++) {
            if (userRepository.getAll().get(i).getAge() == ageValue) {
                filteredList.add(userRepository.getAll().get(i));
            }
        }
        return filteredList;
    }

}
