document.addEventListener('DOMContentLoaded', function() {
    var { OverlayScrollbars } = OverlayScrollbarsGlobal;

    var modalContent = document.querySelector('.modal-content');
    if (modalContent) {
        OverlayScrollbars(modalContent, {
        });
    }
});