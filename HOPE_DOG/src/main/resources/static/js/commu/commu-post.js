// // 댓글등록 알터창
// function Comment() {
//   const comment = document.getElementById('commentInput').value.trim();
//
//   if (comment === '') {
//     alert('입력한 내용이 없습니다.');
//     return false; // 입력 내용이 없을 경우
//   }
//   return true; // 입력 내용이 있을 경우
// }
//
// //게시글
//
// // 수정, 삭제,신고 confirm창
// function modifyAlert() {
//   const userConfirmed = confirm("수정하시겠습니까?");
//   if (userConfirmed) {
//       window.location.href ='../../html/commu/commu-post-rewrite.html'; // 수정페이지로 이동
//   }
// }
//
// function deleteAlert() {
//   const userConfirmed = confirm("삭제하시겠습니까?");
//   if (userConfirmed) {
//       alert("삭제가 완료되었습니다."); // 확인 눌렀을 때 알림 창 표시
//       window.location.href = "../../html/commu/commu-main.html"; // 커뮤 메인으로 이동
//   }
// }
//
// function endAlert() {
//   const contentReport = prompt('신고사유를 100글자 이내로 입력해주세요');
//
//   if (contentReport !== null) { // 사용자가 확인을 누르면
//     console.log('게시글이 신고되었습니다');
//     alert('게시글이 신고되었습니다'); // 알림창 추가
//
//   } else {
//     console.log('게시글 신고가 취소되었습니다.');
//     alert('게시글 신고가 취소되었습니다.'); // 알림창 추가
//   }



// // 페이지네이션
// $(function() {
//   const items = $('.commu-ul-all ul');  // 게시글 항목 선택
//
//   // 게시글 수가 10개 이하인 경우 페이지네이션 처리 생략
//   if (items.length <= 10) {
//     items.show(); // 모든 항목 표시
//     return;
//   }
//
//   // 처음 10개 항목만 보이게 하고 나머지는 숨김
//   items.hide().slice(0, 10).show();
//
//   // 페이지네이션 설정
//   const container = $('#pagination');
//   const pageSize = 10;
//
//   container.pagination({
//     dataSource: items.toArray(),
//     pageSize: pageSize,
//     callback: function(data, pagination) {
//       items.hide();
//       $(data).each(function(index, item) {
//         $(item).show(); // 현재 페이지에 해당하는 항목만 표시
//       });
//     }
//   });
//
//   // 처음 로드 시 첫 번째 페이지의 항목만 보여주기
//   container.pagination('goToPage', 1);
// });
//
// // 검색
// function searchCars() {
//   const searchType = document.getElementById("search-type").value;
//   const keyword = document.getElementById("keyword").value;
//
//   console.log("검색 타입:", searchType, "키워드:", keyword);
//
//   fetch(`/main/commuSearch?searchType=${encodeURIComponent(searchType)}&keyword=${encodeURIComponent(keyword)}`) // searchType 추가
//       .then(response => {
//         if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
//         return response.json();
//       })
//       .then(data => {
//         console.log("검색 결과:", data);
//         displayResults(data); // 검색 결과를 화면에 표시
//         initializePagination(data); // 페이지네이션 초기화
//       })
//       .catch(error => console.error("검색 결과 가져오기 오류:", error));
// }
//
// // 검색 결과를 화면에 표시하는 함수
// function displayResults(data) {
//   const resultsDiv = document.getElementById("search-results");
//   resultsDiv.innerHTML = ""; // 이전 결과 초기화
//
//   if (data.length === 0) {
//     resultsDiv.innerHTML = "<p>검색 결과가 없습니다.</p>";
//     return;
//   }
//
//   data.forEach(commu => {
//     const commuElement = document.createElement("div");
//     commuElement.innerHTML = `
//             <h3>${commu.commuTitle}</h3>
//             <p>카테고리: ${commu.commuCate}</p>
//             <p>내용: ${commu.commuContent}</p>
//             <p>등록일: ${new Date(commu.commuRegiDate).toLocaleString()}</p>
//         `;
//     resultsDiv.appendChild(commuElement);
//   });
// }
//
// // 페이지네이션 초기화 함수
// function initializePagination(data) {
//   const container = $('#pagination');
//   const pageSize = 10;
//
//   // 기존 페이지네이션 제거
//   if (container.data("pagination")) {
//     container.pagination('destroy');
//   }
//
//   // 새로운 페이지네이션 설정
//   container.pagination({
//     dataSource: data,
//     pageSize: pageSize,
//     callback: function(data, pagination) {
//       displayResults(data);
//     }
//   });
//
//   // 첫 번째 페이지로 이동
//   container.pagination('goToPage', 1);
// }
//
// // 폼 제출 시 검색 수행
// document.querySelector('form[name="search"]').addEventListener('submit', function(e) {
//   e.preventDefault(); // 기본 제출 방지
//   searchCars(); // 검색 함수 호출
// });

$(function() {
  const items = $('.commu-ul-all ul');  // 게시글 항목 선택
  const container = $('#pagination');
  const pageSize = 10;

  // 페이지네이션 처리
  function initializePagination(data) {
    // 페이지네이션이 이미 설정되어 있는 경우 초기화
    if (container.data("pagination")) {
      container.pagination('reset');
    }

    container.pagination({
      dataSource: data,
      pageSize: pageSize,
      callback: function(data, pagination) {
        displayResults(data);  // 현재 페이지 데이터 표시
      }
    });

    container.pagination('goToPage', 1); // 첫 페이지로 이동
  }

  // 초기 페이지네이션 설정
  if (items.length > pageSize) {
    items.hide().slice(0, pageSize).show();
    initializePagination(items.toArray());
  } else {
    items.show(); // 게시글 수가 적으면 모든 항목 표시
  }
});

// 검색 함수
function searchCars() {
  const searchType = document.getElementById("search-type").value;
  const keyword = document.getElementById("keyword").value;
  const resultsDiv = document.getElementById("search-results");

  if (!resultsDiv) return;

  console.log("검색 타입:", searchType, "키워드:", keyword);

  fetch(`/main/commuSearch?searchType=${encodeURIComponent(searchType)}&keyword=${encodeURIComponent(keyword)}`)
      .then(response => {
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return response.json();
      })
      .then(data => {
        console.log("검색 결과:", data);
        if (data.length === 0) {
          resultsDiv.innerHTML = "<p>검색 결과가 없습니다.</p>";
          $('#pagination').pagination('destroy'); // 결과가 없으면 페이지네이션 제거
        } else {
          initializePagination(data); // 페이지네이션 설정
        }
      })
      .catch(error => console.error("검색 결과 가져오기 오류:", error));
}

// 검색 결과를 화면에 표시하는 함수
function displayResults(data) {
  const resultsDiv = document.getElementById("search-results");

  if (!resultsDiv) return;

  resultsDiv.innerHTML = ""; // 이전 결과 초기화

  data.forEach(commu => {
    const commuElement = document.createElement("div");
    commuElement.innerHTML = `
            <h3>${commu.commuTitle}</h3>
            <p>카테고리: ${commu.commuCate}</p>
            <p>내용: ${commu.commuContent}</p>
            <p>등록일: ${new Date(commu.commuRegiDate).toLocaleString()}</p>
        `;
    resultsDiv.appendChild(commuElement);
  });
}

// 폼 제출 시 검색 수행
document.querySelector('form[name="search"]').addEventListener('submit', function(e) {
  e.preventDefault(); // 기본 제출 방지
  searchCars(); // 검색 함수 호출
});