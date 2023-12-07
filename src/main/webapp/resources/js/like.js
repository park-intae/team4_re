async function loadLikes() {
  try {
    const users = await rest_get(LIKE_URL);

    console.log("users : ", users);
    console.log("usersLength : ", users.length);

    // users가 배열이라고 가정하고 forEach를 사용하여 각 user에 대한 템플릿 생성
    users.forEach((user) => {
      const userEl = $(createLikeTemplate(user));
      $(".like-list").append(userEl);
    });
  } catch (error) {
    console.error(error);
  }
}

async function loadLikesBooks(user) {
  try {
    const books = await rest_get(`${LIKE_URL}${user}`);

    console.log(user);

    books.forEach((book) => {
      const bookEl = $(createLikeBookTemplate(book));
      $(`[data-no="${user}"]`).append(bookEl);
    });
  } catch (error) {
    console.log(error);
  }
}

function createLikeTemplate(user) {
  return `
      <div class="like-user">
        <img class="avatar" src="https://api.dicebear.com/7.x/identicon/svg?seed=${user.username}" />
        <p class="username">
          ${user.username}
        </p>
        <p class="userid">
          ${user.userid}
        </p>
        <div class="likeBooks" data-no="${user.userid}"></div>
        <hr>
      `;
}

function createLikeBookTemplate(book) {
  return `
    <a href="/book/detail?bookid=${book.bookid}">
    ${book.title}
    </a>
    <br>
    `;
}
