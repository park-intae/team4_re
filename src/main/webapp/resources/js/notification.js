document.addEventListener('DOMContentLoaded', (event) => {
    startSSE();
    loadNotifications();
    applyOverlayScrollbars();

   // alarm-conten 영역에 삭제 버튼 처리
     $('.alarm-content').on('click', '.delete-icon', function() {
        const notificationDiv = $(this).closest('.notification-item');
        const notificationId = notificationDiv.attr('id').replace('content-notification-', '');
        deleteNotification(notificationId, notificationDiv);
    });
});

function loadNotifications() {
    $.ajax({
        url: '/api/notifications',
        type: 'GET',
        success: function(notifications) {
            notifications.forEach(function(notification) {
                if (!document.getElementById('content-notification-' + notification.id)) {
                    addNotificationToContent(notification);
                }
            });
        },
        error: function(error) {
            console.error("알림 데이터를 불러오는데 실패", error);
        }
    });
}

function addNotificationToContent(notification, isInitialLoad = false) {
    
      var div = $('<div></div>').addClass('notification-item').text(notification.content);
    div.attr('id', 'content-notification-' + notification.id);
    
    // 삭제 버튼 추가
    var deleteButton = $('<i class="fa-solid fa-trash" style="color: #000000;"></i>').addClass('delete-button');
    deleteButton.on('click', function() {
        deleteNotification(notification.id, div);
    });

    div.append(deleteButton);
    
    $('.alarm-content').prepend(div);
}

let eventSource;

function startSSE() {
    eventSource = new EventSource('/connect');

    eventSource.onopen = function(event) {
        // 연결이 열릴 때의 로직
    };

       
    eventSource.onmessage = function(event) {
        try {
            const data = JSON.parse(event.data);
            displayNotificationInList(data); // 우측 상단 알림 목록에 알림 표시
            addNotificationToContent(data); // 알림을 페이지에 동적으로 추가
        } catch (error) {
            console.error('JSON parsing error:', error);
        }
    };

    eventSource.onerror = function() {
        console.error('SSE 연결 오류 발생. 재연결 시도.');
        eventSource.close();
        setTimeout(startSSE, 5000);
    };
}

// 우측 상단 알림 목록에 알림 표시
function displayNotificationInList(data) {
    if (!data || !data.content) return;

    const notificationList = document.getElementById('notificationList');
    const newNotification = document.createElement('li');
    newNotification.textContent = data.content;
    notificationList.appendChild(newNotification);

    // 알림이 추가될 때마다 알림 목록 표시
    notificationList.style.display = 'block';

    // 일정 시간 후에 알림 자동으로 숨기기 (옵션)
    setTimeout(function() {
        notificationList.style.display = 'none';
    }, 7000);

    // 기존에 설정된 7초 후 알림 삭제 로직 유지
    setTimeout(function() {
        notificationList.removeChild(newNotification);
    }, 7000);
}


function toggleNotifications() {
    const notificationList = document.getElementById('notificationList');
    notificationList.style.display = notificationList.style.display === 'none' ? 'block' : 'none';
}



// 알림 삭제 함수
function deleteNotification(notificationId, div) {
    $.ajax({
        url: '/api/deleteNotification/' + notificationId,
        type: 'POST',
        success: function(result) {
            div.remove();
        },
        error: function(error) {
            console.error("알림 삭제 실패", error);
        }
    });
}



function applyOverlayScrollbars() {
    OverlayScrollbars(document.querySelectorAll('.alarm-content'), {});
}

$(document).ready(function() {
    loadNotifications();
});
