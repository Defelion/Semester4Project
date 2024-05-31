$(document).ready(function () {
    getAllRecipes();

    //This part runs the "button_actions"-function
    //with parameter being based on the button that is pressed.
    $("#startButton").click(function () {
        button_actions("start");
    });
    $("#stopButton").click(function () {
        button_actions("stop");
    });

    $("#resumeButton").click(function () {
        button_actions("resume");
    });

    //This part creates a Batch using the inputs in the Production webpage.
    $('#createBatchButton').click(function() {
        let recipe = $('#recipeInput').val();
        let quantity = $('#quantityInput').val();
        let priority = $('#priorityInput').val();

        let batch = {
            recipe: recipe,
            quantity: quantity,
            priority: priority
        };

        addBatch(batch);
    });
});

//This function uses the appropriate function in the ProductionController
//whenever a button is clicked in the Monitor Tab.
function button_actions(action) {
    $.ajax({
        type: "POST",
        url: "/production/" + action,
        dataType: "json",
        success: function (response) {
            console.log(response);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("HTTP Status: " + jqXHR.status + "; Error Text: " + jqXHR.responseText);
            alert("An error occurred. Please try again.");
        }
    });
}

//This function gets all the Recipes from the database
/*function getAllRecipes() {
    $.ajax({

        type: "GET",
        url: '/production/getAllRecipes',
        success: function(products) {
            $.each(products, function(index, product) {
                $('#recipeInput').append('<option value="' + product.id + '">' + product.name + '</option>');
            });
        },
        error: function(error) {
            console.log('Error fetching recipes:', error);
            alert("An error occurred. Please try again.");
        }
    });
}*/

function getAllRecipes() {
    $.ajax({
        type: "GET",
        url: '/production/getAllRecipes',
        success: function(recipes) {
            // Clear existing options first
            $('#recipeInput').empty().append('<option value="">Select a Recipe</option>');
            $.each(recipes, function(index, recipe) {
                $('#recipeInput').append('<option value="' + recipe.product.id + '">' + recipe.product.name + '</option>');
            });
        },
        error: function(error) {
            console.log('Error fetching recipes:', error);
            alert("An error occurred. Please try again.");
        }
    });
}

//This function the function of the same name in the ProductionController to add a Batch.
function addBatch(batch) {
    $.ajax({
        type: "POST",
        url: '/production/addBatch',
        contentType: 'application/json',
        data: JSON.stringify(batch),
        success: function(response) {
            console.log('Batch added successfully:', response);

            //This part resets the inputs.
            $('#recipeInput').val('');
            $('#quantityInput').val('');
            $('#priorityInput').val('');
        },
        error: function(error) {
            console.log('Error adding batch:', error);
            alert("An error occurred. Please try again.");
        }
    });
}