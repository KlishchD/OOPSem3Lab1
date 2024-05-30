search()

function addTripInstance(id) {
    $.ajax({
        url: "/api/addTripInstance",
        data: {
            time: getTripTime(id),
            id: id
        },
        type: "post",
        async: false
    })
}

function getTripTime(id) {
    return document.getElementById(`trip-time-input-field${id}`).value
}

function addTrip() {
    $.ajax({
        url: "/api/addTrip",
        data: {
            title: getTripTitle(),
            description: getTripDescription(),
            baseCost: getBaseCost(),
            isHot: getIsHot(),
            salesCost: getSalesCost(),
            capacity: getCapacity(),
        },
        type: "post",
        async: false
    }).done(search)
}

function getTripTitle() {
    return document.getElementById("title-input-field").value
}

function getTripDescription() {
    return document.getElementById("description-input-field").value
}

function getBaseCost() {
    return document.getElementById("base-cost-input-field").value
}

function getIsHot() {
    return document.getElementById("is-hot-input-field").checked
}

function getSalesCost() {
    return document.getElementById("sales-cost-input-field").value
}

function getCapacity() {
    return document.getElementById("capacity-input-field").value
}

function search() {
    $.ajax({
        url: "/api/findTripByPrompt",
        data: {
            prompt: getDesiredPrompt()
        },
        type: "get",
        async: false
    }).done(updateTripsList)
}

function getDesiredPrompt() {
    return document.getElementById("prompt-input-field").value
}

function updateTripsList(instances) {
    let div = document.getElementById("prompt-search-results")
    let html = `<table>
        <thead>
            <th>TITLE</th>
            <th>DESCRIPTION</th>
            <th>BASE COST</th>
            <th>IS HOT</th>
            <th>SALES COST</th>
            <th>CAPACITY</th>
            <th></th>
        </thead>
    <tbody>`

    instances.forEach(instance => html += getTripHTMLTableEntry(instance))

    html += "</tbody></table>"

    div.innerHTML = html
}

function getTripHTMLTableEntry(trip) {
    return ` <tr xmlns="http://www.w3.org/1999/html">
            <td>${trip.title}</td>
            <td>${trip.description}</td>
            <td>${trip.baseCost}</td>
            <td>${trip.isHot}</td>
            <td>${trip.salesCost}</td>
            <td>${trip.capacity}</td>
            <td>
                <input id="trip-time-input-field${trip.id}" type="date">
                <button onclick="addTripInstance(${trip.id})">add trip instance</button>
            </td>
        </tr>
    `
}