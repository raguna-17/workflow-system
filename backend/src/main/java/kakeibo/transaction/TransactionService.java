package kakeibo.transaction;

import kakeibo.category.Category;
import kakeibo.category.CategoryRepository;
import kakeibo.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;


    public TransactionService(
            TransactionRepository transactionRepository,
            CategoryRepository categoryRepository
    ) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }


    /**
     * 取引一覧取得
     */
    @Transactional(readOnly = true)
    public List<TransactionDto.Response> getTransactions(User user) {

        return transactionRepository.findByUser(user)
                .stream()
                .map(transaction -> new TransactionDto.Response(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getDescription(),
                        transaction.getTransactionDate(),
                        transaction.getCategory().getId(),
                        transaction.getCategory().getName(),
                        transaction.getCategory().getType()
                ))
                .toList();
    }


    /**
     * 取引登録
     */
    @Transactional
    public TransactionDto.Response createTransaction(
            TransactionDto.Request request,
            User user
    ) {

        Category category =
                categoryRepository.findById(request.getCategoryId())
                        .orElseThrow(() ->
                                new RuntimeException("カテゴリが存在しません")
                        );


        Transaction transaction = new Transaction(
                request.getAmount(),
                request.getDescription(),
                request.getTransactionDate(),
                user,
                category
        );


        Transaction saved =
                transactionRepository.save(transaction);


        return new TransactionDto.Response(
                saved.getId(),
                saved.getAmount(),
                saved.getDescription(),
                saved.getTransactionDate(),
                saved.getCategory().getId(),
                saved.getCategory().getName(),
                saved.getCategory().getType()
        );
    }


    /**
     * 取引削除
     */
    @Transactional
    public void deleteTransaction(
            Long id,
            User user
    ) {

        Transaction transaction =
                transactionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("取引が存在しません")
                        );


        if (!transaction.getUser().getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "権限がありません"
            );
        }


        transactionRepository.delete(transaction);
    }
}