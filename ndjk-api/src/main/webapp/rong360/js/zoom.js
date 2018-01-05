$(document).ready(
	function(){
		function zoom() {
				var zoom = $(window).width() / 750;
				$("body").css("zoom", zoom).show();
			}
			zoom();
			window.onresize = zoom;
	}
)