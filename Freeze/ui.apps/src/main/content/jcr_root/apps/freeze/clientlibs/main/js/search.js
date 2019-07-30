var displayResults="demo";
var getResults = function(){

    var searchValue = $("#search-field").val();
    $.ajax({
        url : "/bin/search",
        type : "POST",
        data : {
               "search-field" : searchValue },
        success : function(result){
					displayResults(result);
        }

    });

};

    displayResults = function(resultData){
        console.log(resultData);
	var resultsJSON = JSON.parse(resultData), results = [];

    $.each(resultsJSON, function(key, val) {
		if (key !== "facets"){
			results.push("<a href='" + val + "'>" + key.substring(0, key.indexOf("*")) + "</a>");
		}
	});

		$("#results").html(results.join("<br/>"));




};



