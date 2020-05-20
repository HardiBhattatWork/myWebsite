const getBtn = document.getElementById('get-btn');
const postBtn = document.getElementById('post-btn');

const sendHTTPRequest = (method, url, data) => {
    const promise = new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open(method, url);
    
        xhr.responseType = 'json';

        if(data) {
            xhr.setRequestHeader('Content-Type', 'application/json')
        }
        
        xhr.onerror = () => {
            reject("Something went wrong!")
        }
        
        xhr.onload = function(){
           resolve(xhr.response)
        };
    
        xhr.send(JSON.stringify(data));
    });
   return promise;
}
const getData = () => {
   sendHTTPRequest('GET', 'http://reqres.in/api/users').then(responseData => {
       console.log(responseData);
   });
};

const sendData = () => {
    sendHTTPRequest('POST', 'http://reqres.in/api/register', {email:'eve.holt@reqres.in', password: 'pistol'}).then(responseData => {
        console.log(responseData);
    }).catch(err => {
        console.log(err);
    });
};

getBtn.addEventListener('click', getData);
postBtn.addEventListener('click', sendData);