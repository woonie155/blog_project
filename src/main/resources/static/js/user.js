let userForm  = {
    init: function(){
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });
    },

     save: function(){
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };
        $.ajax({
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            location.href="/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    update: function () {
        let data = {
            id: $("#id").val(),
            username: $('#username').val(),
            password: $("#password").val(),
            email: $("#email").val()
        }

        $.ajax({
            type: "PUT",
            url: "/api/user/update",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (res) {
            alert("회원수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
}

userForm.init();