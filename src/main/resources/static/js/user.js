let userForm  = {
    init: function(){
        $("#btn-save").on("click", ()=>{
            this.save();
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
}

userForm.init();