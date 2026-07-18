package kakeibo.summary;

import kakeibo.transaction.Transaction;
import kakeibo.transaction.TransactionRepository;
import kakeibo.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SummaryService {

    private final TransactionRepository transactionRepository;


    public SummaryService(
            TransactionRepository transactionRepository
    ) {
        this.transactionRepository = transactionRepository;
    }


    /**
     * カテゴリ別支出集計
     */
    @Transactional(readOnly = true)
    public SummaryResponse getCategoryExpenseSummary(
            User user
    ) {

        List<Transaction> transactions =
                transactionRepository.findByUser(user);


        Map<String, Long> categoryExpenses =
                transactions.stream()

                        // 支出だけ対象
                        .filter(transaction ->
                                transaction.getCategory()
                                        .getType()
                                        .name()
                                        .equals("EXPENSE")
                        )

                        // カテゴリごとに合計
                        .collect(Collectors.groupingBy(
                                transaction ->
                                        transaction.getCategory()
                                                .getName(),

                                Collectors.summingLong(
                                        Transaction::getAmount
                                )
                        ));


        List<SummaryResponse.CategoryExpense> result =
                categoryExpenses.entrySet()
                        .stream()
                        .map(entry ->
                                new SummaryResponse.CategoryExpense(
                                        entry.getKey(),
                                        entry.getValue()
                                )
                        )
                        .toList();


        return new SummaryResponse(result);
    }


    public static class SummaryResponse {

        private List<CategoryExpense> categoryExpenses;


        public SummaryResponse(
                List<CategoryExpense> categoryExpenses
        ) {
            this.categoryExpenses = categoryExpenses;
        }


        public List<CategoryExpense> getCategoryExpenses() {
            return categoryExpenses;
        }


        public static class CategoryExpense {

            private String categoryName;

            private Long amount;


            public CategoryExpense(
                    String categoryName,
                    Long amount
            ) {
                this.categoryName = categoryName;
                this.amount = amount;
            }


            public String getCategoryName() {
                return categoryName;
            }


            public Long getAmount() {
                return amount;
            }
        }
    }
}