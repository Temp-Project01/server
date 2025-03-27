document.addEventListener("DOMContentLoaded", function () {
    // DOM 요소 참조 영역
    // ----------------------------------------------------

    // 신고관리 필터 요소 선택
    const companyInquiryStatusFilter = document.querySelector(".company-inquiry-status-filter-status-filter"); // 상태 필터 드롭다운
    const companyInquiryDateFilter = document.querySelector(".company-inquiry-date-filter-date-filter"); // 기간 필터 드롭다운
    const companyInquirySearchInput = document.querySelector(".company-inquiry-search-input-search-input"); // 검색어 입력창
    const companyInquirySearchBtn = document.querySelector(".company-inquiry-search-btn"); // 검색 버튼

    // 필수 DOM 요소 존재 검증
    //   if (
    //     !reportStatusFilter ||
    //     !reportDateFilter ||
    //     !reportSearchInput ||
    //     !reportSearchBtn
    //   ) {
    //     console.error("신고관리 필터 초기화 실패: 필수 DOM 요소 누락");
    //     return;
    //   }

    // 유틸리티 함수 정의 영역
    // ----------------------------------------------------

    // 검색어 유효성 검사 및 검색 실행
    function companyInquirySearch(searchText) {
        // 검색어 입력 여부 확인
        if (!searchText) {
            alert("검색어를 입력해주세요.");
            return;
        }

        // 검색 실행 (추후 서버 연동)
        console.log("신고관리 검색어:", searchText);
    }

    // 필터 초기화 함수
    function initializeFilters() {
        companyInquiryStatusFilter.value = "all"; // 상태 필터 '전체'로 초기화
        companyInquiryDateFilter.value = "week"; // 기간 필터 '1주일'로 초기화
        companyInquirySearchInput.value = ""; // 검색어 입력창 비우기
        console.log("신고관리 필터 초기화 완료");
    }

    // 이벤트 리스너 설정 영역
    // ----------------------------------------------------

    // 상태 필터 변경 이벤트
    // companyInquiryStatusFilter.addEventListener("change", function () {
    //     const selectedStatus = this.value;
    //     // 선택된 상태값에 따른 한글 상태 매핑
    //     console.log(
    //         "신고관리 상태값:",
    //         selectedStatus === "hold"
    //             ? "보류"
    //             : selectedStatus === "pending"
    //             ? "처리중"
    //             : selectedStatus === "false"
    //             ? "허위신고"
    //             : selectedStatus === "completed"
    //             ? "처리완료"
    //             : "전체"
    //     );
    // });

    // 기간 필터 변경 이벤트
    // companyInquiryDateFilter.addEventListener("change", function () {
    //     const selectedDate = this.value;
    //     console.log("신고관리 기간 필터:", selectedDate);
    // });

    // 검색 버튼 클릭 이벤트
    // companyInquirySearchBtn.addEventListener("click", function () {
    //     const searchText = companyInquirySearchInput.value.trim();
    //     companyInquirySearch(searchText);
    // });

    // 검색어 입력창 엔터키 이벤트
    // companyInquirySearchInput.addEventListener("keypress", function (e) {
    //     if (e.key === "Enter") {
    //         const searchText = this.value.trim();
    //         companyInquirySearch(searchText);
    //     }
    // });

    // 초기화 실행
    // ----------------------------------------------------
    initializeFilters(); // 필터 초기 상태 설정
    console.log("신고관리 필터 기능 초기화 완료");
});
