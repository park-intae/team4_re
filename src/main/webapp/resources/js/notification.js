let osInstance;

document.addEventListener('DOMContentLoaded', (event) => {
    startSSE();
    loadNotifications();
    loadNotificationsFromStorage(); // 페이지 로드 시 저장된 알림 로드
    applyOverlayScrollbars();

     $('.alarm-content').on('click', '.alarm-content .delete-icon', function() {
        const notificationDiv = $(this).closest('.notification-item');
        const notificationId = notificationDiv.attr('id').replace('content-notification-', '');
        deleteNotification(notificationId, notificationDiv);
    });

    OverlayScrollbars(document.querySelectorAll('.alarm-content'), {
        overflowBehavior: {
            x: "hidden",
            y: "scroll"
        },
        scrollbars: {
            autoHide: "never"
        }
    });
});

function loadNotifications() {
    $.ajax({
        url: '/api/notifications',
        type: 'GET',
        success: function(notifications) {
            notifications.forEach(function(notification) {
                var notificationId = 'content-notification-' + notification.id;
                if (!document.getElementById(notificationId)) {
                    addNotificationToContent(notification);
                }
            });
            updateAlarmContentHeight();
             if (osInstance) {
        osInstance.update();
    }
        },
        error: function(error) {
            console.error("알림 데이터를 불러오는데 실패", error);
        }
    });
}

function addNotificationToContent(notification, isInitialLoad = false) {
      var notificationId = 'content-notification-' + notification.id;
    if (document.getElementById(notificationId)) {
        // 이미 존재하는 알림은 추가하지 않음
        return;
    }

    var notificationDiv = $('<div></div>').addClass('notification-item');
    notificationDiv.attr('id', notificationId);
    var notificationText = $('<div></div>').text(notification.content).addClass('notification-text');
    notificationDiv.append(notificationText);

    var deleteButton = $('<i class="fa-solid fa-trash"></i>').addClass('delete-button');
    deleteButton.on('click', function() {
        deleteNotification(notification.id, notificationDiv);
    });
    notificationDiv.append(deleteButton);

    $('.alarm-content .os-content').append(notificationDiv);

    if (!isInitialLoad) {
        saveNotificationToStorage(notification);
    }
    
    osInstance.update();
    updateAlarmContentHeight();
}



function updateAlarmContentHeight() {
    var totalHeight = 0;
    $('.alarm-content .notification-item').each(function() {
        totalHeight += $(this).outerHeight(true);
    });

    var alarmContent = $('.alarm-content');
    alarmContent.css('height', totalHeight + 'px');

    if(totalHeight > alarmContent.outerHeight()) {
        alarmContent.css('overflow-y', 'scroll');
    } else {
        alarmContent.css('overflow-y', 'hidden');
    }
}


function deleteNotification(notificationId, div) {
    $.ajax({
        url: '/api/deleteNotification/' + notificationId,
        type: 'POST',
        success: function(result) {
            div.remove();
            removeNotificationFromStorage(notificationId); // localStorage에서 알림 제거
            if (osInstance) {
                osInstance.update(); // 오버레이 스크롤바 업데이트
            }
        },
        error: function(error) {
            console.error("알림 삭제 실패", error);
        }
    });
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
            addNotificationToContent(data, true); // 알림을 페이지에 동적으로 추가. 저장안함
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


function applyOverlayScrollbars() {
    osInstance = OverlayScrollbars(document.querySelectorAll('.alarm-content'), {
        overflowBehavior: {
            x: "hidden",
            y: "scroll"
        },
        scrollbars: {
            autoHide: "never"
        }
    });
}

$(document).ready(function() {
    loadNotifications();
});
