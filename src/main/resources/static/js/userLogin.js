function init() {
    const href = location.href;
    const queryString = href.substring(href.indexOf("?")+1)
    if (queryString === 'error') {
        const errorDiv = document.getElementById('login-failed');
        errorDiv.style.display = 'block';

        setTimeout(function() {
            errorDiv.style.display = "none";
        }, 3500);

        history.pushState(null, null, 'login')
    }
}

init();