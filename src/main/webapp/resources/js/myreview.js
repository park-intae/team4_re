
async function fetchBookTitle(bookId) {
    try {
        const response = await fetch(`/book/detail/${bookId}/title`);
        if (!response.ok) {
            throw new Error(`네트워크 응답이 정상이 아닙니다: ${response.statusText}`);
        }
        const title = await response.text();
        return title;
    } catch (error) {
        return '알 수 없는 제목';
    }
}

// 사용자 댓글 불러오기
async function loadUserComments() {
    try {
        const response = await fetch(`/api/user/comments?userId=${loggedInUserId}`);
        if (!response.ok) {
            throw new Error(`Network response was not ok: ${response.statusText}`);
        }
        const comments = await response.json();
        return comments;
    } catch (error) {
        return [];
    }
}

// 댓글 표시
async function displayUserComments(comments) {
    const commentsContainer = document.querySelector('.review-content');

    for (const comment of comments) {
        const bookTitle = await fetchBookTitle(comment.bookid); // 책 제목 조회
        const formattedDate = moment(comment.rating_date).format("YYYY-MM-DD hh:mm");
        const commentElement = document.createElement('div');
        
        commentElement.className = 'user-comment';
        commentElement.setAttribute('data-bookid', comment.bookid); // bookId 저장
        commentElement.setAttribute('data-no', comment.ratingid); // commentId 저장
        commentElement.innerHTML = `
            <strong>책 제목: ${bookTitle}</strong>
            <p>${comment.rating_review}</p>
            <div> ${formattedDate} </div>
            <button onclick="editComment(${comment.ratingid})" class="btn btn-primary btn-sm my-2 comment-add-btn"><i class="fa-solid fa-pen-to-square"></i> 수정 </button>
            <button onclick="deleteComment(${comment.ratingid})" class="btn btn-primary btn-sm my-2 comment-add-btn"><i class="fa-solid fa-trash"></i> 삭제 </button>
        `;
        commentsContainer.appendChild(commentElement);
    }
}

// 댓글 수정 함수
async function editComment(commentId) {

 	const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    
    const commentElement = document.querySelector(`.user-comment[data-no="${commentId}"]`);
    const bookId = commentElement.getAttribute('data-bookid');
    const newCommentContent = prompt("댓글을 수정하세요.");

    if (newCommentContent) {
        try {
            const response = await fetch(`/api/book/detail/${bookId}/comment/${commentId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken 
                },
              body: JSON.stringify({ 
                    rating_review: newCommentContent,
                    userid: loggedInUserId, 
                    bookid: bookId,
                    ratingid: commentId
                })
            });

            if (response.ok) {
            
            	const commentElement = document.querySelector(`.user-comment[data-no="${commentId}"] p`);
                commentElement.textContent = newCommentContent;
                
                alert("댓글이 수정되었습니다.");
                loadUserComments();
            } else {
                alert("댓글 수정 실패!");
            }
        } catch (error) {
            console.error('댓글 수정 중 오류 발생:', error);
        }
    }
}

// 댓글 삭제 함수
async function deleteComment(commentId) {
    const commentElement = document.querySelector(`.user-comment[data-no="${commentId}"]`);
    const bookId = commentElement.getAttribute('data-bookid'); 

    if (confirm("댓글을 삭제하시겠습니까?")) {
        try {
            const response = await fetch(`/api/book/detail/${bookId}/comment/${commentId}`, {
                method: 'DELETE'
            });
            if (response.ok) {
            	commentElement.remove();
                alert("댓글이 삭제되었습니다.");
                loadUserComments(); // 댓글 목록 새로고침
            } else {
                alert("댓글 삭제 실패!");
            }
        } catch (error) {
            console.error('댓글 삭제 중 오류 발생:', error);
        }
    }
}

// 페이지 로드 시 댓글 불러오기
document.addEventListener('DOMContentLoaded', async () => {
    const userComments = await loadUserComments();
    if (userComments.length > 0) {
        await displayUserComments(userComments);
    } else {
        document.querySelector('.review-content').innerText = '';
    }
});



