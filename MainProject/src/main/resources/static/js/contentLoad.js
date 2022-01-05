let num_meetup_page = 0;
let num_request_page = 0;
let num_report_page = 0;

let num_meetup_admin_page = 0;
let num_request_admin_page = 0;


let max_meetup_page;

let temp_id = null;

let isFilterActive = false;
let wasSearch = false;

function nextPage(){
    if(num_meetup_page < max_meetup_page)
    {
        num_meetup_page++;

        if(wasSearch)
        {
            if(isFilterActive){
                getMeetupsByDateAndTime();
            }
            else{
                getMeetupsByDate();
            }
        }
        else if(isFilterActive)
        {
            getMeetupsByTime();
        }
        else{
            getAllMeetups();
        }
    }
}

function previoslyPage(){
    if(num_meetup_page > 0)
    {
        num_meetup_page--;

        if(wasSearch){
            if(isFilterActive){
                getMeetupsByDateAndTime();
            }
            else{
                getMeetupsByDate();
            }
        }
        else if(isFilterActive){
            getMeetupsByTime();
        }
        else{
            getAllMeetups();
        }
    }
}

async function nextRequestPage(){
    num_request_page++;
    let count = await getUserRequests();
    console.log(count);
    if(count === 0){
        prevRequestPage();
    }
}

function prevRequestPage(){
    if(num_request_page > 0){
        num_request_page--;
        getUserRequests();
    }
}

 async function nextAdminRequestPage(){
    num_request_admin_page++;
    let count = await getRequestsForAdmin();
    if(count === 0){
        prevAdminRequestPage();
    }
}

function prevAdminRequestPage(){
    if(num_request_admin_page > 0){
        num_request_admin_page--;
        getRequestsForAdmin();
    }
}

async function nextAdminMeetupsPage(){
    num_meetup_admin_page++;
    let count = await getAllMeetupsForAdmin();
    if(count === 0){
        prevAdminMeetupsPage();
    }
}

function prevAdminMeetupsPage(){
    if(num_meetup_admin_page > 0){
        num_meetup_admin_page--;
        getAllMeetupsForAdmin();
    }
}

async function nextReportPage(){
    num_report_page++;
    let count = await getUserReports();
    if(count === 0){
        prevReportPage();
    }
}

function prevReportPage(){
    if(num_report_page > 0){
        num_report_page--;
        getUserReports();
    }
}

function resetPages(){
    num_meetup_page = 0;
    num_report_page = 0;
    num_request_page = 0;
}

function resetAdminPages(){
    num_meetup_admin_page = 0;
    num_request_admin_page = 0;
}

function getMeetupsByDate(){
    let date = document.getElementById('search_date').value;
    fetch(`http://localhost:8080/meetups/search/${num_meetup_page}`,{
        method: 'POST',
        headers: {
            'Content-Type':'application/json',
            'Accept':'*/*',
            'Authorization':`${localStorage.getItem('token')}`
        },
        body: JSON.stringify({date: date})
    })
        .then(response => {return response.json()})
        .then(data =>{
            console.log(data);
            document.getElementById('meetups').innerHTML = '';
            for (const iterator of data) {
                document.getElementById('meetups').innerHTML += `
            <table class="container">
            <tr>
                <td colspan="4" class="topic">${iterator.topic}</td>
            </tr>
            <tr id="${iterator.id}">
                <td class="datetime">${iterator.date}</td>
                <td>${iterator.place}</td>
                <td rowspan="2" style="width: 100px;">
                    <button onclick="sendRequest(this)">
                        Will go
                    </button>
                </td>
                <td rowspan="2" style="width: 100px;">
                    <button onclick="getReportForm(this)">Will go with report</button>
                </td>
            </tr>
            <tr>
                <td class="datetime">${iterator.time}</td>
                <td>${iterator.requirements}</td>
            </tr>
            </table>
            `;
            }
        })
}

function getMeetupsByDateAndTime(){
    let date = document.getElementById('search_date').value;
    let start = document.getElementById('start_time').value;
    let end = document.getElementById('end_time').value;


    fetch(`http://localhost:8080/meetups/search/filter/${num_meetup_page}`,{
        method: "POST",
        headers: {
            'Content-Type':'application/json',
            'Accept':'*/*',
            'Authorization':`${localStorage.getItem('token')}`
        },
        body: JSON.stringify({date: date, start_time: start, end_time: end})
    })
        .then(response => {return response.json()})
        .then(data =>{
            console.log(data);
            document.getElementById('meetups').innerHTML = '';
            for (const iterator of data) {
                document.getElementById('meetups').innerHTML += `
            <table class="container">
            <tr>
                <td colspan="4" class="topic">${iterator.topic}</td>
            </tr>
            <tr id="${iterator.id}">
                <td class="datetime">${iterator.date}</td>
                <td>${iterator.place}</td>
                <td rowspan="2" style="width: 100px;">
                    <button onclick="sendRequest(this)">
                        Will go
                    </button>
                </td>
                <td rowspan="2" style="width: 100px;">
                    <button onclick="getReportForm(this)">Will go with report</button>
                </td>
            </tr>
            <tr>
                <td class="datetime">${iterator.time}</td>
                <td>${iterator.requirements}</td>
            </tr>
            </table>
            `;
            }
        })
}

async function searchMeetupsByDate(){
    let date = document.getElementById('search_date').value;
    wasSearch = true;

    if(!isFilterActive)
    {
        let response  = await fetch(`http://localhost:8080/meetups/search/count`,{
            method: 'POST',
            headers: {
                'Content-Type':'application/json',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            },
            body: JSON.stringify({date: date})
        })

        let obj = await response.json();

        max_meetup_page = Math.trunc(obj.count / 8);
        num_meetup_page = 0;

        getMeetupsByDate();
    }
    else{
        let date = document.getElementById('search_date').value;
        let start = document.getElementById('start_time').value;
        let end = document.getElementById('end_time').value;

        let response  = await fetch(`http://localhost:8080/meetups/search/count`,{
            method: 'POST',
            headers: {
                'Content-Type':'application/json',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            },
            body: JSON.stringify({date: date, start_time: start, end_time: end})
        })

        let obj = await response.json();

        max_meetup_page = Math.trunc(obj.count / 8);
        num_meetup_page = 0;

        getMeetupsByDateAndTime();
    }
}

function getMeetupsByTime(){
    let start = document.getElementById('start_time').value;
    let end = document.getElementById('end_time').value;
    fetch(`/meetups/filter/${num_meetup_page}`,{
        method: 'POST',
        headers: {
            'Content-Type':'application/json',
            'Accept':'*/*',
            'Authorization':`${localStorage.getItem('token')}`
        },
        body: JSON.stringify({start: start, end: end})
    })
        .then(response => {return response.json()})
        .then(data =>{
            console.log(data);
            document.getElementById('meetups').innerHTML = '';
            for (const iterator of data) {
                document.getElementById('meetups').innerHTML += `
            <table class="container">
            <tr>
                <td colspan="4" class="topic">${iterator.topic}</td>
            </tr>
            <tr id="${iterator.id}">
                <td class="datetime">${iterator.date}</td>
                <td>${iterator.place}</td>
                <td rowspan="2" style="width: 100px;">
                    <button onclick="sendRequest(this)">
                        Will go
                    </button>
                </td>
                <td rowspan="2" style="width: 100px;">
                    <button onclick="getReportForm(this)">Will go with report</button>
                </td>
            </tr>
            <tr>
                <td class="datetime">${iterator.time}</td>
                <td>${iterator.requirements}</td>
            </tr>
            </table>
            `;
            }
        })
}

function FilterByTime(){
    if(!isFilterActive){
        isFilterActive = true;
        document.getElementById('filter_button').value = "Disable filter";
    }
    else
    {
        isFilterActive = false;
        document.getElementById('filter_button').value = "Filter";
    }

}

function magic(){
    let start_time = document.getElementById('start_time').value;
    document.getElementById('end_time').setAttribute('min',start_time);
}

function magic2(){
    let end_time = document.getElementById('end_time').value;
    document.getElementById('start_time').setAttribute('max',end_time);
}

    async function getAllMeetups(){

        let response = await fetch('http://localhost:8080/meetups/count',{
            method:"GET",
            headers: {
                'Content-Type':'text/html',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            }
        });

        let obj = await response.json();

        max_meetup_page = Math.trunc(obj.count / 8);

    fetch(`http://localhost:8080/meetups/${num_meetup_page}`, {
        method: 'GET',
        headers: {
            'Content-Type':'text/html',
            'Accept':'*/*',
            'Authorization':`${localStorage.getItem('token')}`
        }
    })
        .then(response => {return response.json()})
        .then(data => {
            console.log(data);
            document.getElementById('meetups').innerHTML = '';
            for (const iterator of data) {
                document.getElementById('meetups').innerHTML += `
            <table class="container">
            <tr>
                <td colspan="4" class="topic">${iterator.topic}</td>
            </tr>
            <tr id="${iterator.id}">
                <td class="datetime">${iterator.date}</td>
                <td>${iterator.place}</td>
                <td rowspan="2" style="width: 100px;">
                    <button onclick="sendRequest(this)">
                        Will go
                    </button>
                </td>
                <td rowspan="2" style="width: 100px;">
                    <button onclick="getReportForm(this)">Will go with report</button>
                </td>
            </tr>
            <tr>
                <td class="datetime">${iterator.time}</td>
                <td>${iterator.requirements}</td>
            </tr>
            </table>
            `;
            }
        })
    }

    function convertCurrentDate() {
        function pad(s) { return (s < 10) ? '0' + s : s; }
        var d = new Date()
        return [d.getFullYear(), pad(d.getMonth()+1), pad(d.getDate())].join('-')
    }

    function cheat(){
        document.getElementById('search_date').setAttribute('min', convertCurrentDate());
    }

    function sendRequest(button){
        console.log(button.parentElement);
        let meetup_id = Number.parseInt(button.parentElement.parentElement.id);

        let obj = {
            meetup_id: meetup_id,
            requester_email: localStorage.getItem('email')
        }

        let request_body = JSON.stringify(obj);

        console.log(request_body);
        fetch('http://localhost:8080/requests/add', {
            method: 'POST',
            headers: {
                'Content-Type':'application/json',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            },
            body: request_body
        })
            .then(response => {console.log("{status: ok}")})
    }

    function getReportForm(element){
        temp_id = Number.parseInt(element.parentElement.parentElement.id);
        document.getElementById('topic-form').offsetTop = window.pageYOffset;
        document.getElementById('topic-form').classList.remove('unvisible');
    }

    function clearReportForm(){
        let topic = document.getElementById('report_topic').value ='';
    }

    function hideReportForm(){
        document.getElementById('topic-form').classList.add('unvisible');
    }
    function sendReport(){
        let topic = document.getElementById('report_topic').value;
        let obj = {
            topic: topic,
            reporter: localStorage.getItem('email')
        }

        let request_body = JSON.stringify(obj);

        console.log(request_body);
        fetch(`http://localhost:8080/reports/add/${temp_id}`, {
            method: 'POST',
            headers: {
                'Content-Type':'application/json',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            },
            body: request_body
        })
            .then(response => {
                console.log("{status: ok}")
                clearReportForm();
                hideReportForm();
            })

        temp_id = null;
    }

    function getRequestPage(){
        document.getElementsByClassName('meetups-container')[0].classList.add('unvisible');
        document.getElementsByClassName('reports-container')[0].classList.add('unvisible');
        document.getElementsByClassName('requests-container')[0].classList.remove('unvisible');
        resetPages();
        getUserRequests();
    }

    function getReportPage(){
        document.getElementsByClassName('requests-container')[0].classList.add('unvisible');
        document.getElementsByClassName('meetups-container')[0].classList.add('unvisible');
        document.getElementsByClassName('reports-container')[0].classList.remove('unvisible');
        resetPages();
        getUserReports();
    }

    function getMeetupPage(){
        document.getElementsByClassName('requests-container')[0].classList.add('unvisible');
        document.getElementsByClassName('reports-container')[0].classList.add('unvisible');
        document.getElementsByClassName('meetups-container')[0].classList.remove('unvisible');
        resetPages();
    }

    async function getUserRequests(){

        let elements_count = 0;
        await fetch(`http://localhost:8080/requests/myrequests/${num_request_page}/${localStorage.getItem('email')}`,{
            method:'GET',
            headers: {
                'Content-Type':'text/html',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            }
        })
            .then( result => {return result.json()})
            .then(data => {
                document.getElementById('requests').innerHTML = '';
                elements_count = data.length;
                for (const element of data) {
                    let status = '';
                    if(element.isCanceled){
                        status = "Canceled";
                    }
                    else if(element.isApproved){
                        status = "Approved";
                    }
                    else{
                        status = "Waiting for a response";
                    }
                    document.getElementById('requests').innerHTML += `
                    <table class="container">
                        <tr id="${element.id}">
                            <td  class="place" style="width: 100px;">Id: ${element.id}</td>
                            <td  >Status: ${status}</td>
                            <td  style="width: 100px" ><button onclick="deleteUserRequest(this)">Delete</button></td>
                        </tr>
                    </table>
                    `;
                }
            })

        return elements_count;
    }

    function deleteUserRequest(elem){
        let request_id = Number.parseInt(elem.parentElement.parentElement.id);

        fetch(`http://localhost:8080/requests/delete/${request_id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type':'text/html',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            }
        })
            .then(result => {
                console.log('deleting successful');
                getUserRequests();
            })
    }

    async function getUserReports(){
        let elements_count = 0;
        await fetch(`http://localhost:8080/reports/myreports/${num_report_page}/${localStorage.getItem('email')}`,{
            method:'GET',
            headers: {
                'Content-Type':'text/html',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            },
        })
            .then( result => {return result.json()})
            .then(data => {
                document.getElementById('reports').innerHTML = '';
                elements_count = data.length;
                for (const element of data) {
                    document.getElementById('reports').innerHTML += `
                    <table class="container">
                        <tr id="${element.id}">
                            <td  class="place" style="width: 100px;">Id: ${element.id}</td>
                            <td>Topic: ${element.topic}</td>
                            <td style="width: 100px"><button onclick="getEditReportForm(this)">Edit</button></td>
                            <td style="width: 100px" ><button onclick="deleteReport(this)">Delete</button></td>
                        </tr>
                    </table>
                    `;
                }
            })

        return elements_count;
    }

    function deleteReport(elem){
        let report_id = Number.parseInt(elem.parentElement.parentElement.id);

        fetch(`http://localhost:8080/reports/delete/${report_id}`,{
            method: 'DELETE',
            headers: {
                'Content-Type':'text/html',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            }
        })
            .then(result => {
                console.log('report deleted successful');
                getUserReports();
            })
    }

    function getEditReportForm(elem){
        temp_id = Number.parseInt(elem.parentElement.parentElement.id);
        document.getElementById('edit_report_form').offsetTop = window.pageYOffset;
        document.getElementById('edit_report_form').classList.remove('unvisible');
    }

    function clearEditReportForm(){
        document.getElementById('edit_report_topic').value = '';
    }

    function hideEditReportForm(){
        document.getElementById('edit_report_form').classList.add('unvisible');
    }

    function saveChangedReport(){
        let topic = document.getElementById('edit_report_topic').value;
        let request_body = JSON.stringify({topic: topic, reporter: localStorage.getItem('email')});
        fetch(`http://localhost:8080/reports/update/${temp_id}`, {
            method: 'PUT',
            headers: {
                'Content-Type':'application/json',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            },
            body : request_body
        })
            .then(result =>{
                console.log('edit successful');
                clearEditReportForm();
                hideEditReportForm();
                getUserReports();
            })
    }

    function getAdminRequestPage(){
        document.getElementsByClassName('meetups-container')[0].classList.add('unvisible');
        document.getElementsByClassName('requests-container')[0].classList.remove('unvisible');
        resetAdminPages();
        getRequestsForAdmin();
    }

    function getAdminMeetupsPage(){
        document.getElementsByClassName('requests-container')[0].classList.add('unvisible');
        document.getElementsByClassName('meetups-container')[0].classList.remove('unvisible');
        resetAdminPages();
    }

    async function getRequestsForAdmin(){
        let count = 0;
        await fetch(`http://localhost:8080/requests/all/${num_request_admin_page}/${localStorage.getItem('email')}`,{
            method: 'GET',
            headers: {
                'Content-Type':'text/html',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            }
        })
            .then(result => {return result.json()})
            .then(data => {
                document.getElementById('requests').innerHTML = '';
                count = data.length;
                for (const element of data) {
                    document.getElementById('requests').innerHTML += `
                    <table class="container">
                        <tr id="${element.id}">
                            <td  class="place" style="width: 100px;">Id: ${element.id}</td>
                            <td  >Status: Waiting for a response</td>
                            <td><button onclick="cancelRequest(this)">Cancel</button></td>
                            <td><button onclick="approveRequest(this)">Approve</button></td>
                        </tr>
                    </table>
                    `;
                }
            })

        return count;
    }

    function DeleteMeetup(element){
        let meetup_id = Number.parseInt(element.parentElement.parentElement.id);

        console.log(meetup_id);
        fetch(`http://localhost:8080/meetups/delete/${meetup_id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type':'text/html',
                'Accept':'*/*',
                'Authorization':`${localStorage.getItem('token')}`
            }
        })
            .then(response => {
                console.log("{status: ok}")
                getAllMeetupsForAdmin();
            })
    }

async function getAllMeetupsForAdmin(){
    let count = 0;
    await fetch(`http://localhost:8080/meetups/${num_meetup_admin_page}`, {
        method: 'GET',
        headers: {
            'Content-Type':'text/html',
            'Accept':'*/*',
            'Authorization':`${localStorage.getItem('token')}`
        }
    })
        .then(response => {return response.json()})
        .then(data => {
            console.log(data);
            document.getElementById('meetups').innerHTML = '';
            count = data.length;
            for (const iterator of data) {
                document.getElementById('meetups').innerHTML += `
            <table class="container">
            <tr>
                <td colspan="4" class="topic">${iterator.topic}</td>
            </tr>
            <tr id="${iterator.id}">
                <td class="datetime">${iterator.date}</td>
                <td>${iterator.place}</td>
                <td rowspan="2" style="width: 100px;">
                    <button onclick="getEditMeetupForm(this)">
                        Edit
                    </button>
                </td>
                <td rowspan="2" style="width: 100px;">
                    <button onclick="DeleteMeetup(this)">Delete</button>
                </td>
            </tr>
            <tr>
                <td class="datetime">${iterator.time}</td>
                <td>${iterator.requirements}</td>
            </tr>
            </table>
            `;
            }
        })

    console.log(count);
    return count;
}

function getEditMeetupForm(element){
        temp_id = Number.parseInt(element.parentElement.parentElement.id);
        document.getElementById('edit-meetup-form').offsetTop = window.pageYOffset;
        document.getElementById('edit-meetup-form').classList.remove('unvisible');
}

function clearEditMeetupForm(){
    document.getElementById('edit_meetup_place').value = '';
    document.getElementById('edit_meetup_date').value = '';
    document.getElementById('edit_meetup_time').value = '';
    document.getElementById('edit_meetup_topic').value = '';
    document.getElementById('edit_meetup_requirements').value = '';
}

function hideEditMeetupForm(){
    document.getElementById('edit-meetup-form').classList.add('unvisible');
}

function cancelRequest(element){
    let request_id = Number.parseInt(element.parentElement.parentElement.id);
    fetch(`http://localhost:8080/requests/update/reject/${request_id}`,{
        method: 'PUT',
        headers: {
            'Content-Type':'text/html',
            'Accept':'*/*',
            'Authorization':`${localStorage.getItem('token')}`
        }
    })
        .then(response => {
        console.log("{status: ok}")
        getRequestsForAdmin();})
}

function approveRequest(element){
        let request_id = Number.parseInt(element.parentElement.parentElement.id);
    fetch(`http://localhost:8080/requests/update/approve/${request_id}`,{
        method: 'PUT',
        headers: {
            'Content-Type':'text/html',
            'Accept':'*/*',
            'Authorization':`${localStorage.getItem('token')}`
        }
    })
        .then(response => {
            console.log("{status: ok}")
            getRequestsForAdmin();})
}

function saveEditedMeetup(){
    let place = document.getElementById('edit_meetup_place').value;
    let date = document.getElementById('edit_meetup_date').value;
    let time = document.getElementById('edit_meetup_time').value;
    let topic = document.getElementById('edit_meetup_topic').value;
    let requirements = document.getElementById('edit_meetup_requirements').value;

    let request_body = JSON.stringify({
        place: place,
        date: date,
        time: time,
        topic: topic,
        requirements: requirements,
        creator_email: localStorage.getItem('email')
    })

    fetch(`http://localhost:8080/meetups/update/${temp_id}`, {
        method: 'PUT',
        headers: {
            'Content-Type':'application/json',
            'Accept':'*/*',
            'Authorization':`${localStorage.getItem('token')}`
        },
        body: request_body
    })
        .then(result => {
            console.log("{status: ok}");
            temp_id = null;
            clearEditMeetupForm();
            hideEditMeetupForm();
            getAllMeetupsForAdmin();
        });
}

function getMeetupForm(){
        document.getElementById('meetup-form').classList.remove('unvisible');
}

function hideMeetupForm(){
    document.getElementById('meetup-form').classList.add('unvisible');
}

function clearMeetupForm(){
    document.getElementById('meetup_place').value = '';
    document.getElementById('meetup_date').value = '';
    document.getElementById('meetup_time').value = '';
    document.getElementById('meetup_topic').value = '';
    document.getElementById('meetup_requirements').value = '';
}

function addMeetup(){
    let place = document.getElementById('meetup_place').value;
    let date = document.getElementById('meetup_date').value;
    let time = document.getElementById('meetup_time').value;
    let topic = document.getElementById('meetup_topic').value;
    let requirements = document.getElementById('meetup_requirements').value;

    let request_body = JSON.stringify({
        place: place,
        date: date,
        time: time,
        topic: topic,
        requirements: requirements,
        creator_email: localStorage.getItem('email')
    })

    fetch(`http://localhost:8080/meetups/add`, {
        method: 'POST',
        headers: {
            'Content-Type':'application/json',
            'Accept':'*/*',
            'Authorization':`${localStorage.getItem('token')}`
        },
        body: request_body
    })
        .then(result => {
            console.log("{status: ok}");
            temp_id = null;
            clearMeetupForm();
            hideMeetupForm();
            getAllMeetupsForAdmin();
        });
}