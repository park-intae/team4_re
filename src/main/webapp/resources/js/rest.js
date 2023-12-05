/**
 *
 */

async function rest_get(url) {
  try {
    let res = await fetch(url);
    return await res.json();
  } catch (e) {
    console.log(e);
  }
}

async function rest_create(url, data) {
  try {
    let res = await fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });

    if (res.ok) {
      // 서버로부터 성공적인 응답을 받았을 때의 처리
      return await res.json();
    }
  } catch (e) {
    console.log(e);
  }
}

async function rest_modify(url, data) {
  try {
    let res = await fetch(url, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });

    return await res.json();
  } catch (e) {
    console.log(e);
  }
}

async function rest_delete(url) {
  try {
    let res = await fetch(url, { method: "DELETE" });
    return await res.text(); // res.json();
  } catch (e) {
    console.log(e);
  }
}
