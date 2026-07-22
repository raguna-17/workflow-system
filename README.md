# Workflow System

社内申請・承認業務を効率化するWebワークフローシステムです。

紙やメールで行われていた申請・承認業務をWeb化し、申請状況の可視化、承認経路管理、権限制御を実現しています。

## 概要

企業内の申請業務では、以下のような課題があります。

- 承認状況の確認に時間がかかる
- 承認履歴の管理が難しい
- 承認経路の変更に手間がかかる

これらの課題を解決するため、申請から承認完了までを一元管理できるワークフローシステムを開発しました。

## デモ

https://www.youtube.com/watch?v=-LqqAjInjrA


# 主な機能

## 認証・認可

JWT認証によるユーザー管理とRoleベースのアクセス制御を実装しています。

- ユーザー登録
- ログイン認証
- JWT Token発行
- Bearer認証
- 権限管理

### 権限

|Role|権限|
|-|-|
|USER|申請作成、自分の申請確認、承認処理|
|ADMIN|ユーザー管理、Workflow管理、承認設定|


## 申請管理

申請作成から承認完了までの状態管理を行います。

### 機能

- 申請作成
- 申請一覧表示
- 申請詳細確認
- 申請状態管理

### 状態管理

|状態|説明|
|-|-|
|SUBMITTED|申請受付|
|IN_PROGRESS|承認処理中|
|APPROVED|承認完了|
|REJECTED|却下|


## ワークフロー管理

管理者が承認経路を設定できます。

WorkflowとWorkflowStepを分離することで、複数段階の承認フローに対応しています。

例:

申請
↓
課長承認
↓
部長承認
↓
完了


### 機能

- Workflow作成
- Workflow一覧取得
- 承認Step設定
- 承認者設定


## 承認管理

申請に対する承認処理を管理します。

### 機能

- 承認待ち一覧取得
- 承認処理
- 却下処理
- コメント登録
- 承認履歴管理


## ダッシュボード

申請・承認状況を集計して表示します。

表示項目:

- 申請総数
- 承認済み件数
- 却下件数
- 承認待ち件数


# 技術構成

## Backend

|技術|用途|
|-|-|
|Java 21|Backend|
|Spring Boot 3.5|REST API|
|Spring Security 6|認証・認可|
|Spring Data JPA|DBアクセス|
|PostgreSQL|Database|
|Flyway|Migration管理|
|JWT|認証|
|Gradle|Build|


## Frontend

|技術|用途|
|-|-|
|React|UI|
|Vite|開発環境|
|React Router|画面遷移|
|Axios|API通信|


## Infrastructure

- Docker
- Docker Compose


# 設計

## レイヤードアーキテクチャ

責務分離を目的として以下の構成を採用しています。

Controller
↓
Service
↓
Repository
↓
Database


### Service層

業務ロジックを担当しています。

- 承認状態管理
- 権限チェック
- Workflow制御
- 申請処理


### DTO利用

Entityを直接API公開せずDTOを利用しています。

目的:

- DB構造とAPI仕様の分離
- 不要なデータ公開防止
- 保守性向上


### Flyway Migration

DB変更履歴をMigrationファイルで管理しています。

環境ごとの差異を防ぎ、変更履歴を追跡できる構成にしています。


# API

REST APIとして各機能を提供しています。

|Module|内容|
|-|-|
|Authentication|認証|
|User|ユーザー管理|
|Workflow|承認経路管理|
|Request|申請管理|
|Approval|承認処理|
|Dashboard|集計表示|


Swagger UI:

http://localhost:8080/swagger-ui/index.html


# 起動方法

## Backend

```bash
./gradlew bootRun
Frontend
npm install
npm run dev
```
必要環境:

- Java 21
- PostgreSQL
- Node.js
- Docker


## テスト

- JUnit 5・Mockitoによるユニットテストを実装
- Request・Approvalを中心に正常系・異常系を検証
- JaCoCoでコードカバレッジを測定


## 今後の改善予定
- Refresh Token対応
- 承認期限管理
- 通知機能
- ファイル添付
- 操作ログ管理