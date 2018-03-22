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
                alert(i18n["file.add"]);
            },
            error: function () {
                alert(i18n["file.add.err"]);
            },
        });
    } else {
        alert(i18n["file.select"]);
    }
}