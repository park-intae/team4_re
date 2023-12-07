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
  // 별점 생성 함수
  function createStarRatingShow(rating) {
    let starRating = '<fieldset class="rated">';
    for (let i = 4; i >= 1; i--) {
      const isChecked = i <= rating ? "checked" : ""; // i가 rating 이하인 경우 checked
      const additionalStyle =
        isChecked === "checked" ? 'style="color: #f73c32 !important;"' : ""; // 예시로 gold 색을 추가 스타일로 지정
      starRating += `
          <label for="rate${i}" title="${i}점" ${isChecked} ${additionalStyle}></label>
        `;
    }
    starRating += "</fieldset>";
    return starRating;
  }

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
                  ${createStarRatingShow(comment.rating)}

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
async function createComment(bookid, userid, rating) {
  const rating_review = $(".new-comment-content").val();

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
    rating,
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
  <fieldset class="star-edit">
    <input type="radio" id="star4" name="star" value="4"
      onclick="handlestarEdit(4)"> <label for="star4"
      title="4점"></label> <input type="radio" id="star3" name="star"
      value="3" onclick="handlestarEdit(3)"> <label
      for="star3" title="3점"></label> <input type="radio" id="star2"
      name="star" value="2" onclick="handlestarEdit(2)"> <label
      for="star2" title="2점"></label> <input type="radio" id="star1"
      name="star" value="1" onclick="handlestarEdit(1)"> <label
      for="star1" title="1점"></label>
  </fieldset>
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

// 등급 변경 함수
function handlestarEdit(star) {
  starEdit = star;
  console.log("ratingEdit : " + starEdit);
}
let starEdit = 0;
//댓글 수정 화면 보여주기
//
function showUpdateComment(e) {
  const commentEl = $(this).closest(".comment");
  const no = commentEl.data("no");

  const contentEl = commentEl.find(".comment-content");
  const comment = { no, content: contentEl.html().trim() };

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

  console.log("starEdit : " + starEdit);
  const rating = starEdit;
  console.log("rating : " + rating);

  let comment = { ratingid, userid, rating_review, rating };

  comment = await rest_modify(COMMENT_URL + comment.ratingid, comment);

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
