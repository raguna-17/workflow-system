package kakeibo.summary;

import kakeibo.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/summary")
public class SummaryController {


    private final SummaryService summaryService;


    public SummaryController(
            SummaryService summaryService
    ) {
        this.summaryService = summaryService;
    }


    /**
     * カテゴリ別支出取得
     */
    @GetMapping("/expenses")
    public SummaryService.SummaryResponse getExpenseSummary(
            @AuthenticationPrincipal User user
    ) {

        return summaryService.getCategoryExpenseSummary(user);
    }
}