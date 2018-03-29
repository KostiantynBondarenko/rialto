function uploudFile() {
    var file = $("#file");
    var fileVal = file.val();
    if (fileVal != null && fileVal != "") {
        var fileFormat = fileVal.split('.')[1];
        if (fileFormat != "xls" && fileFormat != "xlsx") {
            alert(i18n["file.format.err"]);
            return;
        }

        var fileUpload = file.prop('files')[0];
        if (fileUpload.size > 7 * 1024 * 1024) {
            alert(i18n["file.size.err"]);
            return;
        }
        var formData = new FormData;
        formData.append('file', fileUpload);

        $.ajax({
            url: '/rialto/uploudFile',
            type: 'POST',
            cache: false,
            contentType: false,
            processData: false,
            data: formData,
            success: function () {
                successNoty(i18n["file.add"]);
            },
            error: function () {
                failNoty(i18n["file.add.err"]);
            },
        });
    } else {
        warningNoty(i18n["file.select"]);
    }
}

$(document).ajaxError(function (event, jqXHR, options, jsExc) {
    failNotyAjax(event, jqXHR, options, jsExc);
});

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='glyphicon glyphicon-ok'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function warningNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='glyphicon glyphicon-exclamation-sign'></span> &nbsp;" + text,
        type: 'warning',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(text) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='glyphicon glyphicon-exclamation-sign'></span> &nbsp;" + text,
        type: "error",
        layout: "bottomRight"
    }).show();
}

function failNotyAjax(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='glyphicon glyphicon-exclamation-sign'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}