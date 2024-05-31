document.getElementById("process").innerHTML = "test";
//alert("test");



$(document).ready(function () {
    Operation();
});

function Operation() {
    $.ajax({
        type: "GET",
        url: '/run',
        success: function (data) {
            document.getElementById("process").innerHTML = "Opperation Process: "+data.toString();
            interval = setTimeout(Operation, 3000);
        },
    });
}