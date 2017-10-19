function star(i, j) {
	var k = 1;
	for (; k <= j; k++) {
		$(".star" + i + k).removeClass("empty");
		$(".star" + i + k).addClass("fit");
	}
	for (; k <= 5; k++) {
		$(".star" + i + k).removeClass("fit");
		$(".star" + i + k).addClass("empty");
	}
}

function submit() {
	var score1 = $(".score1").find(".fit").length;
	var score2 = $(".score2").find(".fit").length;
	var score3 = $(".score3").find(".fit").length;
	
	$("#star1").val(score1);
	$("#star2").val(score2);
	$("#star3").val(score3);
	
	$("#evaluateFrom").submit();
}
