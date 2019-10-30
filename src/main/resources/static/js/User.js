/**
 * 判断邮箱
 */
$("#email").on('input', function () {
    if (isMail($(this).val())) {
        $(this).removeClass("is-invalid");
        $(this).addClass("is-valid");
    } else {
        $(this).addClass("is-invalid");
        $(this).removeClass("is-valid");
    }
});

/**
 * 实时判断昵称
 */
$("#nick-name").on('input', function () {
    if (isNickName($(this).val())) {
        $(this).removeClass("is-invalid");
        $(this).addClass("is-valid");
    } else {
        $(this).addClass("is-invalid");
        $(this).removeClass("is-valid");
    }
});
/**
 * 实时判断密码
 */
$("#password").on('input', function () {
    if (isPass($(this).val())) {
        $(this).removeClass("is-invalid");
        $(this).addClass("is-valid");
    } else {
        $(this).addClass("is-invalid");
        $(this).removeClass("is-valid");
    }
    if ($("#password-two").length > 0 && $("#password-two").val().length > 0) {
        $("#password-two").trigger('input');
    }
});

/**
 * 实时判断两次密码
 */
$("#password-two").on('input', function () {
    if (isPass($(this).val()) && isSamePass($(this).val(), $("#password").val())) {
        $(this).removeClass("is-invalid");
        $(this).addClass("is-valid");
    } else {
        $(this).addClass("is-invalid");
        $(this).removeClass("is-valid");
    }
});

/**
 * 注册提交
 */
$("#form-register").submit(function () {
    if ($(this).find('input').length === $(this).find('.is-valid').length) {
        $("#password").val($.md5($("#password").val()));
        return true;
    } else {
        return false;
    }
});

/**
 * 登录提交
 */
$("#login-form").submit(function () {
    if ($(this).find('input').length === $(this).find('.is-valid').length) {
        $("#password").val($.md5($("#password").val()));
        return true;
    } else {
        return false;
    }
});
