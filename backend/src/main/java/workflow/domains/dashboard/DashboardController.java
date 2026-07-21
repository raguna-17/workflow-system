package workflow.domains.dashboard;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import workflow.domains.user.User;



@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {



    private final DashboardService dashboardService;




    /**
     * ダッシュボード取得
     */
    @GetMapping
    public ResponseEntity<DashboardService.DashboardResponse> getDashboard(

            Authentication authentication

    ){


        User user =
                (User)
                authentication.getPrincipal();




        return ResponseEntity.ok(

                dashboardService
                        .getDashboard(user)

        );

    }

}