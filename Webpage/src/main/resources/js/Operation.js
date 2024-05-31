$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: "/production/run",
        dataType: "json",
        success: function(response) {
            document.getElementById("process").innerHTML = response;
            console.log(response);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("HTTP Status: " + jqXHR.status + "; Error Text: " + jqXHR.responseText);
        },
        timeout: 5000
    });
});