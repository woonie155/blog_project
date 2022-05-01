let orderForm = {

    init: function(){
        $("#btn-order-save").on("click", ()=>{
            this.save();
        });
    },

     save: function(){

            let data = {
                id: $("#user").val(),
                itemId: $("#item").val(),
                count: $("#count").val()
            };

            $.ajax({
                type: "POST",
                url: "/shop/order/saveProc",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function(resp){
                alert("주문 등록 OK");
                location.href="/shop/main";
            }).fail(function(error){
                alert(JSON.stringify(error));
            });
    }

}

orderForm.init();