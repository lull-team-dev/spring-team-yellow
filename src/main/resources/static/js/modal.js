/**
 * 
 */
document.addEventListener("DOMContentLoaded", function() {
    const errorMsg = document.getElementById("errorMsg").innerHTML;
    if (errorMsg && errorMsg.trim() !== "") {
        document.getElementById("errorModal").style.display = "flex";
    }
});

function closeModal() {
    document.getElementById("errorModal").style.display = "none";
}
