// 게시글로 이동
document.querySelectorAll('.mypage-adopt-list').forEach(function(element) {
    element.addEventListener('click', function() {
        const adoptRequestNo = element.querySelector('.adoptRequestNo').innerText;
        window.location.href = '/mypage/updateAdoptRequest?adoptRequestNo=' + adoptRequestNo; // 상세 페이지로 이동
    });

});
