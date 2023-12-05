/**
 *
 */

const commentUpdatable = `
	<button class="btn btn-light btn-sm comment-update-show-btn">
		<i class="fa-solid fa-pen-to-square"></i> 수정
	</button>
	<button class="btn btn-light btn-sm comment-delete-btn">
		<i class="fa-solid fa-times"></i> 삭제
	</button>
`;

// 댓글 바디

function createCommentTemplate(comment, username) {
  console.log(comment);
  return `
      <div class="comment my-3" data-no="${comment.ratingid}">
          <div class="comment-title my-2 d-flex justify-content-between">
              <div >
                <strong class="writer">
                ${comment.userid}
                  </strong>
                  <span class="text-muted ms-3 comment-date">
                      ${moment(comment.rating_date).format("YYYY-MM-DD hh:mm")}
                  </span>
              </div>
              <div  class="btn-group">
              ${
                username && username == comment.userid ? commentUpdatable : ""
              }                     		
            </div>
          </div>
  
          <div class="comment-body">
              <div class="comment-content">${comment.rating_review}</div>

          </div>  
      </div>
      `;
}

// 코멘트 불러오는 곳?
async function loadComments(bno, writer) {
  let comments = [];
  // API로 불러오기
  comments = await rest_get(COMMENT_URL);

  for (let comment of comments) {
    const commentEl = $(createCommentTemplate(comment, writer));
    $(".comment-list").append(commentEl);
  }
}

// 코멘트 생성
async function createComment(bookid, userid) {
  const rating_review = $(".new-comment-content").val();
  console.log(rating_review);

  if (!rating_review) {
    alert("내용을 입력하세요.");
    $(".new-comment-content").focus();
    return;
  }

  if (!confirm("댓글을 추가할까요?")) return;

  let comment = {
    bookid,
    userid,
    rating_review,
  };

  comment = await rest_create(COMMENT_URL, comment);

  // 등록 성공 후 DOM 처리
  const commentEl = createCommentTemplate(comment, userid);
  $(".comment-list").prepend($(commentEl));
  $(".new-comment-content").val("");
}

//코멘트 수정 화면 만들기
function createCommentEditTemplate(comment) {
  return `
		<div class="bg-light p-2 rounded comment-edit-block">
			<textarea class="form-control mb-1 comment-editor"
				>${comment.content}</textarea>
			<div class="text-end">
				<button class="btn btn-light btn-sm py-1 comment-update-btn">
					<i class="fa-solid fa-check"></i> 확인</button>
				<button class="btn btn-light btn-sm  py-1 comment-update-cancel-btn">
					<i class="fa-solid fa-undo"></i> 최소</button>
			</div>
		</div>
	`;
}

//댓글 수정 화면 보여주기
//
function showUpdateComment(e) {
  const commentEl = $(this).closest(".comment");
  const no = commentEl.data("no");

  const contentEl = commentEl.find(".comment-content");
  const comment = { no, content: contentEl.html().trim() };

  console.log(comment);

  contentEl.hide();
  commentEl.find(".btn-group").hide();

  const template = createCommentEditTemplate(comment);
  const el = $(template);
  commentEl.find(".comment-body").append(el);
}

// 댓글 수정하기
async function updateComment(commentEl, userid) {
  if (!confirm("수정할까요?")) return;

  const editContentEl = commentEl.find(".comment-edit-block"); // 수정 창
  const rating_review = editContentEl.find(".comment-editor").val(); // 수정 내용
  const ratingid = parseInt(commentEl.data("no"));
  console.log("---------->>> " + ratingid);
  let comment = { ratingid, userid, rating_review };

  console.log("----->>  Data : " + comment.userid);
  console.log("----->>  Data : " + comment.rating_review);

  comment = await rest_modify(COMMENT_URL + comment.ratingid, comment);
  console.log("수정", comment);

  const contentEl = commentEl.find(".comment-content");
  editContentEl.remove();
  contentEl.html(comment.rating_review); // 변경된 내용으로 화면 내용 수정
  contentEl.show();

  commentEl.find(".btn-group").show();
}

// 댓글 수정 취소
function cancelCommentUpdate(e) {
  const commentEl = $(this).closest(".comment");
  commentEl.find(".comment-content").show();
  //.css('display', 'block');

  commentEl.find(".comment-edit-block").remove();
  commentEl.find(".btn-group").show();
}

// 댓글 삭제
async function deleteComment(e) {
  if (!confirm("댓글을 삭제할까요?")) return;

  const comment = $(this).closest(".comment");
  const no = comment.data("no");

  await rest_delete(COMMENT_URL + no);

  // api 호출
  comment.remove();
}
