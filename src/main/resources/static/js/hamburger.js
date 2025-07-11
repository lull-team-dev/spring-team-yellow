/**
 * 
 */

const icon = document.getElementById("hamburger-icon");
const nav = document.getElementById("hamburger-nav",)
icon.addEventListener('click',() =>{
	nav.classList.toggle('active');
	icon.classList.toggle('active');

});