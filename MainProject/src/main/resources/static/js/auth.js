var isAuthorized = localStorage.getItem('token') !== null;

function sendAuthForm()
{
    let auth_obj = {email:'', password: ''};

    let email = document.getElementById('auth_email').value;
    let password = document.getElementById('auth_password').value;

    clearAuthFormFields();

    auth_obj.email = email;
    auth_obj.password = password;

    let json = JSON.stringify(auth_obj);

    fetch('http://localhost:8080/auth/login',{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': '*/*'
        },
        body: json
    })
        .then(response => {return response.json()})
        .then((pdata) => {
            console.log(pdata);
            localStorage.setItem('token', pdata.token);
            localStorage.setItem('email', pdata.email);
            setAuthFormUnvisible();

            if (pdata.role === "ADMIN"){
                getAdminPage();
            }
            else if (pdata.role === "USER"){
                getUserPage();
            }

        })
        .catch((err) => {
            console.log(err);
        })
}

function getLoginPage()
{
    if(!isAuthorized)
    {
        document.getElementById('auth_form').classList.remove("unvisible");
        isAuthorized = true;
    }
    else
    {
        console.log('Logout');
        LogOut();
    }
}

function setAuthFormUnvisible()
{
    document.getElementById("auth_form").classList.add("unvisible");
}


function LogOut()
{
    fetch('http://localhost:8080/auth/logout', {
            method: 'POST',
            headers: {
            'Content-Type': 'application/json',
            'Accept': '*/*'
    },})
        .then(response => {
            localStorage.removeItem('token');
            localStorage.removeItem('email');
            isAuthorized = false;
            window.location.href = 'http://localhost:8080/';
            email = null;
        })
        .catch((err) => {
        console.log(err);
    });
}

function clearAuthFormFields()
{
    document.getElementById('auth_email').value = '';
    document.getElementById('auth_password').value= '';
}

function getAdminPage(){
    fetch('/admin', {
        method: 'GET',
        headers: {
            'Content-Type':'text/html',
            'Accept':'text/html',
            'Authorization':`${localStorage.getItem('token')}`
        }
    })
        .then(response => {return response.text()})
        .then(data => {
            let parser = new DOMParser();
            let html = parser.parseFromString(data, 'text/html');
            document.body = html.body;
        })
}

function getUserPage(){
    fetch('/user', {
        method: 'GET',
        headers: {
            'Content-Type':'text/html',
            'Accept':'text/html',
            'Authorization':`${localStorage.getItem('token')}`
        }
    })
        .then(response => {return response.text()})
        .then(data => {
            let parser = new DOMParser();
            let html = parser.parseFromString(data, 'text/html');
            document.body = html.body;
        })
}

function sendRegisterForm(){
    let email = document.getElementById('reg_email').value;
    let password = document.getElementById('reg_password').value;
    let firstname = document.getElementById('reg_firstname').value;
    let lastname = document.getElementById('reg_lastname').value;

    let obj = {
        email: email,
        password: password,
        first_name: firstname,
        last_name: lastname
    }

    fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json',
        'Accept': '*/*'},
        body: JSON.stringify(obj)
    })
    .then(response => {return response.json()})
        .then((pdata) => {
            console.log(pdata);
        })
        .catch((err) => {
            console.log(err);
        })
}