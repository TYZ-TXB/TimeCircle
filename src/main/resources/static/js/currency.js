let ReleaseIndex = null;
let DynamicIframes = ["toHome", "toMyDynamic"];
/**
 * 打开发布新动态iframe框层
 */
let openRelease = function (obj) {

    parent.layer.closeAll(ReleaseIndex);
    ReleaseIndex = parent.layer.open({
        type: 2,                        //类型
        title: '发表新动态',             //标题
        content: ['toRelease', 'yes'],  //iframe的url，no代表不显示滚动条
        skin: 'layui-layer-molv',        //样式
        area: ['380px', '350px'],       //尺寸
        offset: 'auto',                 //显示位置
        closeBtn: 1,                    //关闭按钮
        shade: 0.3,                     //遮罩层
        shadeClose: true,              //遮罩层
        id: 'release',                  //id
        anim: 0,                        //动画
        isOutAnim: true,                //关闭动画
        maxmin: false,                  //最大化最小化
        fixed: true,
        resize: false,
        tipsMore: false
    });
};

let spinkit = function (status, obj) {
    if (status === 'off') {
        let a = $(obj).find('.spiner-example');
        if (a != undefined || a != null) {
            $(a).remove();
        }
    } else if (status === 'on') {
        let htmlContent = '<div class="spiner-example">' +
            '<div class="sk-spinner sk-spinner-circle" >' +
            '<div class="sk-circle1 sk-circle"></div>' +
            '<div class="sk-circle2 sk-circle"></div>' +
            '<div class="sk-circle3 sk-circle"></div>' +
            '<div class="sk-circle4 sk-circle"></div>' +
            '<div class="sk-circle5 sk-circle"></div>' +
            '<div class="sk-circle6 sk-circle"></div>' +
            '<div class="sk-circle7 sk-circle"></div>' +
            '<div class="sk-circle8 sk-circle"></div>' +
            '<div class="sk-circle9 sk-circle"></div>' +
            '<div class="sk-circle10 sk-circle"></div>' +
            '<div class="sk-circle11 sk-circle"></div>' +
            '<div class="sk-circle12 sk-circle"></div>' +
            ' </div>' +
            '</div>';
        $(obj).empty();
        $(obj).append(htmlContent);
    }
};

let addDynamic = function (JSONData) {
    let index = ReleaseIndex;
    $.ajax({
        url: "addDynamic",
        type: "post",
        async: false,
        contentType: 'application/json',
        data: JSON.stringify(JSONData),
        success: function (data) {
            console.log(data);
            if (data.status === 'success') {
                addShowDynamic(data, index);
            } else {
                alert("发表失败");
            }
        }
    });
};

let addShowDynamic = function (data, index) {
    let parent = window.top;
    for (let i = 0; i < DynamicIframes.length; i++) {
        let b = parent.frames[DynamicIframes[i]];
        if (b != undefined) {
            let obj = $("#DynamicContent", b.document);
            showDynamic(data, obj);
        }

    }
    parent.layer.closeAll(index);
};

let updateDynamicHtml = function (rid, status, obj) {
    let htmlContent = '';
    if (status === 1) {
        htmlContent += '<li><a href="javascript:void(0);" onclick="updateDynamicStatus(' + rid + ',2,this)">隐藏</a></li>';
    } else if (status === 2) {
        htmlContent += '<li><a href="javascript:void(0);" onclick="updateDynamicStatus(' + rid + ',1,this)">公开</a></li>';
    } else if (status === 0 && obj != null) {
        $(obj).closest('.feed-element').remove();
    }
    if (obj != null) {
        $(obj).closest('ul').prepend(htmlContent);
        $(obj).remove();
    }
    return htmlContent;
};


let updateDynamicStatus = function (rid, status, obj) {
    let url = '/updateDynamicStatus';
    let data = {"rid": rid, 'status': status};
    $.ajax({
        url: url,
        type: "post",
        data: data,
        success: function (data) {
            if (data.status === 'success')
                updateDynamicHtml(rid, status, obj);
        }
    });
};


let imagesHtml = function (images) {
    let content = '';
    for (let j = 0; j < images.length; j++) {
        content += '<img class="feed-photo" alt="image' + j + '" src="' + images[j].url + '" width="110px" height="110px">';
        if ((j + 1) % 3 === 0) {
            content += '<br/>';
        }
    }
    return content;
};

let getAllDynamic = function (callback, obj) {
    $.ajax({
        url: "getAllDynamic",
        type: "post",
        success: function (data) {
            callback(data, obj);
        }
    });
};
let getMyDynamic = function (callback, obj) {
    $.ajax({
        url: "getMyDynamic",
        type: "post",
        success: function (data) {
            callback(data, obj);
        }
    });
};

let showDynamic = function (data, obj) {
    spinkit('off', obj);
    data = eval(data);
    data = data.data;
    if (data.length <= 0) {

    } else {
        for (let i = 0; i < data.length; i++) {
            let htmlContent = '<div class="feed-element">' +
                '<a href="#" class="pull-left">' +
                '<!--头像-->' +
                '<img alt="头像" class="img-circle" src="' + data[i].head + '">' +
                '</a>' +
                '<div class="media-body">';
            if (data[i].isMe === true) {
                htmlContent += '<small class="pull-right social-action dropdown">' +
                    '<button data-toggle="dropdown" class="dropdown-toggle btn-white">' +
                    '<i class="fa fa-angle-down"></i>' +
                    '</button>' +
                    '<ul class="dropdown-menu m-t-xs">';
                htmlContent += updateDynamicHtml(data[i].id, data[i].status, null);
                htmlContent += '<li><a href="javascript:void(0)" onclick="updateDynamicStatus(' + data[i].id + ',0,this)">删除</a></li>' +
                    '</ul>' +
                    '</small>';
            }
            htmlContent += '<strong>' + data[i].nickName + '</strong>' +
                '<br>' +
                '<small class="text-muted">' + dateFormat(data[i].modifiedTime) + '</small>' +
                '<div class="text-body">' +
                data[i].content +
                '</div>' +
                '<!--附件列表-->' +
                '<div class="photos">' +
                imagesHtml(data[i].imgs) +
                '</div>' +
                '</div>' +
                '</div>';
            $(obj).prepend(htmlContent);
        }
    }
};