function openNav() {
    document.getElementById("SIDENAV").style.width = "250px";
	document.body.style.marginLeft="250px";
	document.getElementById("LOGO").onclick=function(){closeNav();}
}

function closeNav() {
    document.getElementById("SIDENAV").style.width = "0";
	document.body.style.marginLeft="0px";
	document.getElementById("LOGO").onclick=function(){openNav();}
}

