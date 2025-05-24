# GSITM-Bank-Intranet
### 소개

**사내 직원들이 고객, 상품, 일정을 통합 관리할 수 있는 금융사 내부 인트라넷 시스템 구축**
- **진행 기간**: 2024.11.12 - 12.03
- **팀 구성**: 9인 (풀스택)
- **주요 타겟**: 금융사 내부 직원, 관리자

### 🛠 기술 스택

- **Frontend**: Thymeleaf, HTML/CSS, JavaScript, jQuery, Bootstrap
- **Backend**: Java, Spring Boot, MyBatis, AJAX, RestTemplate, Jsoup
- **Database**: PostgreSQL (ERD 설계 참여)
- **Infra/External**: AWS EC2 S3, 공공데이터 API, 네이버 뉴스 크롤링
- **Tool**: IntelliJ, DBeaver, GitHub, Figma

### **🧱 주요 기능 요약**

- 메인 페이지에서 실시간 뉴스와 지수, 고객 분석 차트를 제공해 주요 경제 흐름과 매출 데이터를 한눈에 파악할 수 있도록 했으며, 금융상품 프로모션 관리, 고객·이벤트 연계 마케팅, 직원별 고객 관리, 사내 일정 스케줄링, 공지·게시판을 통한 내부 커뮤니케이션까지 통합 지원하는 인트라넷 시스템입니다.

### **👩‍💻 담당 기능 요약**

🔹 **Fullstack 개발 (프론트엔드 + 백엔드 + DB 설계 일부 참여)**

- 외부 API를 연동하여 메인 대시보드 구성
- 게시판 CRUD 구현 (파일 첨부 최대 5개, S3 연동)
- 북마크 기능 구현
- 고객 매출, 성별/연령 통계 차트 시각화 (chart.js)
