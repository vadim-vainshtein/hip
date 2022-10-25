function expand(id) {
    let dWrapper = document.getElementById("d_wrapper" + id);

    if(window.getComputedStyle(dWrapper).display == "none") {
        dWrapper.style.display = "inline-block";
    } else {
        dWrapper.style.display = "none";
    }
}
