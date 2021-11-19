const PREV_PAGE_BTN = document.getElementById("JSprevpage-btn");
const NEXT_PAGE_BTN = document.getElementById("JSnextpage-btn");

function checkLinkNull(nextPage, prevPage) {

    if(nextPage != "/null") {
        NEXT_PAGE_BTN.style.display = "block";
    }

    if(prevPage != "/null") {
        PREV_PAGE_BTN.style.display = "block";
    }
}


function init() {
    let nextPageLink = NEXT_PAGE_BTN.getAttribute("href");
    let prevPageLink = PREV_PAGE_BTN.getAttribute("href");
    checkLinkNull(nextPageLink, prevPageLink);
}


init();
