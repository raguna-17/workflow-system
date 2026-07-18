package kakeibo.transaction;

import kakeibo.category.Category;
import kakeibo.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 金額
     */
    @Column(nullable = false)
    private Long amount;

    /**
     * メモ
     */
    @Column
    private String description;

    /**
     * 取引日
     */
    @Column(nullable = false)
    private LocalDate transactionDate;

    /**
     * 登録ユーザー
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * カテゴリ
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * 作成日時
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;


    public Transaction(
            Long amount,
            String description,
            LocalDate transactionDate,
            User user,
            Category category
    ) {
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.user = user;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }
}