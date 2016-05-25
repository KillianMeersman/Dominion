//-----------------GLOBALE VARIABELEN-----------------//
/*var cards = ["adventurer", "bureaucrat", "gold", "silver", "copper", "cellar", "chancellor", "chapel", "councilroom", "feast", "festival", "gardens", "laboratory", "library", "market", "militia", "mine", "moat", "moneylender", "remodel", "smithy", "spy", "thief", "throneroom", "village", "witch", "woodcutter", "workshop"];*/

var cards = [];

var treasureCards = ["copper", "silver", "gold"];
var firstPageHtml = "";
var secondPageHtml = "";
var thirdPageHtml = "";
var loginServlet = "/LoginServlet";
var gameServlet = "/GameServlet";

var bigMoney = ["adventurer", "bureaucrat", "chancellor", "chapel", "feast", "laboratory", "market", "mine", "moneylender", "throneroom"];

//-----------------COMMON FUNCTIONS-----------------//
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function getPreBuiltDeck(name) {
    switch (name.toLowerCase()) {
    case "big money":
        return bigMoney;
    default:
        return null;
    }
}

function removeBrackets(string) {
    return string.slice(1, -1);
}


/*
$.ajax({
    method: "GET",
    url: '/Dominion/GameServlet',
    data: {
        action: "test"
    },
    success: function (request) {

        alert(getParameterByName('action', request));
    },
    error: function (response) {
        console.log(response);
    },
});

*/


var strin = "action=buy&parameters=[4,1,1]&hand=[1,4,12,34,2]";

//-----------------SHOW/HIDE BIJ OPSTART BROWSER-----------------//
$(document).on('ready', function () {
    $("#play").hide();
    $("#save").hide();
    $("#new").hide();
    $("#footer").hide();
    $(".deckBuild").hide();
    $("#next").hide()
    $("#back").hide();
    $("#blackscreen").hide();
    $("#gameTable").hide();
    $("#trashCardsView").hide();
    $("#cardSpectator").hide();
    $("#playerHand").hide();
    $("#playerStats").hide();
    $("#tutorialScreen").hide();
    $("#introTutorial").hide();
    $("#esc").hide();
    $("#playerSelection").hide();

    $("#playerSelection+div").hide();
    $('#revealView').hide();


    console.log("Ajax getCards");
    $.ajax({
        method: "GET",
        url: gameServlet,
        data: {
            action: "getCards"
        },
        success: function (response) {
            response = removeBrackets(response);
            cards = response.split(', ');
            console.log(cards);
        },
        error: function (response) {
            console.log(response);
        },
    });

});
$(document).on("contextmenu", function () {
    return false;
});

//-----------------LOGIN SCREEN-----------------//
function ajaxAuthentication(action) {
    $.ajax({
        method: "POST",
        url: loginServlet,
        data: {
            username: $("#username").val(),
            password: $("#password").val(),
            action: action
        },
        success: function (response) {

            if ((response == "200_good_login") || (response == "user_registered")) {
                $("#authenticationScreen").fadeOut("fast", function () {
                    $("#play").fadeIn("fast");
                })
            } else {
                alert(response);
            }
        },
        error: function (response) {
            alert(response);
        },

        dataType: "text"


    });


}


$("#login").on("click", function () {
    console.log("login");
    ajaxAuthentication("login");

});
$("#login+a").on("click", function () {
    console.log("register");
    $("#authenticationScreen").fadeOut("fast", function () {
        $("#play").fadeIn("fast");
    })

    //ajaxAuthentication("register");
});
//-----------------LOGIN SCREEN-----------------//
function ajaxBasicGet(data) {
    $.ajax({
        method: "GET",
        url: gameServlet,
        data: data,
        success: function (response) {
            console.log(response);
            procesAjax(response);
        },
        error: function (response) {
            console.log(response);
        },
    });
}



//-----------------MAIN MENU BUILD + FUNCTIES-----------------//

$("#newGame").on("click", function () {
    $("#save").hide("fast");
    $("#new").toggle("fast");
    console.log("save hidden, newGame toggled");

});

$("#saveGame").on("click", function () {
    $("#new").hide("fast");
    $("#save").toggle("fast");
    console.log("newGame hidden, saves toggled");
});

$("#tutorial").on("click", function () {
    chosenCards = ["militia", "mine", "moat", "moneylender", "remodel", "smithy", "spy", "thief", "throneroom", "village"];
    amountPlayers = ["T-Dawg4win", "RubinNub", "Killiwin", "SeppScrub"];
    initPlayerNames();
    $("#blackscreen").fadeIn(500, function () {
        $(".start").fadeOut(500, initBoard());

        $("#tutorialScreen").fadeIn(200);
        $("#introTutorial").fadeIn(200);
    });

    $(".menu").fadeOut(200);
    $("#new").fadeOut(200);
    $("#save").fadeOut(200);
    $("#tutorialScreen").focus();
    tutorialStage = 0
    cardsHere = false;

});

//player selection
var deckName
$("#new a").on("click", function () {

    deckName = $(this).attr("id");



    $(".menu").fadeOut(200);
    $("#new").fadeOut(200);
    $("#save").fadeOut(200);
    $("#playerSelection").fadeIn();
});





var activeSelectionTab = false;
var amountPlayers = 0;
$("#playerSelection a").on("click", function () {

    htmlPlayerName = "";
    if (amountPlayers != parseInt($(this).html().slice(0, 1))) {

        amountPlayers = parseInt($(this).html().slice(0, 1));
        for (i = 1; i < (amountPlayers + 1); i++) {


            htmlPlayerName += "<label for='player" + i + "'>Player " + i + "'s name:</label><input id='inputPlayerName" + i + "' name='player" + i + "' type='text' maxLength='11'>"


        };


        if (activeSelectionTab == false) {

            $("#playerSelection+div>div").empty();
            $("#playerSelection+div>div").append(htmlPlayerName);
            $("#playerSelection+div").fadeIn(200);
            activeSelectionTab = true;
        } else {
            $("#playerSelection+div").fadeOut(200, function () {
                $("#playerSelection+div>div").empty();
                $("#playerSelection+div>div").append(htmlPlayerName);
                $("#playerSelection+div").fadeIn(200);
                $("#playerSelection+div a").hide();
                $("#playerSelection+div").css("height", "auto");
            });
        }
    }





});
var playerNames = [];
var activePlay = false;
$("#playerSelection+div>div").on("keyup", "input", function () {
    allFilled = true;

    for (i = 1; i < (amountPlayers + 1); i++) {

        if (document.getElementById("inputPlayerName" + i).value == "") {

            allFilled = false;
            break;
        } else {
            allFilled = true;

        }
    };
    if (allFilled == true) {

        if (activePlay == false) {
            $("#playerSelection+div").animate({
                height: "+=35px"

            });
            activePlay = true;
        }

        $("#playerSelection+div a").fadeIn();
    } else if (activePlay == true) {


        $("#playerSelection+div").animate({
            height: "-=35px"


        });
        $("#playerSelection+div a").fadeOut();
        activePlay = false;


    }



});
var deck = [];
var deckNames = [];
$("#playerSelection+div").on("click", "a", function () {
    //chosenCards = ["militia", "mine", "moat", "moneylender", "remodel", "smithy", "spy", "thief", "throneroom", "village"];

    console.log("Ajax give deckName and playernames to server");

    for (i = 0; i < amountPlayers; i++) {
        playerNames[i] = document.getElementById("inputPlayerName" + (i + 1)).value;
    }
    deckNames = getPreBuiltDeck("Big money"); // VERANDEREN!!!


    for (i = 0; i < deckNames.length; i++) {
        console.log(cards.indexOf(deckNames[i]));
        deck[i] = cards.indexOf(deckNames[i])
        chosenCards[i] = deckNames[i];
    }

    ajaxBasicGet({
        deck: JSON.stringify(deck).replace("[", "").replace("]", ""),
        playernames: JSON.stringify(playerNames).replace("[", "").replace("]", ""),
        action: "new",
        success: function(response) {
            console.log(response);
        }
    });
    $("#blackscreen").fadeIn(500, function () {
        $(".start").fadeOut(500, initBoard())
    });
    $(".menu").fadeOut(200);

    $("#new").fadeOut(200);
    $("#save").fadeOut(200);
});

$("#firstGame").on("click", function () {
    chosenCards = ["militia", "mine", "moat", "moneylender", "remodel", "smithy", "spy", "thief", "throneroom", "village"];



    $("#blackscreen").fadeIn(500, function () {
        $(".start").fadeOut(500, initBoard())
    });
    $(".menu").fadeOut(200);

    $("#new").fadeOut(200);
    $("#save").fadeOut(200);


});

//-----------------TURORIAL-----------------//
var tutorialStage = 0
var cardsHere = false;

function changeStageVisual(stage) {
    $("#tips div:nth-of-type(" + (stage - 1) + ")").fadeOut();
    $("#tips div:nth-of-type(" + (stage) + ")").fadeIn();
    switch (stage) {
    case 1: //action Cards

        $("#introTutorial").fadeOut();
        $("#tutorialScreen").animate({
            width: '971px',
            height: '346px',
            left: '-1525px',
            top: '-1975px'
        });
        break;
    case 2: //victory cards
        $("#tutorialScreen").animate({
            width: '168px',
            height: '482px',
            left: '-501px',
            top: '-1965px'
        });
        break;

    case 3: //treasure Cards
        $("#tutorialScreen").animate({
            width: '168px',
            height: '482px',
            left: '-316px',
            top: '-1965px'
        });
        break;

    case 4: //curse card
        $("#tutorialScreen").animate({
            width: '168px',
            height: '154px',
            left: '-316px',
            top: '-1473px'
        });
        break;
    case 5: //action Cards
        $("#tutorialScreen").animate({
            width: '160px',
            height: '154px',
            left: '-498px',
            top: '-1473px'
        });
        break;

    case 6: //end turn

        $("#tutorialScreen").animate({
            top: '-1257px',
            width: '152px',
            height: '220px',
            left: '-282px'

        });
        break;


    case 7: //trash
        $("#tutorialScreen").animate({
            width: '267px',
            height: '154px',
            left: '-575px',
            top: '-1140px'
        });
        break;


    case 8: //HAND//
        if (cardsHere == false) {
            addCardToHand("moat")
            addCardToHand("thief")
            addCardToHand("village")
            addCardToHand("gardens")
            addCardToHand("spy")
            addCardToHand("throneroom")
            cardsHere = true;
        };
        $("#tutorialScreen").animate({
            width: '500px',
            height: '220px',
            left: '-1290px',
            top: '-1222px',
        });

        break;

    case 9:
        $("#tutorialScreen").animate({
            width: '194px',
            height: '80px',
            left: '-1909px',
            top: '-1100px',
        });
        break;


    case 10:
        $("#tutorialScreen").animate({
            width: '79px',
            height: '225px',
            left: '-2000px',
            top: '-1325px',
        });
        break;

    case 11:
        $("#playerStats")
            .animate({
                top: '-236px'
            });
        $("#tutorialScreen").animate({
            width: '419px',
            height: '297px',
            left: '-1980px',
            top: '-2000px',
        });
        break;

    default:
        $("#blackscreen").fadeIn(500, function () {
            $("#gameTable").fadeOut(500, denitBoard());
            $("#tutorialScreen").animate({
                width: '0px',
                height: '0px',
                left: '0px',
                top: '0px',
            });
        });
        break;
    }
};

$("#tutorialScreen").bind("keydown", function (event) {

    if (event.keyCode == 39) {
        tutorialStage++;
        changeStageVisual(tutorialStage);

    } else if (event.keyCode == 37) {

        $("#tips div:nth-of-type(" + (tutorialStage) + ")").fadeOut();
        tutorialStage--;


        changeStageVisual(tutorialStage);

    };

});


$("#tutorialScreen").on("click", function () {
    tutorialStage++;
    changeStageVisual(tutorialStage);
});

//-----------------DECKBUILDER-----------------//
var firstTimeDeckBuilder = true;

function initDeckBuilder() {
    for (i = 0; i < 10; i++) {
        firstPageHtml += "<a id='" + cards[i] + "'  class='buildCard' href='#'><img src='images/ActionCards/" + cards[i] + ".jpg'/></a>";
    }

    $("#viewCards").append(firstPageHtml);
    for (i = 10; i < 20; i++) {
        secondPageHtml += "<a id='" + cards[i] + "' class='buildCard' href='#'><img src='images/ActionCards/" + cards[i] + ".jpg'/></a>";
    }


    for (i = 20; i < 25; i++) {
        thirdPageHtml += "<a id='" + cards[i] + "'  class='buildCard' href='#'><img src='images/ActionCards/" + cards[i] + ".jpg'/></a>";
    }
};

$("#new > :last-child").on("click", function () {
    $(".menu").fadeOut("fast");
    $("#back").fadeIn("fast");
    $("#new").fadeOut("fast");
    $("#save").fadeOut("fast");
    $(".deckBuild").fadeIn("fast");
    $("#footer").fadeIn("fast");

    if (firstTimeDeckBuilder == true) {
        initDeckBuilder();
        firstTimeDeckBuilder = false;
    }
});

//-----------------NEXT ARROW FUNCTIE-----------------//
var pageDeckBuilder = 1;

function nextPage() {
    if (pageDeckBuilder == 3) {
        pageDeckBuilder = 1;
    } else {
        pageDeckBuilder++;
    }
    switch (pageDeckBuilder) {
    case 1:
        $("#cardBookFooter p").empty().append("Page 1 of 3");
        $("#viewCards").empty().append(firstPageHtml);
        break;
    case 2:
        $("#cardBookFooter p").empty().append("Page 2 of 3");
        $("#viewCards").empty().append(secondPageHtml);
        break;
    case 3:
        $("#cardBookFooter p").empty().append("Page 3 of 3");
        $("#viewCards").empty().append(thirdPageHtml);
        break;
    }



}
$("#nextArrow").on("click", function () {
    nextPage();

});

//-----------------PREVIOUS ARROW FUNCTIE-----------------//

function previousPage() {
    if (pageDeckBuilder == 1) {
        pageDeckBuilder = 3;
    } else {
        pageDeckBuilder--;
    }
    switch (pageDeckBuilder) {
    case 1:
        $("#cardBookFooter p").empty().append("Page 1 of 3");
        $("#viewCards").empty().append(firstPageHtml);
        break;
    case 2:
        $("#cardBookFooter p").empty().append("Page 2 of 3");
        $("#viewCards").empty().append(secondPageHtml);
        break;
    case 3:
        $("#cardBookFooter p").empty().append("Page 3 of 3");
        $("#viewCards").empty().append(thirdPageHtml);
        break;
    }

}
$("#previousArrow").on("click", function () {
    previousPage();
});

//-----------------DECK BUILDER ALGORITMES + DYNAMISCHE OPBOUW HTML-----------------//

var chosenCards = [];
var numberCardsInDeck = 0;

$("#viewCards").on("click", "a.buildCard", function () {

    if (numberCardsInDeck >= 10) {
        alert("You already selected 10 cards");

    } else if (chosenCards.indexOf($(this).attr("id")) == -1 && numberCardsInDeck <= 10) {


        chosenCards.push($(this).attr("id"));
        numberCardsInDeck++;

        $("#viewDeck").append("<a id='" + $(this).attr("id") + "2'  class='deckCard' href='#'><img src='images/ActionCards/" + $(this).attr("id") + ".jpg'/></a>");
        if (numberCardsInDeck == 10) {
            $("#next").fadeIn(200);
        }
    }
});

$("#viewDeck").on("click", "a.deckCard", function () {
    $(this).fadeOut(50);

    var removeID = "#" + $(this).attr("id");
    var index = chosenCards.indexOf($(this).attr("id").substring(0, $(this).attr("id").length - 1));
    console.log(index);
    chosenCards.splice(index, 1);
    $("#viewDeck").remove(removeID);

    console.log(removeID);
    console.log(chosenCards);
    numberCardsInDeck--;
    $("#next").fadeOut();


});


$("#next").on("click", function () {

    $("#playerSelection").fadeIn();

    console.log(chosenCards);

});

$("#back").on("click", function () {

    $(".start").fadeIn(200);
    $("#save").hide();
    $("#new").show();
    $("#footer").fadeOut(200);
    $(".deckBuild").fadeOut(200);
    $("#next").hide();
    $("#playerSelection").fadeOut();


});

//-----------------FUNCTIE VOOR VULLEN VAN BOARD-----------------//
function initBoard() {


    $("body").css("background-image", "url(images/tableBoard.png)")
    $("#gameTable").fadeIn(1);
    $("#playerHand").fadeIn(1);
    $("#playerStats").fadeIn(1);
    actionCardsHtml = "";
    buyButtonHtml = "";
    amountCardsLeft = "";
    for (i = 0; i < 10; i++) {
        var cardID = chosenCards[i] + "Buy";
        actionCardsHtml += "<a id='" + cardID + "'  class='buyActionCards' href='#'><img src='images/ActionCardsBuy/" + cardID + ".png'/></a>";
        buyButtonHtml += "<a id='" + cardID + "Button' class='buttonBuyDesign buttonActionCards' href='#'>+</a>";
        amountCardsLeft += "<p id='" + cardID + "Amount' class='amountCardsDesign amountActionCards'>50</p>";

    }


    fillNames();

    console.log(actionCardsHtml);
    actionCardsHtml += buyButtonHtml + amountCardsLeft;
    $("#actionCardsBuy").append(actionCardsHtml);

    $("#blackscreen").fadeOut(500);


    //todo
    /*startingHand = ajaxBasicGet({
        action:
    });*/

}

function fillNames() {

    $("#turnIndicator p").html(playerNames[0]);
    for (i = 1; i < amountPlayers; i++) {
        $("#p" + i + ", #p" + i + " img").removeClass("invisible");
        $("#p" + i + "+img").removeClass("invisible");
        $("#p" + i).html(playerNames[i]);


    }
    switch (amountPlayers) {
    case 2:
        $("#p1").addClass("player1");
        break;

    case 3:

        $("#p1").addClass("player2");
        $("#p2").addClass("player2");
        break;

    case 4:
        $("#p1").addClass("player3");
        $("#p2").addClass("player3");
        $("#p3").addClass("player3");
        break;

    }

}

function refreshNames(newPlayer) {
    $('#turnIndicator p').fadeOut(500, function () {
        $('#turnIndicator p').html(newPlayer);
        $('#turnIndicator p').fadeIn();
    });

    for (i = 1; i < amountPlayers; i++) {
        if ($("#p" + i).html() == newPlayer) {
            $("#p" + i).html($('#turnIndicator p').html());
            return $("#p" + i + "+img").attr("id");
        }
    }


}


function denitBoard() {
    $("body").css("background-image", "url(images/bg.jpg)")
    $("#gameTable").fadeOut();
    $("#tutorialScreen").fadeOut(200);
    $("#introTutorial").fadeOut(200);
    $("#playerHand").empty();
    $("#playerStats").animate({
        top: '-464px'
    });
    $("header").fadeIn();
    $("#cardField").empty();
    $("#actionCardsBuy").empty();
    $("#deckPile div").empty();
    $(".main").fadeIn();
    $("#blackscreen").fadeOut(1500);
    amountPlayers = 0
    playerNames = [];
    playerHand = [];
    fieldCards = [];
    overalCardID = 0;
    pxFromLeftHand = 875;
    pxOldCardsLeft = 950;
    pxFromLeftField = 450;
    zindex = 1;
    pxFieldReform = 950;
    zIndexDiscardPile = 1


    $("#p1").removeClass();
    $("#p2").removeClass();
    $("#p3").removeClass();
    $("#p2").addClass("invisible");
    $("#p2+img").css("display", "none");
    $("#p3").addClass("invisible");
    $("#p3+img").css("display", "none");
    playerNames.length = 0;
}

//-----------------CLICK ACTIONS OP DE BUY KNOPPEN-----------------//

function showCardSpectateMode(cardName) {
    $("#cardSpectator img").attr("src", "images/ActionCards/" + cardName + ".jpg");
    $("#cardSpectator").fadeIn("fast");
};




$("#actionCardsBuy").on("click", "a.buyActionCards", function () {
    showCardSpectateMode($(this).attr("id").replace("Buy", ""));


});

$("#cardSpectator").on("click", function () {

    $("#cardSpectator").fadeOut("fast");

});

$("a.buyVictoryCards").on("click", function () {
    $("#cardSpectator img").attr("src", "images/VictoryCards/" + $(this).attr("id") + ".jpg");
    $("#cardSpectator").fadeIn("fast");

});

$("a.buyTreasureCards").on("click", function () {
    $("#cardSpectator img").attr("src", "images/TreasureCards/" + $(this).attr("id") + ".jpg");
    $("#cardSpectator").fadeIn("fast");

});

//----BUY CARD----//
var cardBuyDestination = "discard";

function generateBuyCard(el) {
    htmlBoughtCard = '<img id="b' + zIndexDiscardPile + '"class="boughtCard" style="bottom: ' + (($(window).height() - el.offset().top - el.height())) + 'px; left: ' + el.position().left + 'px;" src="images/ActionCards/' + el.attr("id").replace("BuyButton", ".jpg") + '">';
    $("#cardField").append(htmlBoughtCard);
}


//permission BUY card
$("#gameTable").on("click", "a.buttonBuyDesign", function () {
    alert($(this).attr("id").replace("BuyButton", ""));
    console.log(cards);
    $.ajax({
        method: "GET",
        url: gameServlet,
        data: {
            action: "play",
            cardId: cards.indexOf($(this).attr("id").replace("BuyButton", ""))
        },
        success: function (response) {
            changeMessage(response);
            generateBuyCard($(this));
            var card = {
                name: $(this).attr("id").replace("BuyButton", ""),
                id: overalCardID
            };
            if (cardBuyDestination == "discard") {

                $("#b" + zIndexDiscardPile).animate({
                    width: '60px',
                    bottom: '192px',
                    left: '10px'
                }).css("z-index", zIndexDiscardPile);
                $("#b" + zIndexDiscardPile).addClass("discarted")


            } else if (cardBuyDestination == "hand") {

                var card = {
                    name: $(this).attr("id").replace("BuyButton", ""),
                    id: overalCardID

                };

                playerHand.push(card);
                overalCardID++;

                $("#b" + zIndexDiscardPile).addClass("cardInHand").attr("id", card.id);
                cardBuyDestination = "discard";

                pxFromLeftHand += 20;
                $("#" + card.id).animate({
                    bottom: '-65px',
                    left: pxFromLeftHand + 'px',
                    width: '160px'

                });

                reformHand();
                cardBuyDestination = "discarted";
            }

            zIndexDiscardPile++;

        }

        ,
        error: function (response) {
            console.log(response)
        }
    })
});

/*
if (ajaxBasicGet({
        action: "buy",
        card: cards.indexOf($(this).attr("id").replace("BuyButton", ""))
    }) == "true") {
    generateBuyCard($(this));
    var card = {
        name: $(this).attr("id").replace("BuyButton", ""),
        id: overalCardID

    };
};




if (cardBuyDestination == "discard") {

    $("#b" + zIndexDiscardPile).animate({
        width: '60px',
        bottom: '192px',
        left: '10px'
    }).css("z-index", zIndexDiscardPile);
    $("#b" + zIndexDiscardPile).addClass("discarted")


} else if (cardBuyDestination == "hand") {

    var card = {
        name: $(this).attr("id").replace("BuyButton", ""),
        id: overalCardID

    };

    playerHand.push(card);
    overalCardID++;

    $("#b" + zIndexDiscardPile).addClass("cardInHand").attr("id", card.id);
    cardBuyDestination = "discard";

    pxFromLeftHand += 20;
    $("#" + card.id).animate({
        bottom: '-65px',
        left: pxFromLeftHand + 'px',
        width: '160px'

    });

    reformHand();
}

zIndexDiscardPile++;
*/



//-----------------ESCAPE MENU----------------//
function toggleEscapeMenu() {
    $("#esc div").css("display", "block");
    $("#esc").fadeToggle(200);
}
$("#forfeit").on("click", function () {


    $("#blackscreen").fadeIn(500, function () {
        toggleEscapeMenu();
        $("#gameTable").fadeOut(500, denitBoard());
    });
})

$("#forfeit + a").on("click", function () {
    $("#blackscreen").fadeIn(500, function () {
        toggleEscapeMenu();
        $("#gameTable").fadeOut(500, denitBoard());
    });
})

$("#forfeit + a + a").on("click", function () {


    toggleEscapeMenu();
});



$(document).keyup(function (e) {
    //esc
    switch (e.keyCode) {
    case 27: //action Cards
        if ($("#gameTable").css("display") != "none") {
            toggleEscapeMenu();
        }

        break;

    case 39:
        if ($("#cardBook").css("display") != "none") {
            nextPage();

        }

        break;
    case 37:
        if ($("#cardBook").css("display") != "none") {
            previousPage();

        }
        break;
    }




});


//-----------------BOARD ANIMATIONS-----------------//
var playerHand = [];
var fieldCards = [];
var overalCardID = 0;




//-------HAND ANIMATIONS-------//
var pxFromLeftHand = 875;

function addCardToHand(cardName) {
    var card = {
        name: cardName,
        id: overalCardID

    };
    playerHand.push(card);
    overalCardID += 1;
    cardHtml = '<img id="' + card.id + '" src="images/ActionCards/' + card.name + '.jpg" class="cardInHand" style="position: fixed; bottom: 75px; left: 7px; width: 68px; border-radius: 5px; ">';

    $("#cardField").append(cardHtml);
    pxFromLeftHand += 20;



    $("#" + card.id).animate({
        bottom: '-65px',
        left: pxFromLeftHand + 'px',
        width: '160px'

    });


    reformHand();
};

function addExistingCardToHand(card) {

    playerHand.push(card);
    pxFromLeftHand += 20;
    $("#" + card.id).animate({
        bottom: '-65px',
        left: pxFromLeftHand + 'px',
        width: '160px'

    });
    $("#" + card.id).addClass("cardInHand");
    reformHand();
}

var pxOldCardsLeft = 950;


function reformHand() {

    pxOldCardsLeft += 25
    var selectedCard = playerHand.length;
    for (i = 0; i < (playerHand.length); i++) {
        var leftValue
        if (i == 0) {
            leftValue = pxOldCardsLeft - (25 * (playerHand.length - 0.5) * 2.5);
        };
        leftValue = pxOldCardsLeft - (25 * (playerHand.length - i) * 2.5);

        $("#" + playerHand[i].id).animate({
            left: leftValue
        }, "fast");
    };
};


function discardCard(cardID) {
    playerHand.splice(searchCardsInHand($("#" + cardID).attr("id")), 1);
    $("#" + cardID).animate({
        width: '60px',
        bottom: '192px',
        left: '10px'
    }).css("z-index", zIndexDiscardPile);
    $("#" + cardID).addClass("discarted");
    zIndexDiscardPile++;

    reformHand();
};


function discardCardFromAny(cardID) {

    $("#" + cardID).animate({
        width: '60px',
        bottom: '192px',
        left: '10px'
    }).css("z-index", zIndexDiscardPile);
    $("#" + cardID).addClass("discarted");
    zIndexDiscardPile++;

}

//-------FIELD CARDS ANIMATIONS-------//
var pxFromLeftField = 450;
var zindex = 1;

function addCardToPlayField(cardID) {



    pxFromLeftField += 65;
    $("#" + cardID).animate({
        bottom: '320px',
        left: pxFromLeftField + 'px',
        width: '120px'

    });
    $("#" + cardID).css("z-index", zindex)
    zindex += 1;

    playerHand.splice(searchCardsInHand($("#" + cardID).attr("id")), 1);
    console.log(playerHand)
    $("#" + cardID).removeClass("cardInHand").css("border-color", "").css("box-shadow", "");
    fieldCards.push($("#" + cardID).attr("id"));

};


var pxFieldReform = 950;

function reformFieldCards() {
    pxFieldReform += 25
    var selectedCard = playerHand.length;
    for (i = 0; i < (playerHand.length); i++) {
        var leftValue = pxFieldReform - (25 * (playerHand.length - i) * 2.5);
        $("#" + playerHand[i].id).animate({
            left: leftValue
        });
    };
};

var zIndexDiscardPile = 1

function fieldCardsToDiscardPile() {
    for (i = 0; i < fieldCards.length; i++) {
        $("#" + fieldCards[i]).animate({
            width: '60px',
            bottom: '192px',
            left: '10px'
        }).css("z-index", zIndexDiscardPile);
        $("#" + fieldCards[i]).addClass("discarted")
        zIndexDiscardPile++;
    };
    pxFromLeftField = 450;
};



function searchCardsInHand(idToSearch) {
    var foundPosition = -1,
        len = playerHand.length;
    for (var i = 0; i < len; i++) {
        if (playerHand[i].id == idToSearch) {
            foundPosition = i;
        }
    };
    return foundPosition;
};






var selectedCards = []
    //-------FIELD EVENST-------//
var twice = false;
var destinationCardInHand = "field";
$("#cardField").on("click", "img.cardInHand", function () {

    //hand-click events

    cardId = cards.indexOf($(this).attr('src').replace('images/ActionCards/', '').replace('.jpg', ''));
    switch (destinationCardInHand) {

    case "field":

        $.ajax({
            method: "GET",
            url: gameServlet,
            data: {
                action: "play",
                cardId: cardId
            },
            success: function (response) {
                if (response == "invalid_card") {
                    changeMessage(response);

                } else {


                    addCardToPlayField($(this).attr("id"));
                }
            },
            error: function (response) {
                console.log(response);
            },
        });


        if (twice != true) {
            addCardToPlayField($(this).attr("id"));


            $.ajax({
                method: "GET",
                url: gameServlet,
                data: {
                    action: "play",
                    cardId: cardId
                },
                success: function (response) {
                    if (response == "invalid_card") {
                        changeMessage(response);

                    } else {


                        playTwice($(this).attr("id"));
                        twice = false;
                    }
                },
                error: function (response) {
                    console.log(response);
                },
            });


        }


    case "trash":
        selectedCards.push(cardId);
        cardToTrash($(this).attr("id"));
        cardsClicked++;
        if (maxAmountClick == cardsClicked) {
            destinationCardInHand = "field";
            removeColorEffects();
            cardsClicked = 0;
        }
        break;

    case "discard":
        selectedCards.push(cardId);
        discardCard($(this).attr("id"));
        cardsClicked++;
        if (maxAmountClick == cardsClicked) {
            destinationCardInHand = "field";
            removeColorEffects();
            cardsClicked = 0;
        }



    }
    //reformFieldCards();
    pxOldCardsLeft = 950;
    pxFromLeftHand = 875;
    reformHand();




});

$("#cardField").on("contextmenu", "img.cardInHand", function () {
    showCardSpectateMode(playerHand[searchCardsInHand($(this).attr("id"))].name);
    return false;
});









//temp
$("#cover").on("click", function () {
    fieldCardsToDiscardPile();

});
$("#playXXX").on("click", function () {
    showSpy("copper", "Thief player 1");

    for (i = 0; i < playerHand.length; i++) {
        if (playerHand[i].name == "copper" || playerHand[i].name == "silver" || playerHand[i].name == "gold") {

            addCardToPlayField(playerHand[i].id);
            i--;
        };

    }

});
$("#endTurn").on("click", function () {
    if ($("#endTurn").html() == "End Action" && cardsClicked >= minAmountClick) {

        $("#endTurn").html("End Turn");

        //todo ajax

    } else {

        endTurn()
    }


    discardCard(6);
});

var cardPick = 0;
$("a.buttonBuyDesign").on("click", function () {

    addCardToHand(cards[cardPick]);
    cardPick += 1;


});
$("#firstOnPile").on("click", function () {
    $("#trashCardsView").fadeIn();

});
$("#trashCardsView").on("click", function () {
    $(this).fadeOut();

});



//-------PLAYER STATS-------//
/*var state = "up"
$("#playerStats").on("click", function () {
    if (state == "resting") {
        state = "down";

    } else if (state == "up") {
        $(this).animate({
            top: '-236px'
        });
        state = "down";

    } else if (state == "down") {
        $(this).animate({
            top: '-464px'
        });
        state = "up";

    }

});*/

$("h3+img").on("click", function () {
    state = "resting";
    showCardSpectateMode($(this).attr("id"));
})

//-------SPECIAL FUNCTIONS-------//
$("#curseBuyButton").on("click", function () {

    giveCurseCard();

});

//TRASH FUNCTIONS
function cardToTrash(cardID) {
    playerHand.splice(searchCardsInHand($("#" + cardID).attr("id")), 1);
    $("#" + cardID).animate({
        width: '90px',
        bottom: '41px',
        left: '1745px',
        br: '10 px',
        zdex: '499'
    }).removeClass("cardInHand");
}

function handDiscardModudesOn() {
    destinationCardInHand = "discard";
    for (i = 0; i < playerHand.length; i++) {

        $("#" + playerHand[i].id).css("border-color", "GoldenRod").css("box-shadow", "0 0 30px GoldenRod");
    }



}
var clickedCards = 0;
var pickableCards = [];


function checkHandCards(NewHand) {


    for (i = (playerHand.length - 1); i < NewHand.length; i++) {

        addCardToHand(cards[NewHand[i]]);
    }
}


var parameters = [];

function updateParams(params) {

    $("#amountActions").html(params[0]);
    $("#amountBuys").html(params[1]);
    $("#amountCoins").html(params[2]);



}
var firstTurn = true;

function procesAjax(parameterString) {


    switch (getParameterByName('action', parameterString)) {



    case "player":

        if (firstTurn == false) {
            endTurn(getParameterByName('playerName', parameterString));


        } else {
            firstTurn = false;
        }

        checkHandCards(getParameterByName('hand', parameterString));
        break;

    case "buy":
        $(".amountActionCards").fadeOut(300, function () {
            fillSupply(JSON.parse(getParameterByName('playerName', parameterString)))
            $(".amountActionCards").fadeIn(300);

        });
        updateParams(JSON.parse(getParameterByName('parameters', parameterString)));


        //params a b c hand


        break;



    case "action":
        updateParams(JSON.parse(getParameterByName('parameters', parameterString)));


        checkHandCards(getParameterByName('hand', parameterString));
        break;

    case "promptCards":
        tempArray = getParameterByName('cards').split(',');
        promtCards(getParameterByName('description', parameterString), tempArray, parameterString), getParameterByName('minAmount', parameterString), getParameterByName('maxAmount', parameterString), getParameterByName('canExit', parameterString), getParameterByName('visual', parameterString);

        break;






    }



}



function promtCards(description, cards, minAmount, maxAmount, canExit, visual) {
    changeMessage(description);


    if (canExit == true) {
        $("#endTurn").html("End Action");
    }
    switch (visual) {

    case "spy":

        showSpy(cards, description);

        break;


    case "thief":
        showThief(cards, description);
        break;


    case "bureaucrat":
        revealCards(cards, description, false);
        break;


    case "militia":
        revealCards(cards, description, true);
        break;


    case "discard":
        handDiscardModudesOn();
        maxAmountClick = maxAmount;
        minAmountClick = minAmount;

        break;
    default:
        revealCards(cards, description, true);

    case "trash":
        handTrashModudesOn(cards);
        maxAmountClick = maxAmount;
        minAmountClick = minAmount;


        break;
    }




    //action=reveal&parameters=[These are player 1's top 2 cards, [1,24]]
    //action=animation&parameters=[2,deck,hand]





}




function handleAjaxCall(obj) {
    if (obj.action == "animation") {
        switch (obj.action) {
        case obj.action == "addCardToHand":
            addCardToHand(cards[obj.cardID]);
            break;

        case obj.action == "deckToDiscard":
            completeDeckToDiscardPile();

            break;
        case obj.action == "giveCurseCard":
            giveCurseCard();

            break;
        case obj.action == "addSilverToDeck":
            addSilverToDeck();
            break;

        case obj.action == "fieldCardsToDiscard":
            fieldCardsToDiscardPile();

            break;

        }
    }

    switch (obj.action) {
    case obj.action == "showSpy":
        showSpy(obj.cardsToDisplay);
        break;

    case obj.action == "showThief":
        showThief(obj.cardsToDisplay, obj.fromPlayers);
        break;

    case obj.action == "revealCards":
        revealCards(obj.cardsToDisplay, obj.fromPlayers, obj.description);
        break;
    case obj.action == "doAdventureCard":
        loopAdventure(obj.cards)
        break;

    case obj.action == "discardCardsFromHand":
        amountToClick = obj.amountCards;
        handDiscardModudesOn();

        break;
    case obj.action == "trashCardsFromHand":

        break;

    case obj.action == "playCardTwice":
        twice = true;
        break;
    }


}

function handTrashModudesOn(wich) {
    destinationCardInHand = "trash";
    switch (wich) {

    case "all":
        for (i = 0; i < playerHand.length; i++) {

            $("#" + playerHand[i].id).css("border-color", "darkred").css("box-shadow", "0 0 30px darkred");
        }
        break;

    case "treasure":

        for (i = 0; i < playerHand.length; i++) {
            if (treasureCards.indexOf(playerHand[i].name) != -1) {
                $("#" + playerHand[i].id).css("border-color", "darkred").css("box-shadow", "0 0 30px darkred");
            }
        }
        break;
    default:

        for (i = 0; i < playerHand.length; i++) {

            if (wich.indexOf(playerHand[i].name) != -1) {
                $("#" + playerHand[i].id).css("border-color", "darkred").css("box-shadow", "0 0 30px darkred");
            }
        }
        break;

    }
}

function removeColorEffects() {
    $.ajax({
        method: "GET",
        url: gameServlet,
        data: {
            action: "play",
            cardId: JSON.stringify(selectedCards)
        },
        success: function (response) {
            if (response == "error") {
                changeMessage(response);

            } else {

            }
        },
        error: function (response) {
            console.log(response);
        },
    });
    selectedCards = [];
    for (i = 0; i < playerHand.length; i++) {

        $("#" + playerHand[i].id).css("border-color", "").css("box-shadow", "");
    }

}

function handToTrash(cardID) {
    cardToTrash(cardID);
    playerHand.splice(searchCardsInHand($("#" + cardID).attr("id")), 1);
    $("#" + cardID).removeClass("cardInHand")
}

function playTwice(cardID) {
    $("#" + cardID).clone().attr("id", overalCardID).appendTo("#cardField");

    var card = {
        name: overalCardID,
        id: overalCardID

    };
    playerHand.push(card);


    addCardToPlayField(cardID);


    addCardToPlayField(overalCardID);
    overalCardID++;

    pxOldCardsLeft = 950;
    pxFromLeftHand = 875;
    reformHand();
}

function completeDeckToDiscardPile() {
    $("#deckPile>:first-child+img").animate({

        width: '60px',
        bottom: '194px',
        left: '10px',
    }, 300).css("z-index", zIndexDiscardPile);
    zIndexDiscardPile++;

    $(".discarted").delay(300).fadeOut(100, function () {
        $(this).remove();
    });
    $("#deckPile>:first-child+img").delay(500).animate({
        width: '70px',
        left: '6px',
        bottom: '75px'

    });
}

function endTurn(nextPlayer) {
    $(".cardInHand").animate({
        bottom: "-=200px"

    }, 300);
    fieldCardsToDiscardPile();

    $(".discarted").fadeOut();
    $("#cardField").empty();
    $("#cardField").append('<img class="discarted" style="position: fixed; bottom: 192px; left: 10px; width: 60px; z-index: 1;" src="images/ActionCards/' + refreshNames(nextPlayer) + '.jpg">');
    pxOldCardsLeft = 950;
    playerHand = [];
    pxFromLeftHand = 875;

}

function giveCurseCard() {

    for (i = 1; i < amountPlayers; i++) {

        htmlBoughtCard = '<img id="b' + zIndexDiscardPile + '"class="boughtCard" style="bottom: ' + (($(window).height() - $("#curseBuyButton").offset().top - $("#curseBuyButton").height())) + 'px; left: ' + $("#curseBuyButton").position().left + 'px;" src="images/ActionCards/' + $("#curseBuyButton").attr("id").replace("BuyButton", ".jpg") + '">';
        $("#cardField").append(htmlBoughtCard);

        $("#b" + zIndexDiscardPile).animate({
            width: '80px',
            bottom: '1000px',
            left: (40 + (i * 100)) + 'px'
        }, 800);

        zIndexDiscardPile++;

    }


}

function showSpy(cardsToDisplay, description) {

    $("#revealView .cardReveal").empty();

    htmlCardsAndPlayerNames = "<h3>" + description + "</h3>";

    htmlCardsAndPlayerNames += '<div> <h4> </h4><section> <img class="imgCardReaveal" src="images/ActionCards/' + cardsToDisplay + '.jpg"></section></div>'


    //$("#revealView .gameWindow").css("width", (amountPlayers * 320) + "px");
    htmlCardsAndPlayerNames += '<section id="doneSpy" class="done"><p>Done</p></section>';
    $("#revealView .cardReveal").append(htmlCardsAndPlayerNames);
    $("#revealView").fadeIn();
    thiefPhase = "trashPhase";
}

function revealCards(cardsToDisplay, fromPlayers, description) {


    $("#revealView .cardReveal").empty();

    htmlCardsAndPlayerNames = "<h3>" + description + "</h3>";
    for (i = 0; i < fromPlayers.length; i++) {

        htmlCardsAndPlayerNames += '<div> <h4>' + playerNames[fromPlayers[i]] + '</h4><section> <img class="imgRegCardReaveal" src="images/ActionCards/' + cardsToDisplay[i] + '.jpg"></section></div>'

    }

    $("#revealView .gameWindow").css("width", (fromPlayers.length * 320) + "px");
    htmlCardsAndPlayerNames += '<section id="doneRegular" class="done"><p>Done</p></section>';
    $("#revealView .cardReveal").append(htmlCardsAndPlayerNames);
    $("#revealView").fadeIn();
    thiefPhase = "trashPhase";
}

function addCardToDeck(cardID) {

    $("#" + cardID).animate({

        width: '69px',
        left: '6px',
        bottom: '75px'

    }, 800).delay(200).fadeOut(300, function () {
        $(this).remove();

    });
}

function addSilverToDeck() {
    generateBuyCard($("#silverBuyButton"));



    addCardToDeck("b" + zIndexDiscardPile);
    zIndexDiscardPile++;
}

var thiefPhase = "trashPhase";

function showThief(cardsToDisplay, description) {
    $("#revealView .cardReveal").empty();
    htmlCardsAndPlayerNames = "<h3>" + description + "</h3>";
    styleImgOne = "";
    styleImgTwo = "";
    playerNameI = 0;

    for (i = 0; i < cardsToDisplay.length; i += 2) {

        if (treasureCards.indexOf(cardsToDisplay[i]) != -1) {
            styleImgOne = "style='border-color: darkred; box-shadow: 0 0 40px darkred;'"


        } else if (treasureCards.indexOf(cardsToDisplay[i + 1]) != -1) {
            styleImgTwo = "style='border-color: darkred; box-shadow: 0 0 40px darkred;'"
        }




        htmlCardsAndPlayerNames += '<div> <h4></h4><section> <img id="t' + i + '"  class="imgTwoCardReveal" ' + styleImgOne + '  src="images/ActionCards/' + cardsToDisplay[i] + '.jpg" alt="' + cardsToDisplay[i] + '"><img id="t' + (i + 1) + '" class="imgTwoCardReveal"  ' + styleImgTwo + ' src="images/ActionCards/' + cardsToDisplay[i + 1] + '.jpg" alt="' + cardsToDisplay[i + 1] + '"></section></div>'

        playerNameI++;
        $("#revealView .gameWindow").css("width", '390px');
    }

    //$("#revealView .gameWindow").css("width", (fromPlayers.length * 327) + "px");
    htmlCardsAndPlayerNames += '<section id="doneThief" class="done"><p>Done</p></section>';
    $("#revealView .cardReveal").append(htmlCardsAndPlayerNames);
    $("#revealView").fadeIn();

}

function returnThief() {

    trashCardsID = [];
    gainCardsID = [];
    for (i = 0; i < ((amountPlayers - 1) * 2); i++) {
        console.log($("#t" + i).css("border-color"));
        if ($("#t" + i).css("border-color") == "rgb(139, 0, 0)") {
            trashCardsID.push(i);

        } else if ($("#t" + i).css("border-color") == "rgb(0, 100, 0)") {

            gainCardsID.push(i);

        }
    }
    cardsClicked = {
        trash: trashCardsID,
        gain: gainCardsID
    };
    return cardsClicked;

}

function doAdventurer(card, destination) {
    cardHtml = '<img id="' + card.id + '" src="images/ActionCards/' + card.name + '.jpg" style="position: fixed; bottom: 75px; left: 7px; width: 68px; border-radius: 5px; ">';

    $("#cardField").append(cardHtml);
    //show middle
    $("#" + card.id).animate({
        width: '250px',
        left: '835px',
        bottom: '280px'
    }, function () {


        if (destination == "hand") {

            addExistingCardToHand(card);

        } else if (destination == "discard") {
            discardCardFromAny(card.id);

        }


    });

}

function loopAdventure(cardsToDo) {
    for (i = 0; i < cardsToDo.length; i++) {
        card = {
            name: cards[cardsToDo[i].cardID].name,
            id: overalCardID
        };
        overalCardID++;
        doAdventurer(card, cardsToDo[i].destination);

    }
    doAdventurer(card, 'hand');

}

var close = false;

$("section").on("click", "#doneThief", function () {
    if (close) {
        returnThief();
        $("#revealView").fadeOut();
        close = false;
    }
    if (thiefPhase == "trashPhase") {
        $(".cardReveal h3").html("Choose the trashed treasure cards you want to gain.")
        thiefPhase = "gainPhase";

    }
});
$("section").on("click", '#doneRegular', function () {

    $("#revealView").fadeOut();
});

$("section").on("click", "#doneSpy", function () {

    $("#revealView").fadeOut();



});
$(document).mousemove(function () {
    if (thiefPhase == "gainPhase") {
        close = true;
    }
});






$("#revealView").on("click", "img.imgTwoCardReveal", function () {

    if (thiefPhase == "trashPhase") {

        if (thiefPhase == "trashPhase") {
            if (treasureCards.indexOf($(this).attr("alt")) != -1) {
                $(this).parent().addClass("tempClass")
                $(".tempClass img").css("border-color", "").css("box-shadow", "none");
                $(this).parent().removeClass("tempClass");
                $(this).css("border-color", "darkred").css("box-shadow", "0 0 40px darkred");

            }
        }


    } else if (thiefPhase == "gainPhase") {
        if ((treasureCards.indexOf($(this).attr("alt")) != -1)) {

            if ($(this).css("border-color") == "rgb(0, 100, 0)") {

                $(this).css("border-color", "darkred").css("box-shadow", "0 0 40px darkred");

            } else if (($(this).css("border-color") == "rgb(139, 0, 0)")) {

                $(this).css("border-color", "darkgreen").css("box-shadow", "0 0 40px darkgreen");

            }
        }
    }


});
$("#revealView").on("click", "img.imgCardReaveal", function () {

    if ($(this).css("border-color") == "rgb(139, 0, 0)") {
        $(this).css("border-color", "").css("box-shadow", "none");
    } else {


        $(this).css("border-color", "darkred").css("box-shadow", "0 0 40px darkred");

    }


});

function changeMessage(mes) {
    $('#notificationBar p').fadeOut('slow', function () {
        $('#notificationBar p').html(mes);
        $('#notificationBar p').fadeIn('slow');
    });

}

function fillSupply(stashArray) {

    for (i = 0; i < stashArray.length; i++) {

        $("#" + cards[i] + "BuyAmount").html(stashArray[i]);

    }
}