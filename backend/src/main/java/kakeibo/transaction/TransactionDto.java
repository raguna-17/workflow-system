package kakeibo.transaction;

import kakeibo.category.CategoryType;

import java.time.LocalDate;

public class TransactionDto {

    /**
     * 登録・更新リクエスト
     */
    public static class Request {

        private Long amount;

        private String description;

        private LocalDate transactionDate;

        private Long categoryId;


        public Request() {
        }


        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }


        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


        public LocalDate getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(LocalDate transactionDate) {
            this.transactionDate = transactionDate;
        }


        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }
    }


    /**
     * レスポンス
     */
    public static class Response {

        private Long id;

        private Long amount;

        private String description;

        private LocalDate transactionDate;

        private Long categoryId;

        private String categoryName;

        private CategoryType categoryType;


        public Response(
                Long id,
                Long amount,
                String description,
                LocalDate transactionDate,
                Long categoryId,
                String categoryName,
                CategoryType categoryType
        ) {
            this.id = id;
            this.amount = amount;
            this.description = description;
            this.transactionDate = transactionDate;
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.categoryType = categoryType;
        }


        public Long getId() {
            return id;
        }

        public Long getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getTransactionDate() {
            return transactionDate;
        }

        public Long getCategoryId() {
            return categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public CategoryType getCategoryType() {
            return categoryType;
        }
    }
}