//-----------------GLOBALE VARIABELEN-----------------//
var cards = ["adventurer", "bureaucrat", "gold", "silver", "copper", "cellar", "chancellor", "chapel", "councilroom", "feast", "festival", "gardens", "laboratory", "library", "market", "militia", "mine", "moat", "moneylender", "remodel", "smithy", "spy", "thief", "throneroom", "village", "witch", "woodcutter", "workshop"];

var firstPageHtml = ""
var secondPageHtml = ""
var thirdPageHtml = ""

//-----------------COMMON FUNCTIONS-----------------//






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
    $("#khalid").hide();
});
$(document).on("contextmenu", function () {
    return false;
});


//-----------------LOGIN SCREEN-----------------//

$("#login").on("click", function () {

    $("#authenticationScreen").fadeOut("fast", function () {
        $("#play").fadeIn("fast");


    });

});
$("#login+a").on("click", function () {

    $("#authenticationScreen").fadeOut("fast");

});

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
$("#nextArrow").on("click", function () {

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
});



//-----------------PREVIOUS ARROW FUNCTIE-----------------//
$("#previousArrow").on("click", function () {
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
    console.log(chosenCards);
    $("#blackscreen").fadeIn(500, function () {
        $(".start").fadeOut(500, initBoard())
    });


});

$("#back").on("click", function () {

    $(".start").fadeIn(200);
    $("#save").hide();
    $("#new").show();
    $("#footer").fadeOut(200);
    $(".deckBuild").fadeOut(200);
    $("#next").hide()



});


//-----------------FUNCTIE VOOR VULLEN VAN BOARD-----------------//
function initBoard() {


    $("body").css("background-image", "url(images/tableBoard.png)")
    $("#gameTable").fadeIn(1);
    $("#playerHand").fadeIn(1);
    $("#playerStats").fadeIn(1);
    var actionCardsHtml = "";
    var buyButtonHtml = "";
    for (i = 0; i < 10; i++) {
        var cardID = chosenCards[i] + "Buy";
        actionCardsHtml += "<a id='" + cardID + "'  class='buyActionCards' href='#'><img src='images/ActionCardsBuy/" + cardID + ".png'/></a>";
        buyButtonHtml += "<a id='" + cardID + "Button' class='buttonBuyDesign buttonActionCards' href='#'>+</a>";
    }
    console.log(actionCardsHtml);
    actionCardsHtml += buyButtonHtml;
    $("#actionCardsBuy").append(actionCardsHtml);

    $("#blackscreen").fadeOut(1500);
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

    playerHand = [];
    fieldCards = [];
    overalCardID = 0;
    pxFromLeftHand = 875;
    pxOldCardsLeft = 950;
    pxFromLeftField = 450;
    zindex = 1;
    pxFieldReform = 950;
    zIndexDiscardPile = 1

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

    if (e.keyCode === 27 && $("#gameTable").css("display") != "none") {
        toggleEscapeMenu();
    }
    if (e.keyCode === 75) {
        $("#khalid").fadeToggle(350);
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

    zIndexDiscardPile++;

    reformHand();
};






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
    $("#" + cardID).removeClass("cardInHand")
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







//-------FIELD EVENST-------//
$("#cardField").on("click", "img.cardInHand", function () {

    addCardToPlayField($(this).attr("id"));

    //reformFieldCards();
    pxOldCardsLeft = 950;
    pxFromLeftHand = 875;
    reformHand();




});

$("#cardField").on("contextmenu", "img.cardInHand", function () {
    showCardSpectateMode(playerHand[searchCardsInHand($(this).attr("id"))].name);
    return false;
});

//----BUY CARD----//

$("#gameTable").on("click", "a.buttonBuyDesign", function () {
    htmlBoughtCard = '<img id="b' + zIndexDiscardPile + '"class="boughtCard" style="top: ' + $(this).position().top + 'px; left: ' + $(this).position().left + 'px;" src="images/ActionCards/' + $(this).attr("id").replace("BuyButton", ".jpg") + '">';
    $("#cardField").append(htmlBoughtCard);
    $("#b" + zIndexDiscardPile).animate({
        width: '60px',
        top: '685px',
        left: '10px'
    }).css("z-index", zIndexDiscardPile);

    zIndexDiscardPile++;

});









//temp
$("#cover").on("click", function () {
    fieldCardsToDiscardPile();

});
$("#playXXX").on("click", function () {
    for (i = 0; i < playerHand.length; i++) {
        if (playerHand[i].name == "copper" || playerHand[i].name == "silver" || playerHand[i].name == "gold") {

            addCardToPlayField(playerHand[i].id);
            i--;
        };

    }

});
$("#endTurn").on("click", function () {
    discardCard(6);
});

var cardPick = 0;
$("a.buttonBuyDesign").on("click", function () {

    addCardToHand(cards[cardPick]);
    cardPick += 1;


});
$("#firstOnPile").on("click", function () {
    $("#trashCardsView").fadeIn();

})
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