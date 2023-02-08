window.onload = function() {
  document.querySelector(".drop_btn")
  .addEventListener("click", function () {
    document.getElementById("drop_menu")
      .classList
      .toggle("show")
  }); 
}

window.onclick = function(event) {
  if (!event.target.classList.contains("drop_btn")) {
    var menu_list = document.getElementById("drop_menu");
    
    if (menu_list.classList.contains("show")) {
      menu_list
        .classList
        .remove("show");
    }
  }
}