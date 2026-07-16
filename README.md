1. build.gradle
        ↓
2. application.yml
        ↓
① User CRUD
        ↓
② SecurityConfig
        ↓
③ Login API
        ↓
④ JWT発行
        ↓
⑤ JWT Filter
        ↓
⑥ TodoとUser紐付け
        ↓
⑦ Todo認可
        ↓
⑧ Refresh Token
        ↓
⑨ Exception処理
        ↓
⑩ Test追加


User.java
   ↓
UserRepository.java
   ↓
UserRequest.java
   ↓
UserResponse.java
   ↓
UserService.java
   ↓
UserController.java


① LoginRequest.java
        ↓
② LoginResponse.java
        ↓
③ JwtProvider.java
        ↓
④ AuthService.java
        ↓
⑤ AuthController.java
        ↓
⑥ JwtAuthenticationFilter.java


Todo.java            ← 必須
TodoRepository.java  ← 必須
TodoService.java     ← 必須
TodoController.java  ← 少し修正
TodoRequest.java     ← 不要
TodoResponse.java    ← 場合による
TodoApplication.java ← 不要
application.yml      ← JWT設定を追加
V4__add_user_id_to_todos.sql ← 必須

https://todo-app-bakkuendo.onrender.com
https://todo-app-furontoendo.onrender.com