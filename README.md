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

## 概要

Workflow Systemは、社内申請・承認フローを管理するWebアプリケーションです。

ユーザーが申請を作成し、管理者が設定したWorkflowに基づいて承認処理を行います。

JWT認証によるユーザー認証を採用し、ユーザーのRole（権限）に応じて利用可能な機能を制御します。

---

# 主な機能

## 認証機能

JWTを利用した認証機能を提供します。

機能:
- ログイン認証
- Access Token発行
- JWT Bearer認証

ログイン:


POST /auth/login


認証が必要なAPI:


Authorization: Bearer {access_token}


Refresh Token対応は今後追加予定です。

---

## ユーザー管理

ユーザー情報を管理します。

機能:
- ユーザー登録
- ユーザー取得
- Role管理

Role:

### USER
- ログイン
- 申請作成
- 自分の申請確認
- 承認処理

### ADMIN
- Workflow作成
- 承認フロー設定
- ユーザー管理操作

---

## 申請機能（Request）

ユーザーが承認を依頼する申請を管理します。

機能:
- 申請作成
- 自分の申請一覧取得
- 申請詳細取得
- 申請状態管理

API:


POST /requests

GET /requests/my

GET /requests/{id}


申請状態:

| 状態 | 説明 |
|---|---|
| SUBMITTED | 申請済み |
| APPROVED | 承認完了 |
| REJECTED | 却下 |

---

## Workflow機能

承認ルートを管理します。

ADMINが申請に利用するWorkflowを作成します。

機能:
- Workflow作成
- Workflow一覧取得
- Workflow詳細取得
- Workflow Step管理

構成:


Workflow

└ WorkflowStep

  └ Approver(User)

例:


申請
↓
課長承認
↓
部長承認
↓
完了


---

## 承認機能（Approval）

申請に対する承認処理を管理します。

機能:
- 承認一覧取得
- 承認処理
- 却下処理
- 承認コメント管理

API:


GET /approvals/request/{requestId}

POST /approvals/{approvalId}/approve

POST /approvals/{approvalId}/reject


---

## Dashboard機能

ユーザーの申請状況や承認状況を表示します。

表示内容:
- 申請数
- 承認済み件数
- 却下件数
- 承認待ち件数

API:


GET /dashboard


---

# 技術構成

## Backend

| 技術 | 内容 |
|---|---|
| Java | 21 |
| Spring Boot | 3.5 |
| Spring Security | 6 |
| Spring Data JPA | 使用 |
| PostgreSQL | Database |
| Flyway | Migration管理 |
| JWT | jjwt 0.12.6 |
| Lombok | 1.18.38 |
| Gradle | Build Tool |

## Frontend

| 技術 | 内容 |
|---|---|
| React | Vite |
| React Router | ルーティング管理 |
| Axios | API通信 |
| JWT Bearer認証 | 認証方式 |

---

# セットアップ

## 必要環境

- Java 21
- PostgreSQL
- Node.js
- Gradle

---

# データベース設定

PostgreSQLにデータベースを作成します。

```sql
CREATE DATABASE workflow;

環境変数:

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/workflow

SPRING_DATASOURCE_USERNAME=postgres

SPRING_DATASOURCE_PASSWORD=password

JWT_SECRET=your-secret-key
起動方法
Backend

Windows:

./gradlew bootRun

ビルド確認:

./gradlew clean build
Frontend
npm install

npm run dev
API仕様

Swagger:

http://localhost:8080/swagger-ui/index.html

主要API:

Auth
POST /auth/login
User
POST /users

GET /users/{id}
Workflow
POST /workflows

GET /workflows

GET /workflows/{id}

POST /workflows/{workflowId}/steps
Request
POST /requests

GET /requests/my

GET /requests/{id}
Approval
GET /approvals/request/{requestId}

POST /approvals/{approvalId}/approve

POST /approvals/{approvalId}/reject
Dashboard
GET /dashboard
データベースマイグレーション

Flywayを利用しています。

配置場所:

src/main/resources/db/migration

命名規則:

V{番号}__{説明}.sql

例:

V1__create_users.sql

V2__create_workflows.sql

V3__create_requests.sql

V4__create_approvals.sql
開発ルール
Entity

データベーステーブルとの対応を管理します。

Repository

DBアクセス処理を担当します。

Service

ビジネスロジックを担当します。

Controller

HTTPリクエスト・レスポンス処理を担当します。

DTO

API入出力用データを管理します。

今後追加予定
Refresh Token管理
承認期限設定
通知機能
操作ログ
ファイル添付
部署単位の権限制御
ユーザー一覧管理API
Workflow編集・削除機能
承認履歴管理
承認待ち一覧API（自分が担当する承認一覧）