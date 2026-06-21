# LMS 學習管理系統 | 前後端分離系統（技術銜接與協作練習專案）

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.x-green.svg)]()
[![Vue 3](https://img.shields.io/badge/Vue-3.x-42b883.svg)]()
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)]()

## 📌 專案緣起與個人背景
本專案是我在學與退伍前後，為了深度銜接業界實務技術所開發的「前後端分離」實戰專案。系統從零建構 Spring Boot 後端 API，並配置 Vue 3 + Vue Router 的前端架構。

作為資訊管理學系的學生，我深知現代軟體開發極度看重前後端的高效協作。因此，我希望自己**不僅專精後端邏輯，也能具備良好的跨端溝通與前後端分離協作視野**。在整體開發與編譯除錯的過程中，我堅持手敲並徹底理解每行代碼的運作邏輯，藉此全面熟悉單頁應用（SPA）的開發模式與前後端 JSON 資料的對接與交互處理。

---

## 🛠️ 開發環境與技術棧 (Tech Stack)

本專案為前後端分離架構，本人獨立練習了前端與後端的技術整合與數據通訊：

### 🧱 後端技術 (Backend)
- **核心框架**：Java 8+ / Spring Boot / Spring MVC
- **專案管理**：Maven
- **資料持久層**：MySQL / MyBatis
- **通訊配置**：CORS (跨域資源共享配置)
- **開發工具**：IntelliJ IDEA / Apifox / DataGrip

### 🎨 前端技術 (Frontend)
- **核心框架**：Vue.js 3 (採用現代化組合式 API 基礎開發)
- **路由管理**：Vue Router (實作單頁應用 SPA 嵌套路由配置與重定向)
- **非同步通訊**：Axios (封裝 HTTP 請求，實作與後端 API 的非同步資料讀取)
- **組件庫**：Element Plus (用於快速建構乾淨流暢的管理後台介面)
- **建置工具**：Vite / VS Code

---

## 💡 開發過程與核心收穫

### 1. 體驗前後端分離的協作與 API 對接
- **實作方式**：後端專注提供標準的 RESTful API 輸出 JSON 數據，前端利用 Axios 呼叫 API，將開課資訊、學生名單、日誌數據等動態渲染到網頁上。
- **核心收穫**：在自主串接的過程中，深刻體驗並踩過不少**跨域問題 (CORS)** 的坑，並學會如何在 Spring Boot 後端配置全域 CORS 設定（如 `WebMvcConfigurer` 的跨域配置）。同時，我也練習了封裝 Axios 的基礎請求，統一處理後端回傳的常見狀態碼，建立起良好的前後端對接與例外處置觀念。

### 2. 運用 Vue Router 實作後台嵌套路由架構 (Nested Routes)
- **實作方式**：使用 Vue 3 (createRouter) 建立單頁應用 (SPA) 路由。設計一套 `LayoutView` (佈局組件) 作為管理後台的整體外殼（包含左側固定導覽列與頂部狀態列），並利用 `children` 嵌套路由機制，將班級管理 (clazz)、學生管理 (stu)、部門員工管理 (dept/emp) 以及操作日誌 (log) 動態渲染於佈局主體中。此外，亦實作了基礎的路由重定向 (redirect)，確保使用者訪問根路徑 `/` 時能自動平滑導向首頁 `/index`。
- **核心收穫**：深入理解了現代前端框架的「組件複用」核心思想。透過嵌套路由，不需要在每個頁面重複撰寫導覽列的程式碼，大幅提升了前端代碼的維護性。同時，也學會了如何切分「需要後台佈局的頁面」與「獨立的外殼頁面（如 `LoginView` 登入頁）」。

### 3. 前端組件化開發與版面實踐
- **實作方式**：搭配 Element Plus 的導覽列、表格、對話框（Dialog）等組件，快速搭建出乾淨、直覺的學習管理後台。
- **核心收穫**：練習將複雜的網頁拆解成多個可複用的 Vue 組件，提升前端程式碼的維護性。**這段經歷讓我更能站在前端工程師的角度去思考：「後端 API 的資料格式應該怎麼設計，前端才最好串接、最不容易發生例外錯誤。」**

---

## 📐 專案目錄結構 (Architecture)

本專案將前端與後端代碼分開管理，以確保前後端分離架構的清晰度：

```text
├── lms-backend/       # 後端 Spring Boot 專案
│   ├── config/        # 跨域等系統配置
│   ├── controller/    # RESTful API 控制層
│   ├── service/       # 業務邏輯層
│   └── mapper/        # MyBatis 資料持久層
│
└── lms-frontend/      # 前端 Vue 3 專案
    ├── src/
    │   ├── views/     # 頁面組件 (LayoutView, LoginView 等)
    │   ├── router/    # Vue Router 嵌套路由配置
    │   └── assets/    # 靜態資源
    └── vite.config.js # Vite 建置配置
