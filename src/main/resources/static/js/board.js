
let index = {
    init: function () {
        $("#btn-board-write").on("click", () => {
            this.save_post();
        });
        $("#btn-delete").on("click", () => {
            this.delete_post();
        });
    },

    save_post: function () {
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
    delete_post: function () {
//        let id = $("#id").text();
        let id = document.getElementById('postId').value;

        $.ajax({
            type: "DELETE",
            dataType: "json",
            url: "/api/board/"+id,
        }).done(function (res) {
            alert("글삭제 OK");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}

index.init();