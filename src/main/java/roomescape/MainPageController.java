package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/reservation")
    public String showReservationForm() {
        return "new-reservation";
    }

    @GetMapping("/time")
    public String showTimeForm() { return "time"; }
}
