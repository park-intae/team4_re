document.addEventListener('DOMContentLoaded', function() {
    var { OverlayScrollbars } = OverlayScrollbarsGlobal;

    var modalContent = document.querySelector('.modal-content');
    if (modalContent) {
        OverlayScrollbars(modalContent, {
            // OverlayScrollbars 설정을 여기에 넣습니다.
            // 예시: 스크롤바 스타일, 동작 옵션 등
        });
    }
});