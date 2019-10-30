/**
 * 动态发表数据
 * @type {{enclosure: Array, content: string, status: number}}
 */
let formDynamicJSON = {
    content: "",
    status: 1,
    enclosure: []
};

$("#dynamic-content").on('input propertychang', function () {
    formDynamicJSON.content = $(this).val();
    console.log(formDynamicJSON);
});

$("input[name='jurisdiction']").on('input propertychange', function () {
    formDynamicJSON.status = $(this).is(':checked') ? 2 : 1;
});

$("#imgFile").on('input propertychange', function () {
    console.log(formDynamicJSON.enclosure.length);
    let fileDom = document.getElementById("imgFile");
    for (let i = 0; i < fileDom.files.length && i < 9; i++) {
        let file;
        file = fileDom.files[i];
        gen_base64(file, function (base64_result) {
            /**
             * 最多9张图片
             */
            if (formDynamicJSON.enclosure.length < 9) {
                //将图片转码为base64，存储
                formDynamicJSON.enclosure.push({
                    "imgBase64": base64_result,
                    "type": file.name.substr(file.name.lastIndexOf(".") + 1)
                });
                //图片浏览
                let htmlContent = '<div class="img-wrap">';
                htmlContent += '<img src="' + base64_result + '" width="110px" height="110px" id="">';
                htmlContent += '<i class="fa fa-close close-font" onclick="delImage(this)"></i>';
                htmlContent += '</div>';
                $('#imgDiv').append(htmlContent);
                //超过9张，取消文件选择功能
                if (formDynamicJSON.enclosure.length >= 9) {
                    $("#filePicker").attr("for", "");
                }
            }
        });
    }
});

let delImage = function (obj) {
    obj = $(obj).prev();
    let imageContent = $(obj).attr("src");
    for (let i = 0; i < formDynamicJSON.enclosure.length; i++) {
        if (imageContent === formDynamicJSON.enclosure[i].imgBase64) {
            formDynamicJSON.enclosure.splice(i, 1);
            break;
        }
    }
    $(obj).parent().remove();
    if (formDynamicJSON.enclosure.length < 9) {
        $("#filePicker").attr("for", "imgFile");
    } else {
        $("#filePicker").attr("for", "");
    }
};

$("#uploader").click(function () {
    $("#imgFile").trigger("click");
});

$("#form-dynamic").submit(function () {
    if (formDynamicJSON.content.trim().length <= 150) {
        if (formDynamicJSON.enclosure.length >= 1 && formDynamicJSON.enclosure.length <= 9) {
            addDynamic(formDynamicJSON);
            formDynamicJSON = {
                content: "",
                status: 1,
                enclosure: []
            };
        } else {
            alert("必须上传一张图片");
        }
    } else {
        alert("字数请在150字以内");
    }
    return false;
});