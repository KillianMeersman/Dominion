// Voorbeeld ajax voor Gamecontroller

// start AJAX blok
$.ajax({
    url: "localhost/GameController",
    data: "action=new&users=killian,timo,robin&deck=1,2,3,4,5,6,7,8,9,10"
})
    .complete(completeFunction) // functie voor response
    
    .error(function() {         // functie voor error
       $("outBox").html("error"); 
    });
    
// stop ajax blok
    
function completeFunction(response) {
    $("outBox").html(response);
}
