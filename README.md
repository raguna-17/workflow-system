User
 ↓
Workflow「どういう順番で承認するか」を管理します。
 ↓
Requestここが業務データの中心です。
 ↓
Approval実際の承認履歴
 ↓
 ↓
Dashboard最後に見る画面。


 PS C:\Users\admin\projects\workflow-system\backend> tree /f
フォルダー パスの一覧
ボリューム シリアル番号は 30F0-F15D です
C:.
│  .dockerignore
│  build.gradle
│  docker-compose.yml
│  Dockerfile
│  gradlew
│  gradlew.bat
│  
│          
└─src
    ├─main
    │  ├─java
    │  │  └─workflow
    │  │      │  WorkflowApplication.java
    │  │      │  
    │  │      ├─auth
    │  │      │      AuthController.java
    │  │      │      AuthDto.java
    │  │      │      AuthService.java
    │  │      │      
    │  │      ├─domains
    │  │      │  ├─approval
    │  │      │  │      Approval.java
    │  │      │  │      ApprovalController.java
    │  │      │  │      ApprovalDto.java
    │  │      │  │      ApprovalRepository.java
    │  │      │  │      ApprovalService.java
    │  │      │  │      ApprovalStatus.java
    │  │      │  │      
    │  │      │  ├─dashboard
    │  │      │  │      DashboardController.java
    │  │      │  │      DashboardService.java
    │  │      │  │      
    │  │      │  ├─request
    │  │      │  │      Request.java
    │  │      │  │      RequestController.java
    │  │      │  │      RequestDto.java
    │  │      │  │      RequestRepository.java
    │  │      │  │      RequestService.java
    │  │      │  │      RequestStatus.java
    │  │      │  │      
    │  │      │  ├─user
    │  │      │  │      Role.java
    │  │      │  │      User.java
    │  │      │  │      UserController.java
    │  │      │  │      UserDto.java
    │  │      │  │      UserRepository.java
    │  │      │  │      UserService.java
    │  │      │  │      
    │  │      │  └─workflow
    │  │      │          Workflow.java
    │  │      │          WorkflowController.java
    │  │      │          WorkflowDto.java
    │  │      │          WorkflowRepository.java
    │  │      │          WorkflowService.java
    │  │      │          WorkflowStep.java
    │  │      │          
    │  │      └─security
    │  │              CustomUserDetailsService.java
    │  │              JwtAuthenticationFilter.java
    │  │              JwtProvider.java
    │  │              SecurityConfig.java
    │  │              
    │  └─resources
    │      │  application.yml
    │      │  
    │      └─db
    │          └─migration
    │                  V1__create_users.sql
    │                  V2__create_workflows.sql
    │                  V3__create_requests.sql
    │                  V4__create_approvals.sql
    │                  
 
C:\USERS\ADMIN\PROJECTS\WORKFLOW-SYSTEM\FRONTEND\SRC
│  App.css
│  App.jsx
│  index.css
│  main.jsx
│  
├─assets
│      hero.png
│      react.svg
│      vite.svg
│      
├─components
│      Button.jsx
│      Input.jsx
│      Modal.jsx
│      Spinner.jsx
│      
├─features
│  ├─approval
│  │      api.js
│  │      ApprovalDetail.jsx
│  │      ApprovalPage.jsx
│  │      
│  ├─auth
│  │      api.js
│  │      LoginPage.jsx
│  │      
│  │      
│  ├─dashboard
│  │      api.js
│  │      DashboardPage.jsx
│  │      
│  ├─request
│  │      api.js
│  │      RequestDetail.jsx
│  │      RequestForm.jsx
│  │      RequestPage.jsx
│  │      
│  ├─user
│  │      api.js
          RegisterPage.jsx
│  │      UserForm.jsx
│  │      UserPage.jsx
│  │      
│  └─workflow
│          api.js
│          WorkflowDetail.jsx
│          WorkflowForm.jsx
│          WorkflowPage.jsx
│          
├─layouts
│      Header.jsx
│      Layout.jsx
│      Sidebar.jsx
│      
└─lib
        axios.js
        






# Workflow System

社内申請・承認業務をWeb上で管理する業務向けワークフローシステムです。

紙やメールで行われていた申請・承認業務をデジタル化し、申請状況の可視化、承認経路管理、権限制御を実現します。

## Overview

本システムでは以下の業務課題を解決します。

- 承認状況の把握が困難
- 承認確認作業の発生
- 承認履歴の管理不足
- 部署や役職変更による承認ルート変更の難しさ

Workflow設定とRole管理により、柔軟な承認フローを構築できます。

---

# Features

## Authentication / Authorization

JWT認証によるユーザー管理を実装しています。

### Features

- ログイン認証
- Access Token発行
- JWT Bearer認証
- Roleベースアクセス制御

### Roles

|Role|権限|
|-|-|
|USER|申請作成、自分の申請確認、承認処理|
|ADMIN|Workflow管理、ユーザー管理、承認設定|

---

## Request Management

申請から承認完了までの状態管理を行います。

### Features

- 申請作成
- 申請一覧取得
- 申請詳細確認
- 申請状態管理

### Status

|Status|Description|
|-|-|
|SUBMITTED|申請中|
|APPROVED|承認完了|
|REJECTED|却下|

---

## Workflow Management

管理者が承認経路を設定できます。

Workflowは複数の承認Stepで構成されます。


申請
↓
課長承認
↓
部長承認
↓
完了


構成:


Workflow
└ WorkflowStep
└ Approver(User)


### Features

- Workflow作成
- Workflow一覧取得
- Workflow詳細確認
- 承認Step設定

---

## Approval Management

申請に対する承認処理を管理します。

Features:

- 承認待ち確認
- 承認処理
- 却下処理
- 承認コメント登録
- 承認履歴管理

---

## Dashboard

申請・承認状況を可視化します。

表示内容:

- 申請総数
- 承認済み件数
- 却下件数
- 承認待ち件数

---

# Technical Highlights

## Layered Architecture

責務を分離したレイヤードアーキテクチャを採用しています。


Controller
↓
Service
↓
Repository
↓
Database


### Controller

HTTPリクエスト処理を担当。

### Service

業務ロジックを担当。

- 承認ルール管理
- 権限チェック
- 状態変更制御

### Repository

データアクセスを担当。

---

## Design Decisions

### DTO利用

Entityを直接API公開せずDTOを利用し、
DB構造とAPI仕様を分離しています。

### Flyway Migration

DB変更履歴を管理し、
環境ごとの差異を防止しています。

### Role Based Authorization

ユーザー権限ごとに操作範囲を制御しています。

---

# Tech Stack

## Backend

|Technology|Purpose|
|-|-|
|Java 21|Backend|
|Spring Boot 3.5|REST API|
|Spring Security 6|Authentication / Authorization|
|Spring Data JPA|Database Access|
|PostgreSQL|Database|
|Flyway|Migration|
|JWT|Authentication|
|Gradle|Build|

## Frontend

|Technology|Purpose|
|-|-|
|React|UI|
|Vite|Development|
|React Router|Routing|
|Axios|API Communication|

## Infrastructure

- Docker
- Docker Compose

---

# API

REST APIとして各機能を提供しています。

主要機能:

|Module|Description|
|-|-|
|Authentication|JWT認証|
|User|ユーザー管理|
|Workflow|承認フロー管理|
|Request|申請管理|
|Approval|承認処理|
|Dashboard|集計表示|

Swagger UI:


http://localhost:8080/swagger-ui/index.html


---

# Database

Flywayによるマイグレーション管理を採用しています。

Migration:


src/main/resources/db/migration


Example:


V1__create_users.sql
V2__create_workflows.sql
V3__create_requests.sql
V4__create_approvals.sql


---

# Setup

## Backend

```bash
./gradlew bootRun
Frontend
npm install
npm run dev

Required:

Java 21
PostgreSQL
Node.js
Docker
Future Improvements
Refresh Token対応
承認期限管理
通知機能
ファイル添付
操作ログ管理