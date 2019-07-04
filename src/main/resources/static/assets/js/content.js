$('.navbar').css('display', 'none');
$(function () {
    var clickcount = 0;

    <!--鼠标单击正文显示或者取消导航栏-->
    $('.content-wrap').click(function () {
        clickcount++;
        if (clickcount % 2 == 0) {
            $('.navbar').css('display', 'none');
        } else {
            $('.navbar').css('display', 'flex');
        }
    });


    <!--更换skin-->
    $('.skin').click(function () {
        var data = $(this).attr('data-skin');
        $('body').removeClass();
        $('body').addClass(data);
    });
    var fontdata = 'Microsoft Yahei';
    <!--更换字体样式-->
    $('.font-style').click(function () {
        fontdata = $(this).attr('data-skin');
        $('.chapter-content').css('font-family', fontdata);
    });

    var sizedata ='100%';
    <!--更换字体大小-->
    $('.font-sz').click(function () {
        sizedata = $(this).attr('data-skin');
        $('.chapter-content').css('font-size', sizedata);
    });

    var hasajax = false;

    var lastReadChpaterId = ''; // 最新阅读的章节Id

    <!--监听滚动条触发ajax事件-->
    $(document).scroll(function () {

        var scrollTop = document.documentElement.scrollTop || document.body.scrollTop; //滚动条距离顶部高度

        var scroH = $(document).scrollTop();  //滚动高度
        var viewH = $(window).height();  //可见高度
        var contentH = $(document).height();  //内容高度

        if ($('#user').length > 0) {
            //遍历所有的章节标题元素
            $('.ctitle').each(function () {
                if($(this).offset().top  - scrollTop < 200 && $(this).offset().top  > scrollTop ) {
                    if (  lastReadChpaterId != $(this).attr('data')) {
                        lastReadChpaterId = $(this).attr('data');
                        novelId = $('#nid').val();
                        console.log(lastReadChpaterId);
                        //更新最后阅读的章节
                        var url = '/bookshelf/' + $('#uname').text() + "/" + novelId + "/"
                         + lastReadChpaterId;
                        $.get(url, function (data) {
                            if(data == "updated") {
                                console.log('用户最新阅读进度已更新~');
                            }
                        });
                    }
                }
            });
        }

        if (contentH - (scroH + viewH) <= 70){  //距离底部高度小于70px
            if(hasajax) {
                return false;
            }
            hasajax = true;
            var url = $('.ajax-chapter:last').val();
            $.get(url, function (data) {
                $.each(data,function (i, n) {
                    var div =  '<div class="col-sm-10 offset-sm-1 chapter-content"><h3 class="ctitle" data=' + n["id"] +'>' + n["name"] + '</h3> <div class="p-content"> ' + n["content"] + '</div> <input type="hidden" value=/chapterajax/' + n["novelId"] + '/' + n["id"] + ' class="ajax-chapter"> </div>';
                    $('.content-wrap').append(div);
                    $('.chapter-content').css('font-family', fontdata);
                    $('.chapter-content').css('font-size', sizedata);
                });
            });
        }

        else {
            hasajax = false;
        }
    });
});