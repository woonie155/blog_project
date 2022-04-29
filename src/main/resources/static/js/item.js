
let index = {
    init: function () {
        $("#clothes-update").on("click", () => {
            this.update_clothes();
        });
    },

    update_clothes: function () {
        let id = $("#updateId").val();
        let data = {
            name: $("#name").val(),
            price: $("#price").val(),
            stockQuantity: $("#stockQuantity").val(),
            brand: $("#brand").val(),
        }

        $.ajax({
            type: "PUT",
            url: "/shop/clothes/" + id + "/edit",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
        }).done(function (res) {
            alert("clothes 수정 OK");
            location.href = "/shop/main";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}


index.init();

