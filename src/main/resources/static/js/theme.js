// Fonction pour récupérer la valeur d'un cookie par son nom
function getCookie(name) {
    let cookieArr = document.cookie.split(";");

    // Boucle sur chaque cookie pour le trouver
    for (let i = 0; i < cookieArr.length; i++) {
        let cookie = cookieArr[i].trim();
        // Si le cookie correspond au nom
        if (cookie.startsWith(name + "=")) {
            return cookie.substring(name.length + 1); // Retourne la valeur du cookie
        }
    }

    return null; // Si le cookie n'est pas trouvé
}

// Fonction pour définir un cookie
function setCookie(name, value, days) {
    const date = new Date();
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000)); // Expiration après 'days' jours
    let expires = "expires=" + date.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/"; // Définit le cookie
}

// Charger le thème du cookie au démarrage de la page
window.onload = function () {
    const theme = getCookie("theme");
    if (theme) {
        document.documentElement.setAttribute('data-bs-theme', theme); // Appliquer le thème sauvegardé
    }
};

// Événement de changement de thème
document.getElementById('btnSwitch').addEventListener('click', () => {
    const currentTheme = document.documentElement.getAttribute('data-bs-theme');
    const newTheme = currentTheme === 'dark' ? 'light' : 'dark'; // Changer de thème
    document.documentElement.setAttribute('data-bs-theme', newTheme);

    // Sauvegarder le nouveau thème dans un cookie
    setCookie("theme", newTheme, 7); // Le cookie expirera dans 7 jours
});


function updateStatusText() {
    const checkbox = document.getElementById('status');
    const statusText = document.getElementById('statusText');
    statusText.innerText = checkbox.checked ? 'Disponible' : 'Non Disponible';
}