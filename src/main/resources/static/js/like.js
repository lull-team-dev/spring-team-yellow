/**
 * 
 */

$(document).ready(function () {
    $('.like-link').click(function (e) {
        e.preventDefault();

        const $link = $(this);
        const roomId = $link.data('room-id');/* $linkはaタグで、そこのdata-room-id=""の値を取得 */
		

        $.ajax({
            url: `/like/${roomId}`,
            type: 'GET',
            success: function (response) {
                const icon = $link.find('i');

                // サーバーが状態を返す前提
                if (response === "liked") {
                    $link.addClass('liked');
                    icon.removeClass('fa-regular').addClass('fa-solid');
                } else {
                    $link.removeClass('liked');
                    icon.removeClass('fa-solid').addClass('fa-regular');
                }
            },
            error: function () {
                alert('通信エラーが発生しました。');
            }
        });
    });
});
