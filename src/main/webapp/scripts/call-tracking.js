$(document).ready(function () {
    $.get("/stats/source", function (data) {
        console.log(data);
        CallTrackingGraph("#leads-by-source", data).draw();
    });

    $.get("/stats/city", function (data) {
        console.log(data);
        CallTrackingGraph("#leads-by-city", data).draw();
    });
});

CallTrackingGraph = function (selector, data) {
    function getContext() {
        return $(selector).get(0).getContext("2d");
    }

    return {
        draw: function () {
            var context = getContext(selector);
            new Chart(context).Pie(data);
        }
    }
}
