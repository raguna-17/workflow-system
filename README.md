① KakeiboApplication

② User
   ・Role
   ・User
   ・UserRepository
   ・UserService

③ common
   ・CustomUserDetailsService

④ auth
   ・JwtProvider
   ・JwtAuthenticationFilter

⑤ common
   ・SecurityConfig

⑥ Auth
   ・AuthService
   ・AuthController

⑦ Category

⑧ Transaction

⑨ Summary





React
   │
   ▼
Bearer Tokenを送る
   │
   ▼
JwtAuthenticationFilter
   │
   ▼
JwtProvider
   │
   ▼
メールアドレス取得
   │
   ▼
UserRepository
   │
   ▼
User取得
   │
   ▼
Authentication作成
   │
   ▼
SecurityContextへ保存
   │
   ▼
TransactionController
   │
   ▼
TransactionService
   │
   ▼
TransactionRepository
   │
   ▼
Database



# 家計簿アプリ

Spring Boot + React + PostgreSQL + Docker を用いて開発した家計簿アプリです。

JWT認証を利用したログイン機能を備え、収入・支出の管理やカテゴリ別支出の集計を行うことができます。

---

# 使用技術

## バックエンド

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* JWT
* Flyway
* PostgreSQL

## フロントエンド

* React
* Vite
* React Router
* Axios
* Recharts

## インフラ

* Docker
* Docker Compose

---

# 主な機能

## 認証

* ユーザー登録
* ログイン
* JWT認証
* アクセストークン・リフレッシュトークン発行

---

## 取引管理

* 取引一覧取得
* 取引登録
* 取引削除
* ユーザーごとの取引管理

---

## カテゴリ

* 支出カテゴリ取得
* 収入カテゴリ取得
* 全カテゴリ取得

初期データは Flyway により登録されます。

### 支出

* 食費
* 日用品
* 交通費
* 家賃
* 光熱費
* 通信費
* 医療費
* 娯楽
* その他

### 収入

* 給料
* 賞与
* 副業
* その他

---

## 集計

カテゴリ別支出を集計できます。

フロントエンドでは Recharts を利用して円グラフで表示しています。

---

# API

## 認証

| Method | Endpoint       |
| ------ | -------------- |
| POST   | /auth/register |
| POST   | /auth/login    |

---

## カテゴリ

| Method | Endpoint                 |
| ------ | ------------------------ |
| GET    | /categories              |
| GET    | /categories?type=EXPENSE |
| GET    | /categories?type=INCOME  |

---

## 取引

| Method | Endpoint           |
| ------ | ------------------ |
| GET    | /transactions      |
| POST   | /transactions      |
| DELETE | /transactions/{id} |

---

## 集計

| Method | Endpoint          |
| ------ | ----------------- |
| GET    | /summary/expenses |

---


# データベース

Flyway によりマイグレーションを管理しています。

```text
V1__create_users.sql
V2__create_categories.sql
V3__create_transactions.sql
V4__insert_categories.sql
```

---

# セットアップ

## バックエンド

```bash
docker compose up --build
```

バックエンド

```
http://localhost:8080
```

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## フロントエンド

```bash
npm install
npm run dev
```

```
http://localhost:5173
```

---

# 今後の改善

* 取引編集機能
* 月別・年別集計
* カテゴリ追加・編集・削除
* 検索・フィルタ機能
* ページネーション
* バリデーション強化
* ダッシュボードの充実
* レスポンシブデザイン対応

---

# 学習ポイント

このアプリでは以下の技術を実践しました。

* REST API設計
* JWT認証
* Spring Security
* JPAによる永続化
* FlywayによるDBマイグレーション
* Dockerによる開発環境構築
* ReactとAxiosによるAPI通信
* React Routerによる画面遷移
* Rechartsによるデータ可視化
* バックエンドとフロントエンドの連携



https://todo-app-bakkuendo.onrender.com
https://todo-app-furontoendo.onrender.com