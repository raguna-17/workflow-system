package kakeibo.transaction;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kakeibo.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transactions")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {


    private final TransactionService transactionService;


    public TransactionController(
            TransactionService transactionService
    ) {
        this.transactionService = transactionService;
    }


    /**
     * 自分の取引一覧取得
     */
    @GetMapping
    public List<TransactionDto.Response> getTransactions(
            @AuthenticationPrincipal User user
    ) {

        return transactionService.getTransactions(user);
    }


    /**
     * 取引登録
     */
    @PostMapping
    public TransactionDto.Response createTransaction(
            @RequestBody TransactionDto.Request request,
            @AuthenticationPrincipal User user
    ) {

        return transactionService.createTransaction(
                request,
                user
        );
    }


    /**
     * 取引削除
     */
    @DeleteMapping("/{id}")
    public void deleteTransaction(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {

        transactionService.deleteTransaction(
                id,
                user
        );
    }
}