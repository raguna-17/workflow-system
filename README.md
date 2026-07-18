# 家計簿アプリ

Spring Boot + React + PostgreSQL + Docker を使用して開発した家計簿管理アプリです。

JWT認証によるユーザー管理を実装し、ユーザーごとの収入・支出管理、カテゴリ別支出の集計、データ可視化を行うことができます。

バックエンドにはSpring BootによるREST API、フロントエンドにはReactを採用し、フロントエンドとバックエンドを分離した構成で開発しています。

---

# 使用技術

## Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* JWT認証
* Flyway
* PostgreSQL

## Frontend

* React
* Vite
* React Router
* Axios
* Recharts

## Infrastructure

* Docker
* Docker Compose

---

# 主な機能

## 認証機能

* ユーザー登録
* ログイン
* JWTによる認証
* アクセストークン発行
* リフレッシュトークン発行

認証済みユーザーのみ、自身の家計データを操作できます。

---

## 取引管理

収入・支出の登録、取得、削除を行うことができます。

機能:

* 取引一覧取得
* 取引登録
* 取引削除
* ユーザー単位でのデータ管理

---

## カテゴリ管理

収入・支出カテゴリを管理しています。

取得可能なカテゴリ:

### 支出カテゴリ

* 食費
* 日用品
* 交通費
* 家賃
* 光熱費
* 通信費
* 医療費
* 娯楽
* その他

### 収入カテゴリ

* 給料
* 賞与
* 副業
* その他

初期カテゴリデータはFlywayによるマイグレーションで登録しています。

---

## 支出集計・可視化

カテゴリごとの支出額を集計し、Rechartsを利用して円グラフで表示します。

APIから取得した集計データをReact側でグラフ化することで、支出割合を視覚的に確認できます。

---

# API

## Authentication

| Method | Endpoint         |
| ------ | ---------------- |
| POST   | `/auth/register` |
| POST   | `/auth/login`    |

---

## Category

| Method | Endpoint                   |
| ------ | -------------------------- |
| GET    | `/categories`              |
| GET    | `/categories?type=EXPENSE` |
| GET    | `/categories?type=INCOME`  |

---

## Transaction

| Method | Endpoint             |
| ------ | -------------------- |
| GET    | `/transactions`      |
| POST   | `/transactions`      |
| DELETE | `/transactions/{id}` |

---

## Summary

| Method | Endpoint            |
| ------ | ------------------- |
| GET    | `/summary/expenses` |

---

# Database

Flywayを利用してデータベースマイグレーションを管理しています。

Migration:

```text
V1__create_users.sql
V2__create_categories.sql
V3__create_transactions.sql
V4__insert_categories.sql
```

テーブル作成や初期データ投入を自動化しています。

---

# 開発環境セットアップ

## Backend

Docker Composeを利用して起動できます。

```bash
docker compose up --build
```

Backend:

```text
http://localhost:8080
```

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Frontend

```bash
npm install
npm run dev
```

Frontend:

```text
http://localhost:5173
```

---

# 開発で実践した内容

このアプリでは以下の技術を実際に利用しました。

* REST API設計
* Spring Securityによる認証・認可
* JWT認証
* Spring Data JPAによるデータ永続化
* FlywayによるDBマイグレーション管理
* PostgreSQLを利用したデータ管理
* Dockerによる開発環境構築
* ReactによるSPA開発
* AxiosによるAPI通信
* React Routerによる画面遷移
* Rechartsによるデータ可視化
* フロントエンド・バックエンド間の連携

---

# 今後の改善予定

* 取引編集機能
* 月別・年別集計
* カテゴリ追加・編集・削除
* 検索・フィルタ機能
* ページネーション対応
* 入力バリデーション強化
* ダッシュボード機能追加
* レスポンシブデザイン対応
