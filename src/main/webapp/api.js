var api = "http://localhost:8080/paas/api";
function getAppPlatformInfo() {
  $.ajax({
    url: api + "/getAppPlatformInfo",
    type: "GET",
    dataType: "json",
    success: function (data) {
      loadDataIntoTable(data);
    },
    error: function (request, message, error) {
      handleException(request, message, error);
    },
  });
}

function loadDataIntoTable(data){
    console.log(data.domain);
    var appPlatformList = data.appPlatformList;
    var table="<table id='myTable'><tr class='header'><th>Domain</th><th>Org</th>";
    table+="<th>Application Instance</th><th>Cl No</th><th>Environment</th><th>Microservices</th></tr>";
    appPlatformList.forEach((appPlatform) => {
        table+="<tr><td>"+appPlatform.domain+"</td>";
        table+="<td>"+appPlatform.org+"</td>";
        table+="<td>"+appPlatform.applicationInstance+"</td>";
        table+="<td>"+appPlatform.clNo+"</td>";
        table+="<td>"+appPlatform.environment+"</td>";
        table+="<td>"+appPlatform.microservices+"</td></tr>";
      });
      table+="</table>";
      $('#applicationPlatformList').html(table);
}