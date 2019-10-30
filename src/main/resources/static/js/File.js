/**
 * 将图片转为base64
 * @param file          图片文件
 * @param callback      回调函数
 * @returns {boolean}   无
 */
function gen_base64(file,callback) {
    if(!/image\/\w+/.test(file.type)){
        alert("请确保文件为图像类型");
        return false;
    }
    let r = new FileReader();  //本地预览
    r.onload = function(){
        callback(r.result);
    };
    r.readAsDataURL(file);    //Base64
}