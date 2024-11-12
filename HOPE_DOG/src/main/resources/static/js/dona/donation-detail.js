
// 모듈 가져오기
import * as reply from "./comment.js";

let page = 1;
let hasNext = true;
let donaNo = document.getElementById('donaNo').value;
// console.log(donaNo);

// URL에서 파라미터를 가져오는 함수
function getParameter(name) {
  const urlParams = new URLSearchParams(window.location.search);
  return urlParams.get(name);
}

// 글 삭제 버튼
function deleteClick() {
  // donaNo 가져오기
  const donaNo = document.querySelector('.donaNo').textContent.trim();

  console.log('donaNo:', donaNo); // donaNo 값을 콘솔에 출력하여 확인

  if (!donaNo) {
    console.error('donaNo가 유효하지 않습니다.');
  }

  if (confirm('정말 삭제하시겠습니까?')) {
    console.log('글이 삭제되었습니다.');

    // donaNo URL에 포함시켜 이동
    location.href = `/dona/delete?donaNo=${donaNo}`;
  } else {
    console.log('글이 삭제되지 않았습니다.');
  }
}

window.deleteClick = deleteClick;


// 게시글수정 버튼
function modifyClick() {
  const donaNo = document.querySelector('.donaNo').textContent.trim(); // adoptNo를 HTML에서 가져옵니다.
  const centerMemberNo = document.querySelector('.centerMemberNo').textContent.trim(); // centerMemberNo를 HTML에서 가져옵니다.

  console.log(donaNo);
  // console.log(centerMemberNo);

  if (confirm('정말 수정하시겠습니까?')) {
    console.log('수정페이지로 이동합니다.');
    location.href = `/dona/modify?donaNo=${donaNo}&centerMemberNo=${centerMemberNo}`;
  } else {
    console.log('신청서페이지로 이동하지 않습니다.');
  }
}

window.modifyClick = modifyClick;


// 댓글 신고 버튼
function CommentReportClick(e) {
  const reportComment = prompt('신고 사유를 100글자 이내로 입력해주세요');
  if (reportComment && reportComment.length <= 100) {
    e.target.closest('reply').getAttribute('data-id')
    //detail.html에서 name으로 뿌려주고 있으니 donaNo을 name으로 가져오기
    const donaNo = document.querySelector('.donaNo').textContent.trim();
    const donaCommentNo = document.querySelector('.donaCommentNo').textContent.trim();

    location.href = `/dona/donation/donaCommentReport?donaNo=${donaNo}&reportComment=${encodeURIComponent(reportComment)}&donaCommentNo=${donaCommentNo}`;
  } else if (reportComment) {
    alert("신고 사유는 100글자 이내로 입력해주세요.");
  }
}


// ------------------------댓글 메뉴 처리-------------------------------------------
{
  let $replyListWrap = document.querySelector('.reply-list-wrap');

  $replyListWrap.addEventListener('click', function (e) {
    let $target = e.target;

    // 댓글 메뉴 토글
    if ($target.classList.contains('reply-btns')) {
      let $menu = $target.closest('.reply-btn-box').querySelector('.reply-btns__box');
      $menu.classList.toggle('none');
    }
    // 댓글 수정 초기화
    else if ($target.classList.contains('reply-modify-btn')) {
      $target.closest('.reply-btns__box').classList.add('none');
      let $contentBox = $target.closest('.reply').querySelector('.reply-box__content');
      let oldContent = $contentBox.innerText;
      $contentBox.innerHTML = `
        <div class="modify-box">
            <textarea class="modify-content">${oldContent}</textarea>
            <button type="button" class="modify-content-btn">수정 완료</button>
        </div>
      `;
    }
    // 댓글 삭제 처리
    else if ($target.classList.contains('reply-remove-btn')) {
      $target.closest('.reply-btns__box').classList.add('none');
      let donaCommentNo = $target.closest('.reply').dataset.id;

      let confirmed = confirm('정말 이 댓글을 삭제하시겠습니까?');
      if (confirmed) {
        reply.remove(donaCommentNo, () => {
          page = 1;
          reply.getList2(donaNo, page, function (data) {
            hasNext = data.hasNext;
            displayReply(data.contentList);
          });
        });
      }
    }
    // 댓글 수정 처리
    else if ($target.classList.contains('modify-content-btn')) {
      let donaCommentNo = $target.closest('.reply').dataset.id;
      let content = $target.closest('.modify-box').querySelector('.modify-content').value;

      let updateInfo = { donaCommentNo: donaCommentNo, donaCommentContent: content };
      reply.modify(updateInfo, () => {
        page = 1;
        reply.getList2(donaNo, page, function (data) {
          hasNext = data.hasNext;
          displayReply(data.contentList);
        });
      });
    }
    // 댓글 신고 처리
    else if ($target.classList.contains('reply-report-btn')) {
      let donaCommentNo = $target.closest('.reply').dataset.id;
      CommentReportClick(donaCommentNo);
    }
    else {
      document.querySelectorAll('.reply-btns__box').forEach(ele => ele.classList.add('none'));
    }
  });
}

// ------------------------------댓글 처리-----------------------------
{
  let $btnReply = document.querySelector('.btn-reply');

  $btnReply?.addEventListener('click', function () {
    let content = document.querySelector('#reply-content').value;

    if(!content) {
      alert("댓글 내용은 필수 사항입니다.");
      return;
    }

    let replyInfo = {
      donaNo: donaNo,
      donaCommentContent: content
    };
    console.log(donaNo + "donaNo확인 detail.js");
    reply.register(replyInfo, () => {
      document.querySelector('#reply-content').value = '';
      page = 1;
      reply.getList2(donaNo, page, function (data) {
        console.log(donaNo + "donaNo확인 detail.js");

        console.log(donaNo + "register 확인===");
        hasNext = data.hasNext;
        displayReply(data.contentList);
      });
    });
  });

  reply.getList2(donaNo, page, function (data) {
    console.log(donaNo + "getList2 확인===");
    hasNext = data.hasNext;
    displayReply(data.contentList);
  });

  window.addEventListener('scroll', function () {
    if(!hasNext) return;
    let {scrollTop, scrollHeight, clientHeight} = document.documentElement;

    if (clientHeight + scrollTop >= scrollHeight - 5) {
      page++;

      reply.getList2(donaNo, page, function (data) {
        hasNext = data.hasNext;
        appendReply(data.contentList);
      });
    }
  });
}

function displayReply(replyList) {
  console.log("displayReply 확인 ===== ");
  let $replyWrap = document.querySelector('.reply-list-wrap');
  let tags = '';

  replyList.forEach(r => {
    console.log("===== 댓글 내용 ====")
    console.log(r); // 각 댓글 데이터 객체 확인

    tags += `
      <div class="reply" data-id="${r.donaCommentNo}">
        <div class="reply-box">
          <div class="reply-box__writer">${r.commentWriterName}</div>
          <div class="reply-box__content">${r.donaCommentContent}</div>
        </div>
        <div class="reply-time">${reply.timeForToday(r.donaCommentRegidate)}</div>
        <div class="reply-btn-box">
          <span class="reply-btns"></span>
          <div class="reply-btns__box">`;

    if(r.donaCommentWriter === centerMemberNo || r.donaCommentWriter == memberNo) {
      console.log('작성자 일치 =====');
      tags += `<div class="reply-remove-btn">삭제</div>
      <div class="reply-modify-btn">수정</div>`;
    }else{
      tags += `<div class="reply-report-btn">신고</div>`;
    }

    tags += `</div>
        </div>
      </div>`;




  });
  $replyWrap.innerHTML = tags;
}
// 댓글 목록 추가
function appendReply(replyList) {
  let $replyWrap = document.querySelector('.reply-list-wrap');
  let tags = '';

  replyList.forEach(r => {
    r.center_member_name = undefined;
    r.member_nickname = undefined;
    console.log(r); // 각 댓글 데이터 객체 확인

    // 작성자가 회원인지 센터 회원인지 확인하여 적절한 이름 표시
    const writerName = r.dona_comment_writer === 'member'
        ? r.member_nickname
        : r.center_member_name;
    console.log(r.commentWriterName); // 각 댓글 데이터 객체 확인

    tags += `
      <div class="reply" data-id="${r.donaCommentNo}">
        <div class="reply-box">
          <div class="reply-box__writer">${r.commentWriterName}</div>
          <div class="reply-box__content">${r.donaCommentContent}</div>
        </div>
        <div class="reply-time">${reply.timeForToday(r.donaCommentRegidate)}</div>
        <div class="reply-btn-box">
          <span class="reply-btns"></span>
          <div class="reply-btns__box none">
            <div class="reply-remove-btn">삭제</div>
            <div class="reply-modify-btn">수정</div>
            <div class="reply-report-btn">신고</div>
          </div>
        </div>
      </div>
    `;
  });

  $replyWrap.insertAdjacentHTML("beforeend", tags);
}