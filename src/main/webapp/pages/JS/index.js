search()

let selectedInstance = null

function search() {
    $.ajax({
        url: "/api/findTripInstancesByPrompt",
        data: {
            prompt: getDesiredPrompt()
        },
        type: "get",
        async: false
    }).done(updateTripInstancesList)
}

function getDesiredPrompt() {
    return document.getElementById("prompt-input-field").value
}

function getBorrowType() {
    return document.getElementById("borrower-borrow-type").value
}

function getDesiredBorrowerUsername() {
    return document.getElementById("borrower-username-input").value
}

function updateTripInstancesList(instances) {
    let div = document.getElementById("prompt-search-results")
    let html = `<table>
        <thead>
            <th>ID</th>
            <th>TITLE</th>
            <th>DESCRIPTION</th>
            <th>BASE COST</th>
            <th>IS HOT</th>
            <th>SALES COST</th>
            <th>CAPACITY</th>
            <th>TIME</th>
            <th>STATUS</th>
        </thead>
    <tbody>`

    console.log(instances)

    instances.forEach(instance => html += getTripInstanceHTMLTableEntry(instance))

    html += "</tbody></table>"

    div.innerHTML = html
}

function getTripInstanceHTMLTableEntry(instance) {
    return ` <tr xmlns="http://www.w3.org/1999/html">
            <td>${instance.id}</td>
            <td>${instance.trip.title}</td>
            <td>${instance.trip.description}</td>
            <td>${instance.trip.baseCost}</td>
            <td>${instance.trip.isHot}</td>
            <td>${instance.trip.salesCost}</td>
            <td>${instance.trip.capacity}</td>
            <td>${instance.time}</td>
            <td>${instance.status.match(/([A-Z]?[^A-Z]*)/g).slice(0,-1).join(" ")}</td>
            <td><button onclick="doActionWithTrip(${instance.id})">${ instance.status === "NotPaid" ? "Pay" : "Refund" } </button></td>
        </tr>
    `
}

function selectTripInstance(id) {
    $.ajax({
        url: "/api/findTripInstanceByID",
        data: {
            id: id
        },
        type: "get",
        async: false
    }).done(x => selectedInstance = x)
}

function doActionWithTrip(id) {
    selectTripInstance(id)

    if (selectedInstance.status === "NotPaid") {
        buyTrip(selectedInstance)
    } else {
        refundTrip(selectedInstance);
    }
}

function buyTrip(instance) {
    $.ajax({
        url: "/api/buyTrip",
        data: {
            id: instance.id,
            username: username
        },
        type: "post",
        async: false
    }).done(() => {
        search()
    })
}

function refundTrip(instance) {
    $.ajax({
        url: "/api/refundTrip",
        data: {
            id: instance.id,
            username: username
        },
        type: "post",
        async: false
    }).done(() => {
        search()
    })
}