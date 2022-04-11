
let index = {
    init: function () {
        $("#btn-board-write").on("click", () => {
            this.save();
        });
    },

    save: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        }

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("글작성 OK");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}

index.init();