
let index = {
    init: function () {
        $("#btn-board-write").on("click", () => {
            this.save_post();
        });
        $("#btn-delete").on("click", () => {
            this.delete_post();
        });
        $("#btn-update").on("click", () => {
            this.update_post();
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
        let id = $("#postId").val();

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
    update_post: function () {
        let id = $("#updateId").val();
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        }

        $.ajax({
            type: "PUT",
            url: "/api/board/"+id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
        }).done(function (res) {
            alert("글수정 OK");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}

index.init();