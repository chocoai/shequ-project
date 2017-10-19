/*function changeStatus(value, roomId) {
    $(".select-status").html("");
    var hidden = "<input class='room' type='hidden' value=" + roomId + " />";
    $(value).html("<i class='icon-ok shopCar-icon'>" + hidden + "</i>");
}*/
function changeStatus(value, roomId) {
    $(".select-status").html("");
    var hidden = "<input class='room' type='hidden' value=" + roomId + " />";
    $(value).children(".select-status").html("<i class='icon-ok shopCar-icon'>" + hidden + "</i>");
}

function toIndex() {
    var id = $(".shopCar-icon .room").val();
    window.location.href = ctx + "/index?roomId=" + id;
}